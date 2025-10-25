# Snapshot Testing Guide

This document describes the snapshot testing setup for the ColorPicker library using [Roborazzi](https://github.com/takahirom/roborazzi) and the official Android Compose screenshot testing approach.

## Overview

The ColorPicker library uses Roborazzi for snapshot testing, which integrates with the official Android screenshot testing framework. Roborazzi runs tests on the JVM using Robolectric, providing fast, reliable snapshot tests without requiring an emulator.

**Key Features:**
- ✅ JVM-based testing (no emulator required)
- ✅ Fast execution (~2-5 minutes for full test suite)
- ✅ Pixel-perfect UI regression detection
- ✅ Multiple device configurations
- ✅ **PR-based snapshot updates** via comment commands
- ✅ Automated CI/CD integration

## Quick Start

### Running Tests Locally

```bash
# Record reference snapshots (first time setup)
./gradlew :library:recordSnapshots

# Verify snapshots match
./gradlew :library:verifySnapshots

# Compare snapshots (generates comparison images)
./gradlew :library:compareSnapshots

# Clean snapshot artifacts
./gradlew :library:cleanSnapshots

# Generate snapshot report
./gradlew :library:snapshotReport
```

### Updating Snapshots in a Pull Request

When snapshot tests fail in a PR and the changes are intentional, you can update snapshots directly from the PR:

**Comment on the PR:**
```
/record-snapshots
```
or
```
/update-snapshots
```

The GitHub Actions workflow will:
1. Record new snapshots
2. Commit them to your PR branch
3. Comment with the results

This makes it easy to update snapshots without needing to run tests locally!

## Test Coverage

The snapshot tests cover **50+ scenarios** across three test files:

### 1. Component Tests (`ComponentSnapshotTests.kt`) - ~25 tests
- **ColorWheel**: Flower and Circle types with varying densities (6, 10, 15)
- **ColorWheel**: Different lightness values (0.3, 0.6, 1.0)
- **ColorWheel**: With transparency
- **LightnessSlider**: Multiple brightness levels and colors
- **AlphaSlider**: Multiple opacity levels and colors
- **ColorPreviewBox**: Various colors (solid and transparent)

### 2. ColorPicker Tests (`ColorPickerSnapshotTests.kt`) - ~10 tests
- Full-featured picker with all options enabled
- Flower vs Circle wheel types
- Various feature combinations (with/without sliders, color edit)
- Different density settings (6, 10, 15)
- Various initial colors

### 3. Dialog & Configuration Tests (`DialogAndConfigurationSnapshotTests.kt`) - ~15 tests
- **ColorPickerDialog**: Full-featured, minimal configurations
- **ColorPickerPreferenceItem**: Enabled, disabled, multiple items
- **Device Configurations**: Small phone, tablet, landscape
- **Themes**: Light and dark Material3 themes
- **Locales**: RTL support (Arabic, Hebrew)

## Directory Structure

```
library/
├── src/test/
│   ├── java/co/csadev/colorpicker/snapshots/
│   │   ├── ComponentSnapshotTests.kt
│   │   ├── ColorPickerSnapshotTests.kt
│   │   └── DialogAndConfigurationSnapshotTests.kt
│   └── resources/roborazzi/           # Reference snapshots
│       ├── ComponentSnapshotTests_colorWheel_flowerType_default.png
│       ├── ComponentSnapshotTests_colorWheel_circleType_default.png
│       └── ... (50+ PNG files)
└── SNAPSHOT_TESTING.md                # This file
```

## CI/CD Integration

### Snapshot Verification

On every PR and push to main branches, the GitHub Actions workflow:
1. Runs all snapshot tests
2. Compares against reference snapshots
3. Fails if any differences are detected
4. Uploads comparison images as artifacts
5. Comments on PR with results and instructions

### PR Comment Commands

You can control snapshot updates directly from PR comments:

| Command | Description |
|---------|-------------|
| `/record-snapshots` | Records new snapshots and commits to PR |
| `/update-snapshots` | Alias for `/record-snapshots` |

**Example workflow:**
1. Make UI changes and push to PR
2. Snapshot tests fail in CI
3. Review the failure artifacts
4. If changes are intentional, comment `/record-snapshots`
5. GitHub Actions records and commits new snapshots
6. Tests pass on next run!

## Adding New Snapshot Tests

### 1. Create a Test Function

```kotlin
@Test
@Config(sdk = [33], qualifiers = "w411dp-h891dp-420dpi")
fun myNewTest() {
    composeTestRule.setContent {
        TestTheme {
            // Your composable here
            ColorWheel(
                wheelType = ColorPickerState.WheelType.FLOWER,
                density = 10,
                lightness = 1f,
                alpha = 1f
            )
        }
    }
    captureRoboImage()
}
```

### 2. Record the Snapshot

```bash
./gradlew :library:recordSnapshots
```

### 3. Verify the Snapshot

```bash
./gradlew :library:verifySnapshots
```

### 4. Commit Test and Snapshot

```bash
git add library/src/test/java/co/csadev/colorpicker/snapshots/
git add library/src/test/resources/roborazzi/
git commit -m "Add snapshot test for new feature"
```

## Configuration Options

### Device Qualifiers

Control device configuration using `@Config` annotation:

```kotlin
@Config(
    sdk = [33],
    qualifiers = "w411dp-h891dp-normal-long-notround-any-420dpi-keyshidden-nonav"
)
```

Common qualifiers:
- **Size**: `w411dp-h891dp` (width x height)
- **Density**: `420dpi`, `320dpi`, `560dpi`
- **Orientation**: `land` (landscape), `port` (portrait)
- **Locale**: `en-rUS`, `ar-rSA`, `he-rIL`
- **Layout Direction**: `ldrtl` (right-to-left)
- **Screen Size**: `small`, `normal`, `large`, `xlarge`

### Graphics Mode

Tests use native graphics mode for accurate rendering:

```kotlin
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class MySnapshotTests { ... }
```

## Troubleshooting

### Snapshots Don't Match After UI Changes

**If changes are intentional:**

Option 1 - Via PR comment:
```
/record-snapshots
```

Option 2 - Locally:
```bash
./gradlew :library:recordSnapshots
git add library/src/test/resources/roborazzi/
git commit -m "Update snapshots"
git push
```

### Tests Fail in CI But Pass Locally

**Possible causes:**
- Different JDK versions (use JDK 17)
- Uncommitted snapshot changes
- Graphics mode mismatch

**Solution:**
Ensure you're using JDK 17 both locally and in CI.

### Can't Find Snapshot Images

**Location:** `library/src/test/resources/roborazzi/`

**Solution:**
```bash
./gradlew :library:recordSnapshots
```

### Viewing Comparison Images

When tests fail, Roborazzi generates comparison images in:
```
library/build/outputs/roborazzi/
```

These show:
- `*_actual.png` - Current rendering
- `*_compare.png` - Side-by-side comparison with differences highlighted

Download these from CI artifacts to see exactly what changed.

## Best Practices

### 1. Descriptive Test Names

✅ Good:
```kotlin
@Test
fun colorWheel_flowerType_darkLightness()
```

❌ Bad:
```kotlin
@Test
fun test1()
```

### 2. Test One Thing at a Time

Each test should focus on a single variation.

### 3. Use Consistent Sizing

```kotlin
Box(modifier = Modifier.size(300.dp).padding(16.dp)) {
    // Component here
}
```

### 4. Update Snapshots Intentionally

Only update reference snapshots when visual changes are intentional.

### 5. Review Comparison Images

Always review the comparison images before updating snapshots to ensure changes are correct.

## Gradle Tasks Reference

| Task | Description |
|------|-------------|
| `recordSnapshots` | Record reference snapshots |
| `verifySnapshots` | Verify snapshots match references |
| `compareSnapshots` | Generate comparison images |
| `cleanSnapshots` | Clean snapshot artifacts |
| `snapshotReport` | Generate snapshot report |

All tasks are in the `snapshot` group.

## Resources

- [Roborazzi Documentation](https://github.com/takahirom/roborazzi)
- [Android Screenshot Testing](https://developer.android.com/studio/preview/compose-screenshot-testing)
- [Robolectric Documentation](http://robolectric.org/)
- [Material3 Theme Documentation](https://m3.material.io/)

## Test Statistics

| Category | Tests | Description |
|----------|-------|-------------|
| **Component Tests** | ~25 | Individual UI elements |
| **ColorPicker Tests** | ~10 | Full picker configurations |
| **Dialog & Config Tests** | ~15 | Dialogs, preferences, devices, themes |
| **TOTAL** | **~50** | **Comprehensive coverage** |

## Contributing

When adding new features or modifying UI:

1. Write snapshot tests for new components
2. Record snapshots: `./gradlew :library:recordSnapshots`
3. Verify tests pass: `./gradlew :library:verifySnapshots`
4. Commit both test code and snapshot images
5. In PRs, use `/record-snapshots` command if needed

## License

The snapshot tests are part of the ColorPicker library and follow the same license.
