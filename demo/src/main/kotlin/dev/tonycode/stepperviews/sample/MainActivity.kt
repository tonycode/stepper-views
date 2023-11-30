package dev.tonycode.stepperviews.sample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dev.tonycode.stepperviews.sample.databinding.MainActivityBinding
import dev.tonycode.stepperviews.sample.util.onTextChangedFromUi


class MainActivity : AppCompatActivity() {

    private lateinit var vb: MainActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = MainActivityBinding.inflate(layoutInflater)
        setContentView(vb.root)

        vb.intStepperView.apply {
            onChangeListener = { value ->
                Log.d(MainActivity::class.simpleName, "intStepperView.onChangeListener = \"$value\"")

                vb.valueInput.setText(value.toString())
            }

            vb.valueInput.setText(value.toString())
            vb.minValueInput.setText(minValue.toString())
            vb.maxValueInput.setText(maxValue.toString())
            vb.stepInput.setText(step.toString())
        }

        vb.valueInput.onTextChangedFromUi { text ->
            text.toIntOrNull()?.let {
                vb.intStepperView.value = it
            }
        }
        vb.minValueInput.onTextChangedFromUi { text ->
            text.toIntOrNull()?.let {
                vb.intStepperView.minValue = it
            }
        }
        vb.maxValueInput.onTextChangedFromUi { text ->
            text.toIntOrNull()?.let {
                vb.intStepperView.maxValue = it
            }
        }
        vb.stepInput.onTextChangedFromUi { text ->
            text.toIntOrNull()?.let {
                vb.intStepperView.step = it
            }
        }
    }

}
