StepperViews
============

Android ui-components that implement "stepper behavior"

> You can change component's value without using keyboard
> by only tapping "increase/decrease" or "next/previous" buttons


- written in Kotlin
- `minSdk` = 16


## IntStepperView

```xml
<dev.tonycode.stepperviews.IntStepperView
    android:id="@+id/intStepperView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:isv_value="4"
    app:isv_minValue="0"
    app:isv_maxValue="10"
    app:isv_step="2"
/>
```


## License

[MIT](LICENSE)
