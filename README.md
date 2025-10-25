Color Picker
-------------
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Color%20Picker-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1693)
![https://img.shields.io/github/tag/QuadFlask/colorpicker.svg?label=maven](https://img.shields.io/github/tag/QuadFlask/colorpicker.svg?label=maven)

![icon](https://github.com/QuadFlask/colorpicker/blob/master/app/src/main/res/drawable-xxxhdpi/ic_launcher.png)

simple android color picker with color wheel and lightness bar.

[<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/c/cd/Get_it_on_Google_play.svg/800px-Get_it_on_Google_play.svg.png" width="150px">](https://play.google.com/store/apps/details?id=co.csadev.colorpicker.sample)

[market link](https://play.google.com/store/apps/details?id=co.csadev.colorpicker.sample)

## Demo video

[Youtube](https://youtu.be/MwWi9X7eqNI)

## Screenshot

### WHEEL_TYPE.FLOWER

![screenshot3.png](https://github.com/QuadFlask/colorpicker/blob/master/screenshot/screenshot3.png)

### WHEEL_TYPE.CIRCLE

![screenshot.png](https://github.com/QuadFlask/colorpicker/blob/master/screenshot/screenshot.png)

## How to add dependency?

This library is not released in Maven Central, but instead you can use [JitPack](https://jitpack.io)

add remote maven url in `allprojects.repositories`

```groovy
allprojects {
	repositories {
		maven { url "https://jitpack.io" }
	}
}
```

then add a library dependency

```groovy
dependencies {
	compile 'com.github.QuadFlask:colorpicker:0.0.13'
}
```

or, you can manually download `aar` and put into your project's `libs` directory.

and add dependency

```groovy
dependencies {
	compile(name:'[arrFileName]', ext:'aar')
}
```

> check out latest version at [releases](https://github.com/QuadFlask/colorpicker/releases)

## Usage

As a dialog

```java
ColorPickerDialogBuilder
	.with(context)
	.setTitle("Choose color")
	.initialColor(currentBackgroundColor)
	.wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
	.density(12)
	.setOnColorSelectedListener(new OnColorSelectedListener() {
		@Override
		public void onColorSelected(int selectedColor) {
			toast("onColorSelected: 0x" + Integer.toHexString(selectedColor));
		}
	})
	.setPositiveButton("ok", new ColorPickerClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
			changeBackgroundColor(selectedColor);
		}
	})
	.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
		}
	})
	.build()
	.show();
```

As a widget

```xml
	<co.csadev.colorpicker.ColorPickerView
		android:id="@+id/color_picker_view"
		android:layout_width="match_parent"
		android:layout_height="wrap_content"
		app:alphaSlider="true"
		app:density="12"
		app:lightnessSlider="true"
		app:wheelType="FLOWER"
		app:lightnessSliderView="@+id/v_lightness_slider"
	    app:alphaSliderView="@+id/v_alpha_slider"
		/>

	<co.csadev.colorpicker.slider.LightnessSlider
		android:id="@+id/v_lightness_slider"
		android:layout_width="match_parent"
		android:layout_height="48dp"
		/>

	<co.csadev.colorpicker.slider.AlphaSlider
		android:id="@+id/v_alpha_slider"
		android:layout_width="match_parent"
		android:layout_height="48dp"
		/>
```

## Testing

This library includes comprehensive snapshot testing using [Paparazzi](https://github.com/cashapp/paparazzi) to ensure UI consistency across:

- ✅ **~110+ snapshot tests** covering all UI components
- ✅ **Multiple device types** (phones, tablets, various screen densities)
- ✅ **14 languages** including RTL support (Arabic, Hebrew)
- ✅ **Light and dark themes** plus custom Material3 themes
- ✅ **Automated CI/CD** with GitHub Actions

### Running Tests

```bash
# Record reference snapshots
./gradlew :library:recordSnapshots

# Verify snapshots match
./gradlew :library:verifySnapshots

# Generate snapshot report
./gradlew :library:snapshotReport
```

For detailed testing documentation, see [library/SNAPSHOT_TESTING.md](library/SNAPSHOT_TESTING.md).

## Development

### Snapshot Testing

The library uses snapshot testing to prevent visual regressions. All UI components are tested across multiple configurations to ensure consistent rendering.

**Test Coverage:**
- Component Tests (~35): Individual UI elements
- ColorPicker Tests (~15): Full picker configurations
- Dialog Tests (~15): Dialogs and preferences
- Device Tests (~20): Multiple devices and orientations
- Locale Tests (~14): Language and RTL support
- Theme Tests (~12): Material3 theme variations

See the [Snapshot Testing Guide](library/SNAPSHOT_TESTING.md) for more information.

## To do

* gradle support
* performance improvement
* refactoring

## License

```
Copyright 2014-2017 QuadFlask

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
