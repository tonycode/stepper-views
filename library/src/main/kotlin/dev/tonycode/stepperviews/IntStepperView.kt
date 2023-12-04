package dev.tonycode.stepperviews

import android.content.Context
import android.content.res.TypedArray
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.SparseArray
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.children
import dev.tonycode.stepperviews.databinding.IntStepperViewBinding
import dev.tonycode.stepperviews.util.disable
import dev.tonycode.stepperviews.util.enable
import dev.tonycode.stepperviews.util.onTextChangedFromUi
import kotlin.math.max
import kotlin.math.min


/**
 * A stepper-view that handle integer values.
 *
 * User can click on increase/decrease buttons or directly input a value (as in [android.widget.EditText]).
 *
 * Component's properties:
 * - [value] - the current (displayed) value
 *     - default: `0`
 * - (optional) [minValue] and [maxValue] - limits the possible [value] range
 *     - default: `Int.MIN_VALUE` and `Int.MAX_VALUE` accordingly
 * - (optional) [step] - the delta by which [value] will be increased/decreased
 *     - default: `1`
 */
class IntStepperView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    //region public props
    /**
     * Current value displayed in this stepper-view
     */
    var value: Int = DEFAULT_VALUE
        set(newValue) {
            if (field == newValue) return

            val clampedValue =  // apply limits
                max(minValue, min(newValue, maxValue))

            field = clampedValue
            onValueChanged(clampedValue)
        }

    /**
     * The [value] can't be less than this number
     *
     * - if `(value - step < minValue)` - decrease button will be disabled
     * - if user enters a value less than this - it will be replaced with `minValue`
     */
    var minValue: Int = DEFAULT_MIN_VALUE
        set(newMinValue) {
            if (field == newMinValue) return
            field = newMinValue

            if (value < newMinValue) {
                value = newMinValue
            }
            updateButtonsState()
        }

    /**
     * The [value] can't be greater than this number
     *
     * - if `(value + step > maxValue)` - increase button will be disabled
     * - if user enters a value less than this - it will be replaced with `maxValue`
     */
    var maxValue: Int = DEFAULT_MAX_VALUE
        set(newMaxValue) {
            if (field == newMaxValue) return
            field = newMaxValue

            if (value > newMaxValue) {
                value = newMaxValue
            }
            updateButtonsState()
        }

    /**
     * The step to increase/decrease [value]
     */
    var step: Int = DEFAULT_STEP
        set(newStep) {
            if (field == newStep) return
            field = newStep
            onStepChanged()
        }

    /**
     * Callback upon [value] change
     */
    var onChangeListener: ((value: Int) -> Unit)? = null
    //endregion

    private val vb: IntStepperViewBinding


    init {
        vb = IntStepperViewBinding.inflate(LayoutInflater.from(context), this)

        orientation = HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL

        onValueChanged(DEFAULT_VALUE)

        context.obtainStyledAttributes(attrs, R.styleable.IntStepperView, defStyleAttr, 0).let { ta ->
            applyAttrs(ta)
            ta.recycle()
        }

        vb.buttonDecrease.setOnClickListener {
            value -= step
            onChangeListener?.invoke(value)
        }
        vb.buttonIncrease.setOnClickListener {
            value += step
            onChangeListener?.invoke(value)
        }

        vb.editTextValue.onTextChangedFromUi { text ->
            text.toIntOrNull()?.let {
                value = it
                onChangeListener?.invoke(it)
            }
        }
    }

    private fun applyAttrs(ta: TypedArray) {
        for (i in 0 until ta.indexCount) {
            when (val attrIdx = ta.getIndex(i)) {
                R.styleable.IntStepperView_isv_value ->
                    value = ta.getInt(attrIdx, DEFAULT_VALUE)
                R.styleable.IntStepperView_isv_minValue ->
                    minValue = ta.getInt(attrIdx, DEFAULT_MIN_VALUE)
                R.styleable.IntStepperView_isv_maxValue ->
                    maxValue = ta.getInt(attrIdx, DEFAULT_MAX_VALUE)
                R.styleable.IntStepperView_isv_step ->
                    step = ta.getInt(attrIdx, DEFAULT_STEP)
            }
        }
    }

    private fun onValueChanged(newValue: Int) {
        vb.editTextValue.setText(newValue.toString())
        updateButtonsState()
    }

    private fun onStepChanged() {
        updateButtonsState()
    }

    /**
     * Update increase/decrease buttons enable-ability
     */
    private fun updateButtonsState() {
        vb.buttonDecrease.let {
            if (value >= (minValue + step)) it.enable() else it.disable()
        }
        vb.buttonIncrease.let {
            if (value <= (maxValue - step)) it.enable() else it.disable()
        }
    }

    //region lifecycle callbacks
    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState() ?: Bundle()

        val savedState = IntStepperViewState(superState)
        savedState.value = value
        savedState.minValue = minValue
        savedState.maxValue = maxValue
        savedState.step = step
        for (child in children) {
            child.saveHierarchyState(savedState.childrenStates)
        }

        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state !is IntStepperViewState) {
            super.onRestoreInstanceState(state)
            return
        }

        value = state.value
        minValue = state.minValue
        maxValue = state.maxValue
        step = state.step

        super.onRestoreInstanceState(state.superState)
        for (child in children) {
            child.restoreHierarchyState(state.childrenStates)
        }
    }

    /**
     * We save children state manually, see [IntStepperViewState.childrenStates]
     */
    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>?) {
        dispatchFreezeSelfOnly(container)
    }

    /**
     * We restore children state manually, see [IntStepperViewState.childrenStates]
     */
    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>?) {
        dispatchThawSelfOnly(container)
    }
    //endregion


    /**
     * Internal saved state
     */
    internal class IntStepperViewState : BaseSavedState {
        var value: Int = DEFAULT_VALUE
        var minValue: Int = DEFAULT_MIN_VALUE
        var maxValue: Int = DEFAULT_MAX_VALUE
        var step: Int = DEFAULT_STEP

        var childrenStates: SparseArray<Parcelable> = SparseArray()

        /**
         * Called from [IntStepperView.onSaveInstanceState]
         */
        constructor(superState: Parcelable) : super(superState)

        /**
         * Called from [CREATOR]
         */
        private constructor(parcel: Parcel) : super(parcel) {
            value = parcel.readInt()
            minValue = parcel.readInt()
            maxValue = parcel.readInt()
            step = parcel.readInt()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeInt(value)
            out.writeInt(minValue)
            out.writeInt(maxValue)
            out.writeInt(step)
        }

        override fun toString(): String {
            return "IntStepperViewState(" +
                "value=$value, minValue=$minValue, maxValue=$maxValue, step=$step" +
                ")"
        }

        companion object CREATOR : Parcelable.Creator<IntStepperViewState> {
            override fun createFromParcel(parcel: Parcel): IntStepperViewState {
                return IntStepperViewState(parcel)
            }

            override fun newArray(size: Int): Array<IntStepperViewState?> {
                return arrayOfNulls(size)
            }
        }
    }

    companion object {
        const val DEFAULT_VALUE: Int = 0
        const val DEFAULT_MIN_VALUE: Int = Int.MIN_VALUE
        const val DEFAULT_MAX_VALUE: Int = Int.MAX_VALUE
        const val DEFAULT_STEP: Int = 1
    }

}
