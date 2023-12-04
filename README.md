StepperViews
============

[![Platform](http://img.shields.io/badge/platform-android-brightgreen.svg?style=flat)](https://developer.android.com)
[![Language](http://img.shields.io/badge/language-kotlin-blue.svg?style=flat)](https://kotlinlang.org)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![API](https://img.shields.io/badge/API-16%2B-blue.svg?style=flat)](https://apilevels.com)
[![Release](https://jitpack.io/v/tonycode/stepper-views.svg)](https://jitpack.io/#tonycode/stepper-views)

Android ui-components that implement "stepper behavior"

> You can change component's value without using keyboard
> by only tapping "increase/decrease" or "next/previous" buttons


## Gradle

```kotlin
repositories {
    //...
    maven("https://jitpack.io")
}
```

```kotlin
dependencies {
    //...
    implementation("com.github.tonycode:stepper-views:0.1.0")
}
```


## IntStepperView

A stepper-view that handle integer values

User can click on increase/decrease buttons or directly input a value (as in `android.widget.EditText`)

> ![demo-light](docs/demo-light_1-5.gif)
>  - `minValue = 1`
>  - `maxValue = 5`

> ![demo-dark](docs/demo-dark_0-10-s2.gif)
>  - `minValue = 0`
>  - `maxValue = 10`
>  - `step = 2`

```xml
<dev.tonycode.stepperviews.IntStepperView
    android:id="@+id/intStepperView1"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:isv_value="4"
    app:isv_minValue="0"
    app:isv_maxValue="10"
    app:isv_step="2"
    />
```

```kotlin
// configure programmatically
vb.intStepperView1.apply {
    value = 0
    minValue = -100
    maxValue = 100
    step = 10
}

// listen to value changes
vb.intStepperView1.onChangeListener = { value: Int ->
    Log.d(TAG, "value is $value")
}
```

| xml-attribute  | property           | description                                            |  default value  |
|:---------------|:-------------------|:-------------------------------------------------------|:---------------:|
| `isv_value`    | `value`            | Current value displayed in this stepper-view           |       `0`       |
| `isv_minValue` | `minValue`         | The `value` property can't be less than this number    | `Int.MIN_VALUE` |
| `isv_maxValue` | `maxValue`         | The `value` property can't be greater than this number | `Int.MAX_VALUE` |
| `isv_step`     | `step`             | The step to increase/decrease `value` property         |       `1`       |
|                | `onChangeListener` | Callback upon `value` property change                  |     `null`      |


## License

[MIT](LICENSE)
