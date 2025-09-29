#!/bin/bash
# Automated screenshot capture script for Play Store listing
# This script helps capture all 8 required screenshots at 1080x1920 resolution

set -e

SCREENSHOTS_DIR="./screenshots"
DEVICE_PATH="/sdcard"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${GREEN}================================${NC}"
echo -e "${GREEN}Color Picker Screenshot Capture${NC}"
echo -e "${GREEN}================================${NC}"
echo ""

# Check if adb is available
if ! command -v adb &> /dev/null; then
    echo -e "${RED}Error: adb command not found${NC}"
    echo "Please install Android SDK Platform Tools"
    exit 1
fi

# Check if device is connected
if ! adb devices | grep -q "device$"; then
    echo -e "${RED}Error: No device connected${NC}"
    echo "Please connect a device or start an emulator"
    exit 1
fi

echo -e "${GREEN}Device connected!${NC}"
echo ""

# Function to capture screenshot
capture() {
    local number=$1
    local name=$2
    local description=$3

    echo -e "${YELLOW}Screenshot $number: $name${NC}"
    echo -e "  ${description}"
    echo ""
    echo "  Navigate to the correct screen state, then press Enter..."
    read

    local filename="${number}_${name}.png"

    adb shell screencap -p $DEVICE_PATH/temp_screenshot.png
    adb pull $DEVICE_PATH/temp_screenshot.png $SCREENSHOTS_DIR/$filename > /dev/null 2>&1
    adb shell rm $DEVICE_PATH/temp_screenshot.png

    echo -e "  ${GREEN}✓ Saved: $filename${NC}"
    echo ""
}

# Ensure screenshots directory exists
mkdir -p $SCREENSHOTS_DIR

# Ask if user wants to set device resolution
echo "Do you want to set device resolution to 1080x1920? (recommended)"
echo -n "Enter 'y' for yes, or press Enter to skip: "
read set_resolution

if [[ $set_resolution == "y" || $set_resolution == "Y" ]]; then
    echo "Setting device resolution..."
    adb shell wm size 1080x1920
    adb shell wm density 480
    echo -e "${GREEN}Resolution set to 1080x1920 @ 480dpi${NC}"
    echo ""
    RESET_RESOLUTION=true
else
    RESET_RESOLUTION=false
fi

echo "Starting screenshot capture process..."
echo "Follow the instructions for each screenshot."
echo ""
sleep 2

# Capture all screenshots with detailed instructions
capture "01" "full_featured" \
    "Tab: Full Featured | Color: Vibrant blue (#2196F3) | Show: All controls"

capture "02" "dialog_picker" \
    "Tab: Dialogs | Action: Tap 'Full Featured Dialog' | Show: Dialog OPEN"

capture "03" "flower_wheel" \
    "Tab: Comparison | Scroll: Show 'Flower Wheel' card prominently"

capture "04" "circle_wheel" \
    "Tab: Comparison | Scroll: Show 'Circle Wheel' card prominently"

capture "05" "preferences" \
    "Tab: Settings | Show: Theme preview + all 4 color preferences"

capture "06" "preference_dialog" \
    "Tab: Settings | Action: Tap 'Primary Color' | Show: Dialog OPEN"

capture "07" "custom_selection" \
    "Tab: Full Featured | Color: Teal/Coral/Purple | Show: Unique color"

capture "08" "dense_wheel" \
    "Tab: Comparison | Scroll: Show 'High Density' wheel card"

echo -e "${GREEN}================================${NC}"
echo -e "${GREEN}All screenshots captured!${NC}"
echo -e "${GREEN}================================${NC}"
echo ""

# Reset device resolution if it was changed
if [ "$RESET_RESOLUTION" = true ]; then
    echo "Resetting device resolution..."
    adb shell wm size reset
    adb shell wm density reset
    echo -e "${GREEN}Device resolution reset${NC}"
    echo ""
fi

# Verify screenshots
echo "Verifying screenshots..."
echo ""

for i in {01..08}; do
    file=$(ls $SCREENSHOTS_DIR/${i}_*.png 2>/dev/null | head -1)
    if [ -f "$file" ]; then
        echo -e "${GREEN}✓${NC} $(basename $file)"
    else
        echo -e "${RED}✗${NC} Screenshot $i missing"
    fi
done

echo ""
echo "Screenshots saved to: $SCREENSHOTS_DIR/"
echo ""

# Check if ImageMagick is available for verification
if command -v identify &> /dev/null; then
    echo "Checking resolutions..."
    for file in $SCREENSHOTS_DIR/[0-9][0-9]_*.png; do
        if [ -f "$file" ]; then
            resolution=$(identify -format "%wx%h" "$file")
            if [ "$resolution" = "1080x1920" ]; then
                echo -e "${GREEN}✓${NC} $(basename $file): $resolution"
            else
                echo -e "${YELLOW}⚠${NC} $(basename $file): $resolution (expected 1080x1920)"
            fi
        fi
    done
    echo ""
else
    echo "Install ImageMagick to verify screenshot resolutions:"
    echo "  brew install imagemagick"
    echo ""
fi

echo "Next steps:"
echo "  1. Review screenshots in $SCREENSHOTS_DIR/"
echo "  2. Check against requirements in SCREENSHOT_GUIDE.md"
echo "  3. Upload to Google Play Console"
echo ""
echo -e "${GREEN}Done!${NC}"