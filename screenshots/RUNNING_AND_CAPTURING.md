# Running the Sample App and Capturing Screenshots

This guide explains how to run the Compose sample app and capture the Play Store screenshots.

## Prerequisites

- Android Studio installed
- Android SDK with API 24+
- An Android emulator or physical device

## Step 1: Build the APK

The app has already been built successfully. The debug APK is located at:
```
app/build/outputs/apk/debug/app-debug.apk
```

## Step 2: Setup Device/Emulator

### Option A: Using Android Studio Emulator

1. Open Android Studio
2. Go to Tools → Device Manager
3. Create a new virtual device or use existing one
4. Configure device settings:
   - **Resolution**: 1080 x 1920
   - **Density**: 480 dpi (xxhdpi)
   - **API Level**: 24 or higher
5. Start the emulator

### Option B: Using Physical Device

1. Enable Developer Options on your device
2. Enable USB Debugging
3. Connect device via USB
4. If needed, adjust display settings:
   ```bash
   adb shell wm size 1080x1920
   adb shell wm density 480
   ```

## Step 3: Install and Run the App

### Using Android Studio:
1. Open the project in Android Studio
2. Select your device/emulator from the device dropdown
3. Click the Run button (▶️) or press Shift+F10
4. Wait for the app to install and launch

### Using ADB:
```bash
# Install the APK
adb install app/build/outputs/apk/debug/app-debug.apk

# Launch the app
adb shell am start -n co.csadev.colorpicker.sample/co.csadev.colorpicker.sample.MainActivity
```

## Step 4: Capture Screenshots

The app has 4 tabs at the bottom:
1. **Full Featured** - Complete color picker with all controls
2. **Dialogs** - Dialog examples
3. **Comparison** - Wheel type comparisons
4. **Settings** - DataStore preference integration

### Required Screenshots (1080x1920 each):

#### 1. Full Featured Screen (`01_full_featured.png`)
- Navigate to "Full Featured" tab (should be default)
- Select a vibrant blue color (#2196F3)
- Ensure color wheel, sliders, and color preview are all visible
- Capture screenshot

#### 2. Dialog Example (`02_dialog_picker.png`)
- Navigate to "Dialogs" tab
- Tap on "Full Featured Dialog" card
- Wait for dialog to open showing the color picker
- Capture screenshot with dialog open

#### 3. Flower Wheel (`03_flower_wheel.png`)
- Navigate to "Comparison" tab
- Scroll to ensure "Flower Wheel" card is fully visible
- Capture screenshot showing the flower-style wheel

#### 4. Circle Wheel (`04_circle_wheel.png`)
- Stay on "Comparison" tab
- Scroll to show "Circle Wheel" card prominently
- Capture screenshot showing the circle-style wheel

#### 5. Preferences Screen (`05_preferences.png`)
- Navigate to "Settings" tab
- Ensure theme preview and all 4 color preferences are visible
- Capture screenshot

#### 6. Preference Dialog (`06_preference_dialog.png`)
- Stay on "Settings" tab
- Tap on "Primary Color" preference
- Wait for color picker dialog to open
- Capture screenshot with dialog open

#### 7. Custom Color Selection (`07_custom_selection.png`)
- Navigate back to "Full Featured" tab
- Select an interesting color like teal (#009688) or coral (#FF7043)
- Adjust sliders to show variation
- Capture screenshot

#### 8. Dense Wheel Detail (`08_dense_wheel.png`)
- Navigate to "Comparison" tab
- Scroll to "High Density (More Detail)" card
- Capture screenshot showing the dense wheel

### Capture Methods:

#### Method 1: Android Studio Emulator
- Click the camera icon in the emulator toolbar
- Or press Cmd+S (Mac) / Ctrl+S (Windows)
- Screenshots save to Desktop by default

#### Method 2: Using ADB
```bash
# Capture and pull screenshot
adb shell screencap -p /sdcard/screenshot.png
adb pull /sdcard/screenshot.png ./screenshots/01_full_featured.png
adb shell rm /sdcard/screenshot.png
```

#### Method 3: Physical Device
- Use device's built-in screenshot method (usually Power + Volume Down)
- Transfer files via USB or cloud storage

## Step 5: Verify Screenshots

Check that all screenshots:
- [ ] Are exactly 1080x1920 pixels
- [ ] Are in PNG format
- [ ] Show the correct content
- [ ] Have good color representation
- [ ] Are free of system notifications
- [ ] Have reasonable system time and battery level

### Verify Resolution:
```bash
# Using ImageMagick
identify screenshots/*.png

# Should show: PNG 1080x1920 for each file
```

## Step 6: Reset Device (if needed)

If you changed the device resolution, reset it:
```bash
adb shell wm size reset
adb shell wm density reset
```

## Troubleshooting

### App Won't Install
```bash
# Uninstall old version first
adb uninstall co.csadev.colorpicker.sample
# Then reinstall
adb install app/build/outputs/apk/debug/app-debug.apk
```

### Wrong Resolution
- Check device settings in Android Studio Device Manager
- Or manually set via ADB commands above

### Screenshots Too Large/Small
- Verify device resolution is exactly 1080x1920
- Use ImageMagick to resize if needed:
  ```bash
  convert screenshot.png -resize 1080x1920 screenshot_resized.png
  ```

### Colors Look Different
- Use emulator with software rendering for accurate colors
- Disable any color filters or night mode on physical device

## Next Steps

Once you have all 8 screenshots:
1. Review them against the checklist in SCREENSHOT_GUIDE.md
2. Add device frames if desired (optional)
3. Prepare them for Play Store upload
4. Update the Play Store listing with the suggested text from SCREENSHOT_GUIDE.md

## Sample ADB Script

Here's a complete script to capture all screenshots:

```bash
#!/bin/bash
# capture_screenshots.sh

SCREENSHOTS_DIR="./screenshots"
DEVICE_PATH="/sdcard"

capture() {
    local name=$1
    echo "Capturing $name..."
    echo "Please navigate to the correct screen and press Enter when ready..."
    read
    adb shell screencap -p $DEVICE_PATH/$name.png
    adb pull $DEVICE_PATH/$name.png $SCREENSHOTS_DIR/
    adb shell rm $DEVICE_PATH/$name.png
    echo "Saved $name"
}

# Ensure screenshots directory exists
mkdir -p $SCREENSHOTS_DIR

# Set device resolution
echo "Setting device resolution to 1080x1920..."
adb shell wm size 1080x1920
adb shell wm density 480

# Capture all screenshots
capture "01_full_featured"
capture "02_dialog_picker"
capture "03_flower_wheel"
capture "04_circle_wheel"
capture "05_preferences"
capture "06_preference_dialog"
capture "07_custom_selection"
capture "08_dense_wheel"

# Reset device resolution
echo "Resetting device resolution..."
adb shell wm size reset
adb shell wm density reset

echo "All screenshots captured!"
```

Make it executable and run:
```bash
chmod +x capture_screenshots.sh
./capture_screenshots.sh
```