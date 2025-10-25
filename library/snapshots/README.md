# ColorPicker Snapshot Tests

This directory contains reference snapshots (baseline images) for the ColorPicker library's UI components.

## Quick Start

### Generate Reference Snapshots

```bash
./gradlew :library:recordSnapshots
```

This will create ~110+ PNG snapshots in organized subdirectories.

### Verify Snapshots

```bash
./gradlew :library:verifySnapshots
```

This compares current UI rendering against the reference snapshots.

## Directory Structure

After recording snapshots, this directory will contain:

```
snapshots/
├── images/
│   ├── co.csadev.colorpicker.snapshots_ComponentSnapshotTests_*.png
│   ├── co.csadev.colorpicker.snapshots_ColorPickerSnapshotTests_*.png
│   ├── co.csadev.colorpicker.snapshots_DialogSnapshotTests_*.png
│   ├── co.csadev.colorpicker.snapshots_DeviceConfigurationSnapshotTests_*.png
│   ├── co.csadev.colorpicker.snapshots_LocaleSnapshotTests_*.png
│   └── co.csadev.colorpicker.snapshots_ThemeSnapshotTests_*.png
└── README.md (this file)
```

## Snapshot Categories

### 1. Component Snapshots (~35 images)
Individual UI components:
- ColorWheel (Flower and Circle types, various densities and lightness values)
- LightnessSlider (different brightness levels and colors)
- AlphaSlider (different opacity levels and colors)
- ColorPreviewBox (various colors)

### 2. ColorPicker Snapshots (~15 images)
Complete ColorPicker configurations:
- Full-featured picker
- Various feature combinations
- Different wheel types
- Different density settings
- Various initial colors

### 3. Dialog Snapshots (~15 images)
Dialog and preference components:
- ColorPickerDialog (full-featured, minimal, custom)
- ColorPickerPreferenceItem (enabled, disabled, with/without summary)

### 4. Device Configuration Snapshots (~20 images)
Different devices and configurations:
- Pixel 5, Nexus 4, Pixel 6 Pro (phones)
- Nexus 7, Nexus 10 (tablets)
- Portrait and landscape orientations
- HDPI, XXHDPI, XXXHDPI densities

### 5. Locale Snapshots (~14 images)
Multiple languages and locales:
- English, Spanish, French, German, Italian
- Portuguese, Japanese, Korean, Chinese (Simplified/Traditional)
- Arabic and Hebrew (RTL support)
- Russian, Hindi

### 6. Theme Snapshots (~12 images)
Material3 theme variations:
- Light and dark themes
- Custom color schemes (blue, purple, green)
- High contrast theme

## File Naming Convention

Snapshot filenames follow this pattern:
```
{package}_{testClass}_{testMethod}.png
```

Example:
```
co.csadev.colorpicker.snapshots_ComponentSnapshotTests_colorWheel_flowerType_default.png
```

This makes it easy to:
- Identify which test generated each snapshot
- Organize snapshots by test class
- Find snapshots for specific test cases

## Snapshot Resolution

All snapshots are rendered at the device's native resolution and DPI. For example:
- Pixel 5: 1080 x 2340 px at 440 DPI
- Nexus 7: 1200 x 1920 px at 320 DPI
- Nexus 10: 1600 x 2560 px at 320 DPI

## Version Control

### What to Commit

✅ **DO commit:**
- Reference snapshot images (this directory)
- Test code changes
- Documentation updates

❌ **DON'T commit:**
- `snapshots-compare/` directory
- `out/` directory
- Temporary test artifacts

### Git LFS (Optional)

For large repositories, consider using Git LFS for snapshot images:

```bash
git lfs track "library/src/test/snapshots/**/*.png"
git add .gitattributes
```

## Snapshot Updates

### When to Update Snapshots

Update reference snapshots when:
- ✅ UI changes are intentional (e.g., design updates)
- ✅ New components are added
- ✅ Component configurations change

### How to Update Snapshots

1. Make your UI changes
2. Record new snapshots:
   ```bash
   ./gradlew :library:recordSnapshots
   ```
3. Verify the snapshots look correct by reviewing the PNG files
4. Commit the updated snapshots:
   ```bash
   git add library/src/test/snapshots/
   git commit -m "Update snapshots for [describe change]"
   ```

## CI/CD Integration

Snapshots are automatically verified in CI/CD:
- GitHub Actions workflow runs on every PR
- Compares current UI against committed snapshots
- Fails build if any differences are detected
- Uploads failure reports as artifacts

See `.github/workflows/snapshot-tests.yml` for details.

## Troubleshooting

### Missing Snapshots

If snapshots are missing, record them:
```bash
./gradlew :library:recordSnapshots
```

### Test Failures

If snapshot tests fail:

1. **Review the differences:**
   - Check `library/out/failures/` for failure images
   - Download artifacts from CI build

2. **If changes are intentional:**
   ```bash
   ./gradlew :library:recordSnapshots
   git add library/src/test/snapshots/
   git commit -m "Update snapshots"
   ```

3. **If changes are unintentional:**
   - Fix the UI regression
   - Verify tests pass: `./gradlew :library:verifySnapshots`

### Storage Size

If snapshot directory becomes too large:

1. **Use Git LFS** (recommended for repos with many images)
2. **Compress images** (Paparazzi already optimizes PNGs)
3. **Remove obsolete snapshots** when tests are deleted

## Documentation

For comprehensive documentation, see:
- [SNAPSHOT_TESTING.md](../SNAPSHOT_TESTING.md) - Complete snapshot testing guide
- [GitHub Workflow](.github/workflows/snapshot-tests.yml) - CI/CD configuration

## Statistics

| Category | Count | Description |
|----------|-------|-------------|
| Component Tests | ~35 | Individual UI elements |
| ColorPicker Tests | ~15 | Full picker configurations |
| Dialog Tests | ~15 | Dialogs and preferences |
| Device Tests | ~20 | Multiple devices and configs |
| Locale Tests | ~14 | Language and RTL support |
| Theme Tests | ~12 | Theme variations |
| **TOTAL** | **~110+** | **Complete UI coverage** |

## Performance

Snapshot test execution time:
- **Local:** ~2-3 minutes for all tests
- **CI:** ~3-5 minutes including setup

No emulator or physical device required - all tests run on the JVM!

## Contributing

When adding new UI components or features:

1. Write snapshot tests in the appropriate test file
2. Record snapshots: `./gradlew :library:recordSnapshots`
3. Verify tests pass: `./gradlew :library:verifySnapshots`
4. Commit both test code and snapshots
5. Update documentation if needed

## License

These snapshots are part of the ColorPicker library and follow the same license.
