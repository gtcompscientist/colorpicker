# Color Picker Library - Compose Migration Plan

## Executive Summary
This document outlines the complete migration strategy to modernize the color picker library using Jetpack Compose, Kotlin Flows, coroutines, and modern Kotlin patterns. The library currently uses traditional Android Views and can be significantly simplified with Compose's declarative UI paradigm.

## Current Architecture Analysis

### Core Components
1. **ColorPickerView** - Custom View for color wheel interaction (527 lines)
2. **ColorPickerDialogBuilder** - Builder pattern for creating dialogs (284 lines)
3. **ColorPickerPreference** - Deprecated Preference integration (143 lines)
4. **Slider Components** - AlphaSlider, LightnessSlider (custom Views)
5. **Renderer System** - ColorWheelRenderer, SimpleColorWheelRenderer, FlowerColorWheelRenderer
6. **Support Classes** - ColorCircle, ColorExtensions, listeners

### Key Issues to Address
- Heavy use of custom Views with manual Canvas drawing
- Callback-based listeners (OnColorChangedListener, OnColorSelectedListener)
- Builder pattern creating complex initialization
- Mutable state management scattered across components
- No coroutine/Flow support
- Deprecated Preference class usage
- Manual bitmap and canvas management
- Tightly coupled components

## Migration Strategy

### Phase 1: Foundation - Modern Kotlin Patterns

#### 1.1 Create State Management Layer
**File**: `library/src/main/java/co/csadev/colorpicker/state/ColorPickerState.kt`

```kotlin
@Stable
data class ColorPickerState(
    val selectedColor: Color,
    val alpha: Float = 1f,
    val lightness: Float = 1f,
    val colors: List<Color> = emptyList(),
    val wheelType: WheelType = WheelType.FLOWER,
    val density: Int = 8
) {
    enum class WheelType { FLOWER, CIRCLE }
}

@Composable
fun rememberColorPickerState(
    initialColor: Color = Color.White,
    initialAlpha: Float = 1f,
    initialLightness: Float = 1f
): MutableState<ColorPickerState> {
    return remember {
        mutableStateOf(
            ColorPickerState(
                selectedColor = initialColor,
                alpha = initialAlpha,
                lightness = initialLightness
            )
        )
    }
}
```

#### 1.2 Replace Listeners with Flows
**File**: `library/src/main/java/co/csadev/colorpicker/ColorPickerEvents.kt`

```kotlin
sealed interface ColorPickerEvent {
    data class ColorChanged(val color: Color, val alpha: Float, val lightness: Float) : ColorPickerEvent
    data class ColorSelected(val finalColor: Color) : ColorPickerEvent
}

class ColorPickerEventHandler {
    private val _events = MutableSharedFlow<ColorPickerEvent>(replay = 0)
    val events: SharedFlow<ColorPickerEvent> = _events.asSharedFlow()

    suspend fun emitColorChanged(color: Color, alpha: Float, lightness: Float) {
        _events.emit(ColorPickerEvent.ColorChanged(color, alpha, lightness))
    }

    suspend fun emitColorSelected(color: Color) {
        _events.emit(ColorPickerEvent.ColorSelected(color))
    }
}

// Usage helper
@Composable
fun LaunchedColorPickerEventListener(
    eventHandler: ColorPickerEventHandler,
    onColorChanged: ((Color, Float, Float) -> Unit)? = null,
    onColorSelected: ((Color) -> Unit)? = null
) {
    LaunchedEffect(eventHandler) {
        eventHandler.events.collect { event ->
            when (event) {
                is ColorPickerEvent.ColorChanged -> onColorChanged?.invoke(
                    event.color, event.alpha, event.lightness
                )
                is ColorPickerEvent.ColorSelected -> onColorSelected?.invoke(event.color)
            }
        }
    }
}
```

#### 1.3 Modernize Color Extensions
**File**: Update `library/src/main/java/co/csadev/colorpicker/ColorExtensions.kt`

```kotlin
// Add Compose Color extensions
fun androidx.compose.ui.graphics.Color.toAndroidColor(): Int =
    toArgb()

fun Int.toComposeColor(): androidx.compose.ui.graphics.Color =
    androidx.compose.ui.graphics.Color(this)

// Add HSV utilities for Compose
data class HSV(val hue: Float, val saturation: Float, val value: Float)

fun androidx.compose.ui.graphics.Color.toHSV(): HSV {
    val hsv = FloatArray(3)
    android.graphics.Color.colorToHSV(this.toArgb(), hsv)
    return HSV(hsv[0], hsv[1], hsv[2])
}

fun HSV.toColor(alpha: Float = 1f): androidx.compose.ui.graphics.Color {
    val color = android.graphics.Color.HSVToColor(floatArrayOf(hue, saturation, value))
    return androidx.compose.ui.graphics.Color(color).copy(alpha = alpha)
}

// Replace String formatting
val androidx.compose.ui.graphics.Color.hexString: String
    get() = "#%06X".format(0xFFFFFF and toArgb()).uppercase()

val androidx.compose.ui.graphics.Color.hexStringWithAlpha: String
    get() = "#%08X".format(toArgb()).uppercase()
```

### Phase 2: Core Composables

#### 2.1 Color Wheel Composable
**File**: `library/src/main/java/co/csadev/colorpicker/compose/ColorWheel.kt`

```kotlin
@Composable
fun ColorWheel(
    modifier: Modifier = Modifier,
    state: MutableState<ColorPickerState>,
    wheelType: ColorPickerState.WheelType = ColorPickerState.WheelType.FLOWER,
    density: Int = 8,
    showBorder: Boolean = true,
    onColorChange: (Color) -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    val size = remember(configuration) {
        minOf(configuration.screenWidthDp, configuration.screenHeightDp).dp
    }

    var selectedOffset by remember { mutableStateOf(Offset.Zero) }

    Canvas(
        modifier = modifier
            .size(size)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    selectedOffset = offset
                    val color = calculateColorFromOffset(
                        offset = offset,
                        size = this.size,
                        wheelType = wheelType,
                        density = density,
                        lightness = state.value.lightness,
                        alpha = state.value.alpha
                    )
                    state.value = state.value.copy(selectedColor = color)
                    onColorChange(color)
                }
            }
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    selectedOffset = change.position
                    val color = calculateColorFromOffset(
                        offset = change.position,
                        size = this.size,
                        wheelType = wheelType,
                        density = density,
                        lightness = state.value.lightness,
                        alpha = state.value.alpha
                    )
                    state.value = state.value.copy(selectedColor = color)
                    onColorChange(color)
                }
            }
    ) {
        drawColorWheel(
            wheelType = wheelType,
            density = density,
            lightness = state.value.lightness,
            alpha = state.value.alpha,
            showBorder = showBorder,
            selectedOffset = selectedOffset
        )
    }
}

private fun DrawScope.drawColorWheel(
    wheelType: ColorPickerState.WheelType,
    density: Int,
    lightness: Float,
    alpha: Float,
    showBorder: Boolean,
    selectedOffset: Offset
) {
    val radius = size.minDimension / 2f
    val center = Offset(size.width / 2f, size.height / 2f)

    when (wheelType) {
        ColorPickerState.WheelType.FLOWER -> drawFlowerWheel(
            center, radius, density, lightness, alpha, showBorder, selectedOffset
        )
        ColorPickerState.WheelType.CIRCLE -> drawCircleWheel(
            center, radius, density, lightness, alpha, showBorder, selectedOffset
        )
    }
}

private suspend fun calculateColorFromOffset(
    offset: Offset,
    size: IntSize,
    wheelType: ColorPickerState.WheelType,
    density: Int,
    lightness: Float,
    alpha: Float
): Color = withContext(Dispatchers.Default) {
    // Calculate HSV from touch position
    val center = Offset(size.width / 2f, size.height / 2f)
    val dx = offset.x - center.x
    val dy = offset.y - center.y

    val distance = sqrt(dx * dx + dy * dy)
    val maxRadius = size.minDimension / 2f
    val saturation = (distance / maxRadius).coerceIn(0f, 1f)

    val angle = atan2(dy, dx)
    val hue = ((angle * 180f / PI.toFloat()) + 360f) % 360f

    HSV(hue, saturation, lightness).toColor(alpha)
}
```

#### 2.2 Slider Composables
**File**: `library/src/main/java/co/csadev/colorpicker/compose/Sliders.kt`

```kotlin
@Composable
fun LightnessSlider(
    modifier: Modifier = Modifier,
    color: Color,
    lightness: Float,
    onLightnessChange: (Float) -> Unit
) {
    Column(modifier = modifier) {
        Slider(
            value = lightness,
            onValueChange = onLightnessChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .drawWithCache {
                    val gradient = Brush.horizontalGradient(
                        colors = buildList {
                            val hsv = color.toHSV()
                            for (i in 0..100) {
                                val l = i / 100f
                                add(hsv.copy(value = l).toColor(color.alpha))
                            }
                        }
                    )
                    onDrawBehind {
                        drawRoundRect(
                            brush = gradient,
                            cornerRadius = CornerRadius(8.dp.toPx())
                        )
                    }
                },
            colors = SliderDefaults.colors(
                thumbColor = color.copy(alpha = 1f),
                activeTrackColor = Color.Transparent,
                inactiveTrackColor = Color.Transparent
            )
        )
    }
}

@Composable
fun AlphaSlider(
    modifier: Modifier = Modifier,
    color: Color,
    alpha: Float,
    onAlphaChange: (Float) -> Unit
) {
    Slider(
        value = alpha,
        onValueChange = onAlphaChange,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .drawWithCache {
                // Draw checkerboard pattern for alpha
                val checkerSize = 8.dp.toPx()
                val gradient = Brush.horizontalGradient(
                    colors = buildList {
                        for (i in 0..100) {
                            add(color.copy(alpha = i / 100f))
                        }
                    }
                )
                onDrawBehind {
                    drawCheckerboard(checkerSize)
                    drawRoundRect(
                        brush = gradient,
                        cornerRadius = CornerRadius(8.dp.toPx())
                    )
                }
            },
        colors = SliderDefaults.colors(
            thumbColor = color,
            activeTrackColor = Color.Transparent,
            inactiveTrackColor = Color.Transparent
        )
    )
}

private fun DrawScope.drawCheckerboard(checkerSize: Float) {
    val cols = (size.width / checkerSize).toInt() + 1
    val rows = (size.height / checkerSize).toInt() + 1

    for (row in 0 until rows) {
        for (col in 0 until cols) {
            if ((row + col) % 2 == 0) {
                drawRect(
                    color = Color.LightGray,
                    topLeft = Offset(col * checkerSize, row * checkerSize),
                    size = Size(checkerSize, checkerSize)
                )
            }
        }
    }
}
```

#### 2.3 Color Preview Composable
**File**: `library/src/main/java/co/csadev/colorpicker/compose/ColorPreview.kt`

```kotlin
@Composable
fun ColorPreview(
    colors: List<Color>,
    selectedIndex: Int,
    onColorSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(colors) { index, color ->
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(color)
                    .border(
                        width = if (index == selectedIndex) 3.dp else 1.dp,
                        color = if (index == selectedIndex)
                            MaterialTheme.colorScheme.primary
                        else
                            Color.Gray,
                        shape = CircleShape
                    )
                    .clickable { onColorSelected(index) }
            )
        }
    }
}
```

#### 2.4 Main Color Picker Composable
**File**: `library/src/main/java/co/csadev/colorpicker/compose/ColorPicker.kt`

```kotlin
@Composable
fun ColorPicker(
    modifier: Modifier = Modifier,
    initialColor: Color = Color.White,
    wheelType: ColorPickerState.WheelType = ColorPickerState.WheelType.FLOWER,
    showAlphaSlider: Boolean = true,
    showLightnessSlider: Boolean = true,
    showColorEdit: Boolean = false,
    showBorder: Boolean = true,
    density: Int = 8,
    onColorChanged: ((Color) -> Unit)? = null,
    onColorSelected: ((Color) -> Unit)? = null
) {
    val state = rememberColorPickerState(
        initialColor = initialColor,
        initialAlpha = initialColor.alpha,
        initialLightness = initialColor.toHSV().value
    )

    val scope = rememberCoroutineScope()
    val eventHandler = remember { ColorPickerEventHandler() }

    // Handle events
    LaunchedColorPickerEventListener(
        eventHandler = eventHandler,
        onColorChanged = onColorChanged?.let { callback ->
            { color, alpha, lightness -> callback(color) }
        },
        onColorSelected = onColorSelected
    )

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Color Wheel
        ColorWheel(
            state = state,
            wheelType = wheelType,
            density = density,
            showBorder = showBorder,
            onColorChange = { color ->
                scope.launch {
                    eventHandler.emitColorChanged(
                        color,
                        state.value.alpha,
                        state.value.lightness
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Lightness Slider
        if (showLightnessSlider) {
            LightnessSlider(
                color = state.value.selectedColor,
                lightness = state.value.lightness,
                onLightnessChange = { newLightness ->
                    state.value = state.value.copy(lightness = newLightness)
                    scope.launch {
                        eventHandler.emitColorChanged(
                            state.value.selectedColor,
                            state.value.alpha,
                            newLightness
                        )
                    }
                }
            )
        }

        // Alpha Slider
        if (showAlphaSlider) {
            AlphaSlider(
                color = state.value.selectedColor,
                alpha = state.value.alpha,
                onAlphaChange = { newAlpha ->
                    state.value = state.value.copy(alpha = newAlpha)
                    scope.launch {
                        eventHandler.emitColorChanged(
                            state.value.selectedColor,
                            newAlpha,
                            state.value.lightness
                        )
                    }
                }
            )
        }

        // Color Edit TextField
        if (showColorEdit) {
            ColorEditField(
                color = state.value.selectedColor,
                onColorChange = { newColor ->
                    state.value = state.value.copy(selectedColor = newColor)
                }
            )
        }
    }
}

@Composable
private fun ColorEditField(
    color: Color,
    onColorChange: (Color) -> Unit
) {
    var text by remember(color) {
        mutableStateOf(color.hexStringWithAlpha)
    }

    OutlinedTextField(
        value = text,
        onValueChange = { newText ->
            text = newText.uppercase()
            // Try to parse color
            try {
                val parsedColor = android.graphics.Color.parseColor(newText)
                onColorChange(Color(parsedColor))
            } catch (e: IllegalArgumentException) {
                // Invalid color format, ignore
            }
        },
        label = { Text("Color") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters)
    )
}
```

### Phase 3: Dialog Integration

#### 3.1 Material3 Dialog
**File**: `library/src/main/java/co/csadev/colorpicker/compose/ColorPickerDialog.kt`

```kotlin
@Composable
fun ColorPickerDialog(
    onDismissRequest: () -> Unit,
    onColorSelected: (Color) -> Unit,
    initialColor: Color = Color.White,
    title: String = "Choose Color",
    confirmText: String = "OK",
    dismissText: String = "Cancel",
    wheelType: ColorPickerState.WheelType = ColorPickerState.WheelType.FLOWER,
    showAlphaSlider: Boolean = true,
    showLightnessSlider: Boolean = true,
    showColorEdit: Boolean = false,
    density: Int = 8
) {
    val state = rememberColorPickerState(initialColor = initialColor)

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(title) },
        text = {
            ColorPicker(
                modifier = Modifier.fillMaxWidth(),
                initialColor = initialColor,
                wheelType = wheelType,
                showAlphaSlider = showAlphaSlider,
                showLightnessSlider = showLightnessSlider,
                showColorEdit = showColorEdit,
                density = density
            )
        },
        confirmButton = {
            TextButton(onClick = {
                onColorSelected(state.value.selectedColor)
                onDismissRequest()
            }) {
                Text(confirmText)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(dismissText)
            }
        }
    )
}

// Helper for showing dialog
@Composable
fun rememberColorPickerDialogState(
    initialColor: Color = Color.White
): ColorPickerDialogState {
    return remember { ColorPickerDialogState(initialColor) }
}

class ColorPickerDialogState(
    initialColor: Color
) {
    var isShowing by mutableStateOf(false)
        private set

    var selectedColor by mutableStateOf(initialColor)
        private set

    fun show() {
        isShowing = true
    }

    fun hide() {
        isShowing = false
    }

    fun updateColor(color: Color) {
        selectedColor = color
    }
}

@Composable
fun ColorPickerDialogHost(
    state: ColorPickerDialogState,
    onColorSelected: (Color) -> Unit,
    title: String = "Choose Color",
    confirmText: String = "OK",
    dismissText: String = "Cancel",
    wheelType: ColorPickerState.WheelType = ColorPickerState.WheelType.FLOWER,
    showAlphaSlider: Boolean = true,
    showLightnessSlider: Boolean = true,
    showColorEdit: Boolean = false,
    density: Int = 8
) {
    if (state.isShowing) {
        ColorPickerDialog(
            onDismissRequest = { state.hide() },
            onColorSelected = { color ->
                state.updateColor(color)
                onColorSelected(color)
            },
            initialColor = state.selectedColor,
            title = title,
            confirmText = confirmText,
            dismissText = dismissText,
            wheelType = wheelType,
            showAlphaSlider = showAlphaSlider,
            showLightnessSlider = showLightnessSlider,
            showColorEdit = showColorEdit,
            density = density
        )
    }
}
```

### Phase 4: Preferences Integration (DataStore)

#### 4.1 Modern Settings Integration
**File**: `library/src/main/java/co/csadev/colorpicker/compose/ColorPickerPreference.kt`

```kotlin
// Replace deprecated Preference with Compose + DataStore
@Composable
fun ColorPickerPreferenceItem(
    title: String,
    summary: String? = null,
    color: Color,
    onColorChange: (Color) -> Unit,
    enabled: Boolean = true,
    wheelType: ColorPickerState.WheelType = ColorPickerState.WheelType.FLOWER,
    showAlphaSlider: Boolean = true,
    showLightnessSlider: Boolean = true
) {
    val dialogState = rememberColorPickerDialogState(initialColor = color)

    ListItem(
        headlineContent = { Text(title) },
        supportingContent = summary?.let { { Text(it) } },
        leadingContent = {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(if (enabled) color else color.copy(alpha = 0.5f))
                    .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape)
            )
        },
        modifier = Modifier
            .clickable(enabled = enabled) { dialogState.show() }
            .alpha(if (enabled) 1f else 0.5f)
    )

    ColorPickerDialogHost(
        state = dialogState,
        onColorSelected = onColorChange,
        wheelType = wheelType,
        showAlphaSlider = showAlphaSlider,
        showLightnessSlider = showLightnessSlider
    )
}

// DataStore extension for color preferences
suspend fun DataStore<Preferences>.saveColor(key: String, color: Color) {
    edit { preferences ->
        preferences[intPreferencesKey(key)] = color.toArgb()
    }
}

fun DataStore<Preferences>.getColorFlow(
    key: String,
    defaultColor: Color = Color.White
): Flow<Color> = data.map { preferences ->
    val colorInt = preferences[intPreferencesKey(key)] ?: defaultColor.toArgb()
    Color(colorInt)
}

// Usage example
@Composable
fun ColorPreferenceExample(dataStore: DataStore<Preferences>) {
    val color by dataStore.getColorFlow("theme_color")
        .collectAsState(initial = Color.Blue)

    val scope = rememberCoroutineScope()

    ColorPickerPreferenceItem(
        title = "Theme Color",
        summary = "Choose your theme color",
        color = color,
        onColorChange = { newColor ->
            scope.launch {
                dataStore.saveColor("theme_color", newColor)
            }
        }
    )
}
```

### Phase 5: Testing & Utilities

#### 5.1 Testing Support
**File**: `library/src/main/java/co/csadev/colorpicker/testing/ColorPickerTestUtils.kt`

```kotlin
// Test utilities for Compose UI tests
@VisibleForTesting
fun createTestColorPickerState(
    color: Color = Color.Red,
    alpha: Float = 1f,
    lightness: Float = 0.5f
) = ColorPickerState(
    selectedColor = color,
    alpha = alpha,
    lightness = lightness
)

// Semantic properties for testing
val ColorWheelSemantics = SemanticsPropertyKey<Boolean>("ColorWheel")
var SemanticsPropertyReceiver.isColorWheel by ColorWheelSemantics

val ColorSliderSemantics = SemanticsPropertyKey<String>("ColorSlider")
var SemanticsPropertyReceiver.sliderType by ColorSliderSemantics
```

#### 5.2 Preview Utilities
**File**: `library/src/main/java/co/csadev/colorpicker/compose/Preview.kt`

```kotlin
@Preview(showBackground = true)
@Composable
private fun ColorPickerPreview() {
    MaterialTheme {
        ColorPicker(
            initialColor = Color.Blue,
            wheelType = ColorPickerState.WheelType.FLOWER,
            showAlphaSlider = true,
            showLightnessSlider = true,
            showColorEdit = true
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ColorPickerDialogPreview() {
    MaterialTheme {
        ColorPickerDialog(
            onDismissRequest = {},
            onColorSelected = {},
            initialColor = Color.Green
        )
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
private fun ColorPickerPreferencePreview() {
    MaterialTheme {
        Surface {
            ColorPickerPreferenceItem(
                title = "Accent Color",
                summary = "Choose your accent color",
                color = Color.Cyan,
                onColorChange = {}
            )
        }
    }
}
```

### Phase 6: Migration & Deprecation

#### 6.1 Deprecation Strategy
**File**: Update existing files with deprecation warnings

```kotlin
// In ColorPickerView.kt
@Deprecated(
    message = "Use Compose ColorPicker instead",
    replaceWith = ReplaceWith(
        "ColorPicker",
        "co.csadev.colorpicker.compose.ColorPicker"
    ),
    level = DeprecationLevel.WARNING
)
class ColorPickerView : View {
    // ... existing code
}

// In ColorPickerDialogBuilder.kt
@Deprecated(
    message = "Use ColorPickerDialog composable instead",
    replaceWith = ReplaceWith(
        "ColorPickerDialog",
        "co.csadev.colorpicker.compose.ColorPickerDialog"
    ),
    level = DeprecationLevel.WARNING
)
class ColorPickerDialogBuilder private constructor(context: Context, theme: Int = 0) {
    // ... existing code
}
```

#### 6.2 Interop Layer
**File**: `library/src/main/java/co/csadev/colorpicker/interop/ViewInterop.kt`

```kotlin
// Allow using Compose ColorPicker in Views
class ComposeColorPickerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {

    var onColorSelected: ((Color) -> Unit)? = null
    var initialColor: Color = Color.White
    var wheelType: ColorPickerState.WheelType = ColorPickerState.WheelType.FLOWER

    @Composable
    override fun Content() {
        MaterialTheme {
            ColorPicker(
                initialColor = initialColor,
                wheelType = wheelType,
                onColorSelected = onColorSelected
            )
        }
    }
}
```

### Phase 7: Documentation & Migration Guide

#### 7.1 Create Migration Guide
**File**: `MIGRATION.md`

Include examples showing:
- Before/After code comparisons
- Common patterns in View system → Compose equivalents
- DataStore replacement for Preferences
- Flow-based event handling

#### 7.2 Update README
Add sections for:
- Compose-first approach
- Quick start with Compose
- Legacy View support (deprecated)
- Examples and samples

## Implementation Checklist

### Must-Have (MVP)
- [ ] ColorPickerState and state management
- [ ] Basic ColorWheel composable with gesture handling
- [ ] LightnessSlider and AlphaSlider composables
- [ ] ColorPicker main composable
- [ ] ColorPickerDialog with Material3
- [ ] Color extensions for Compose
- [ ] Basic event handling with Flows
- [ ] Unit tests for state management
- [ ] UI tests for composables
- [ ] Migration guide documentation

### Should-Have
- [ ] ColorPickerPreferenceItem with DataStore
- [ ] Color preview grid composable
- [ ] Multiple wheel types (Flower, Circle)
- [ ] ColorEditField with validation
- [ ] Accessibility support (semantics, content descriptions)
- [ ] Animation support for smooth transitions
- [ ] Interop layer for View system
- [ ] Comprehensive preview functions
- [ ] Sample app demonstrating all features
- [ ] Performance optimization for Canvas drawing

### Nice-to-Have
- [ ] Custom color palettes support
- [ ] Color harmony suggestions (complementary, triadic, etc.)
- [ ] Recent colors history
- [ ] Color themes (Material, iOS, custom)
- [ ] Gradient picker support
- [ ] Export/import color palettes
- [ ] Advanced customization options
- [ ] Dark mode optimizations
- [ ] Tablet/large screen layouts
- [ ] Benchmark tests

## Benefits of This Migration

1. **Reduced Code Complexity**: ~60% reduction in code (estimated)
   - No manual Canvas/Bitmap management
   - Declarative UI reduces boilerplate
   - Built-in state management

2. **Modern Kotlin Features**
   - Coroutines for async operations
   - Flows for reactive events
   - Extension functions for cleaner API
   - Data classes for immutable state

3. **Better Performance**
   - Compose's smart recomposition
   - Automatic optimization
   - Reduced memory allocations

4. **Improved Developer Experience**
   - Preview support for rapid iteration
   - Better testability with Compose testing APIs
   - Type-safe builders
   - Null safety throughout

5. **Future-Proof**
   - Compose is Android's modern UI toolkit
   - Active development and support
   - Better integration with Material Design 3
   - Multiplatform potential (Compose Multiplatform)

## Breaking Changes

1. Minimum API level may increase (Compose requires API 21+, already at 24)
2. Callbacks replaced with lambdas and Flows
3. Builder pattern replaced with composable parameters
4. Preference class removed (use DataStore + Compose)
5. Custom Views removed in favor of composables

## Timeline Estimate

- **Phase 1**: 1 week (Foundation)
- **Phase 2**: 2 weeks (Core Composables)
- **Phase 3**: 1 week (Dialog Integration)
- **Phase 4**: 1 week (DataStore Integration)
- **Phase 5**: 1 week (Testing)
- **Phase 6**: 1 week (Migration & Deprecation)
- **Phase 7**: 1 week (Documentation)

**Total**: ~8 weeks for complete migration

## Conclusion

This migration plan transforms the color picker library from a View-based implementation to a modern, Compose-first library using coroutines, Flows, and modern Kotlin patterns. The result will be a significantly simpler, more maintainable, and future-proof library that provides a better developer experience while maintaining backward compatibility during the transition period.