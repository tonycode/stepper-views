StepperViews
============

![Release](https://jitpack.io/v/tonycode/stepper-views.svg)

Android ui-components that implement "stepper behavior"

> You can change component's value without using keyboard
> by only tapping "increase/decrease" or "next/previous" buttons


- written in Kotlin
- `minSdk` = 16


## Usage

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


## License

[MIT](LICENSE)
