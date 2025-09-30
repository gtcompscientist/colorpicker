# Screenshot Guide for Play Store Listing

This guide explains how to capture and prepare screenshots for the Google Play Store listing.

## Requirements

- **Resolution**: 1080x1920 pixels (Phone Portrait)
- **Format**: PNG
- **Maximum file size**: 8MB per screenshot
- **Minimum screenshots**: 2
- **Maximum screenshots**: 8

## Capturing Screenshots

### Method 1: Using Android Studio/Emulator

1. Open the sample app in Android Studio
2. Run the app on an emulator or device configured to 1080x1920 resolution
3. Use the camera button in the emulator toolbar or `Cmd+S` (Mac) / `Ctrl+S` (Windows)
4. Screenshots will be saved to `~/Desktop` or your configured location

### Method 2: Using ADB

```bash
# Capture screenshot
adb shell screencap -p /sdcard/screenshot.png

# Pull to computer
adb pull /sdcard/screenshot.png ./screenshots/

# Delete from device
adb shell rm /sdcard/screenshot.png
```

### Method 3: Physical Device

1. Configure device display to 1080x1920 in Developer Options
2. Take screenshot using device's native method (usually Power + Volume Down)
3. Transfer to computer via USB or cloud storage

## Screenshot List

Capture the following screens for the Play Store:

### 1. Full Featured Screen (`01_full_featured.png`)

- **Tab**: Full Featured (first tab)
- **State**: Showing complete color picker with flower wheel
- **What to show**:
    - Interactive color wheel prominently displayed
    - All sliders visible (lightness + alpha)
    - Hex color code input field
    - Selected color preview card at top
    - RGB/A values displayed
- **Color to select**: Vibrant blue (#2196F3) or similar eye-catching color

### 2. Dialog Example (`02_dialog_picker.png`)

- **Tab**: Dialogs (second tab)
- **State**: One of the dialog cards OPEN showing the picker
- **What to show**:
    - Material3 dialog with color picker inside
    - Dialog title "Choose Color"
    - OK and Cancel buttons
    - Color wheel visible in dialog
- **Recommended**: Use the "Full Featured Dialog" option

### 3. Comparison Screen - Flower (`03_flower_wheel.png`)

- **Tab**: Comparison (third tab)
- **State**: Scrolled to show the Flower Wheel card clearly
- **What to show**:
    - Title "Flower Wheel" clearly visible
    - Full flower-style color wheel
    - Description text
    - "Best for" recommendation text

### 4. Comparison Screen - Circle (`04_circle_wheel.png`)

- **Tab**: Comparison (third tab)
- **State**: Scrolled to show the Circle Wheel card clearly
- **What to show**:
    - Title "Circle Wheel" clearly visible
    - Full circle-style color wheel
    - Description text
    - Clean, uniform appearance

### 5. Preferences/Settings (`05_preferences.png`)

- **Tab**: Settings (fourth tab)
- **State**: Showing all color preferences
- **What to show**:
    - Theme preview card at top showing all colors
    - List of preferences (Primary, Secondary, Accent, Background)
    - Color indicators next to each preference
    - Hex codes visible
    - DataStore integration info card

### 6. Preference Dialog Open (`06_preference_dialog.png`)

- **Tab**: Settings (fourth tab)
- **State**: One preference dialog OPEN
- **What to show**:
    - Settings screen in background
    - Color picker dialog overlay
    - Preference being edited (e.g., "Primary Color")
    - Full color picker with wheel

### 7. Custom Color Selection (`07_custom_selection.png`)

- **Tab**: Full Featured
- **State**: User has selected an interesting color
- **What to show**:
    - Unique color selected (e.g., coral, teal, or purple)
    - Sliders adjusted to show different values
    - Color preview showing the selected color
    - Hex code clearly readable

### 8. Dense Wheel Detail (`08_dense_wheel.png`)

- **Tab**: Comparison
- **State**: Scrolled to "High Density" wheel
- **What to show**:
    - High-density wheel with many color points
    - Title "High Density (More Detail)"
    - Demonstrates the granular color selection

## Screenshot Naming Convention

Use the following naming pattern:

```
[number]_[screen_name].png
```

Examples:

- `01_full_featured.png`
- `02_dialog_picker.png`
- `03_flower_wheel.png`

## Post-Processing (Optional)

### Using ImageMagick

```bash
# Verify resolution
identify screenshot.png

# Resize if needed (maintain aspect ratio)
convert screenshot.png -resize 1080x1920 screenshot_resized.png

# Add device frame (optional, using device-frame-generator)
# https://developer.android.com/distribute/marketing-tools/device-art-generator
```

### Using Android Asset Studio

Visit: https://developer.android.com/distribute/marketing-tools/device-art-generator

- Upload your screenshots
- Choose "Phone" device
- Download with frame

## Tips for Best Screenshots

1. **Use vibrant colors**: Choose eye-catching colors that showcase the picker's capabilities
2. **Clean UI**: No system notifications, set device to airplane mode
3. **Good lighting**: If using physical device, ensure even lighting
4. **Focus on features**: Each screenshot should highlight a specific feature
5. **Consistent state**: Keep system bars and navigation consistent across screenshots
6. **Portrait orientation**: All screenshots must be in portrait mode (1080x1920)

## Checklist Before Uploading

- [ ] All screenshots are exactly 1080x1920 pixels
- [ ] Files are in PNG format
- [ ] Each file is under 8MB
- [ ] Screenshots show different features/screens
- [ ] No personal information visible
- [ ] System time shows reasonable time (not 3 AM!)
- [ ] Battery level is reasonable (not 2%)
- [ ] Screenshots are numbered in logical order
- [ ] Colors are vibrant and showcase the library well

## Play Store Listing Text Suggestions

### Short Description (80 characters max)

"Modern Jetpack Compose color picker with interactive wheels and Material3 design"

### Full Description

```
🎨 Color Picker Library - Compose Edition

A powerful, modern color picker library for Jetpack Compose with beautiful interactive color wheels, sliders, and Material3 integration.

✨ FEATURES

• Interactive color wheels (Flower & Circle styles)
• Real-time color sliders (Lightness & Alpha)
• Hex color code input/output
• Material3 dialogs
• DataStore preferences integration
• Fully Compose-native
• Customizable density and appearance
• State management with Flows
• Event handling with coroutines

🚀 EASY TO USE

Choose between organic flower-style wheels or precise circle wheels. Adjust lightness and transparency with smooth sliders. Save preferences with built-in DataStore support.

📱 MODERN ARCHITECTURE

Built with Kotlin coroutines, Flows, and Compose from the ground up. Uses modern state management and Material3 components.

💡 FLEXIBLE INTEGRATION

Use as full picker, dialog, or individual components. Customize colors, wheel type, and available controls for your needs.

Perfect for design apps, theme customization, creative tools, and any app needing color selection!
```

## Screenshot Order for Play Store

Recommended order for maximum impact:

1. Full Featured Screen - Shows complete capability
2. Dialog Picker - Shows practical usage
3. Flower Wheel - Shows unique feature
4. Circle Wheel - Shows alternative style
5. Preferences - Shows DataStore integration
6. Custom Selection - Shows flexibility
7. Preference Dialog - Shows dialog integration
8. Dense Wheel - Shows detail capability

## Capturing the Screenshots

### Quick Command Reference

```bash
# Set device to correct resolution first
adb shell wm size 1080x1920
adb shell wm density 480

# Capture each screenshot
adb shell screencap -p /sdcard/01_full_featured.png && adb pull /sdcard/01_full_featured.png screenshots/
adb shell screencap -p /sdcard/02_dialog_picker.png && adb pull /sdcard/02_dialog_picker.png screenshots/
# ... continue for each screenshot

# Reset to original resolution when done
adb shell wm size reset
adb shell wm density reset
```