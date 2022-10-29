package ru.mrpotz.validation

import android.view.View
import androidx.annotation.CheckResult
import ru.mrpotz.validation.*

typealias FormBuilder = Form.() -> Unit
typealias GenericFormField = FormField<*, *, *>
typealias FieldBuilder<T> = T.() -> Unit

/** @author Aidan Follestad (@afollestad) */
/** @author modified: Alexander Gornostaev (@MisterPotz) */
class Form constructor(
    private val container: ValidationContainer,
    val submitEnabled: (Boolean) -> Unit,
) {
    internal var useRealTimeValidation: Boolean = false
    internal var realTimeValidationDebounce: Int = -1
    internal var realTimeDisableSubmit: Boolean = false

    internal var realTimeValidMap: MutableMap<GenericFormField, Boolean>? = null

    private val fieldMap = mutableMapOf<FieldId, GenericFormField>()

    /**
     * Used by [FormField] in real time validation mode to tell the parent [Form] whether or not
     * validation is passing. Aids in decision making when [realTimeDisableSubmit] is true.
     */
    internal fun setFieldIsValid(
        field: GenericFormField,
        valid: Boolean,
    ) {
        realTimeValidMap?.put(field, valid)
        submitEnabled.invoke(!(realTimeValidMap?.any { !it.value } ?: false))
    }

    /** Adds a field to the form. */
    @CheckResult
    fun appendField(fieldId : FieldId, field: GenericFormField): GenericFormField {
        fieldMap[fieldId] = field.apply {
            this.form = this@Form
        }
        return field
    }

    /** Retrieves fields that have been added to the form. */
    fun getFields(): List<GenericFormField> = fieldMap.values.toList()

    /**
     * Enables real-time validation. Views will be observed in real time rather than
     * waiting for Form submission.
     *
     * @param debounce Must be >= 0. Sets a custom delay between a user pausing
     *  interaction with a view and the view being validated. Defaults to a half second (500).
     *  NOTE that certain fields can choose to IGNORE this, like a checkbox.
     * @param disableSubmit When true, the submitWith view is disabled when real time validation
     *  fails. It's re-enabled when it passes.
     */
    fun useRealTimeValidation(
        debounce: Int = 500,
        disableSubmit: Boolean = false,
    ): Form {
        require(debounce >= 0) { "Debounce must be >= 0." }
        useRealTimeValidation = true
        realTimeValidationDebounce = debounce
        if (disableSubmit) {
            realTimeDisableSubmit = true
            realTimeValidMap = mutableMapOf()
        }
        return this
    }

    /** Adds an input field, which must be a [android.widget.EditText]. */
    fun input(
        fieldId : FieldId,
        valueContainer: TextContainer,
        optional: Boolean = false,
        builder: FieldBuilder<InputField>,
    ): GenericFormField {
        val newField = InputField(
            validationContainer = container,
            textContainer = valueContainer,
            fieldID = fieldId
        )
        if (optional) {
            newField.isEmptyOr(builder)
        } else {
            newField.builder()
        }
        return appendField(fieldId, newField)
    }

//  /** Adds a dropdown field, which must be a [android.widget.Spinner]. */
//  fun spinner(
//    view: Spinner,
//    name: String? = null,
//    builder: FieldBuilder<SpinnerField>
//  ): GenericFormField {
//    val newField = SpinnerField(
//        container = container.checkAttached(),
//        view = view,
//        name = name
//    )
//    newField.builder()
//    return appendField(newField)
//  }

    fun checkable(
        fieldId : FieldId,
        checkableContainer: CheckableContainer,
        builder: FieldBuilder<CheckableField>,
    ): GenericFormField {
        val newField = CheckableField(
            validationContainer = container,
            valueContainer = checkableContainer,
            fieldId = fieldId
        )
        newField.builder()
        return appendField(fieldId, newField)
    }


//  /** Adds a AbsSeekBar field, like a [android.widget.SeekBar] or [android.widget.RatingBar]. */
//  fun seeker(
//    view: AbsSeekBar,
//    name: String? = null,
//    builder: FieldBuilder<SeekField>
//  ): GenericFormField {
//    val newField = SeekField(
//        container = container.checkAttached(),
//        view = view,
//        name = name
//    )
//    newField.builder()
//    return appendField(newField)
//  }

    /** Validates all fields in the form. */
    fun validate(fieldToValidate: List<FieldId>? = null, silent: Boolean = false): FormResult {
        val finalResult = FormResult()
        val set = fieldToValidate?.toSet()
        for ((id, field) in fieldMap) {
            if (set != null) {
                if (id in set) {
                    val fieldResult = field.validate()
                    finalResult += fieldResult
                }
            } else {
                val fieldResult = field.validate()
                finalResult += fieldResult
            }
        }
        return finalResult
    }

//    fun validateField(fieldId : FieldId) : FormResult {
//        val field = fieldMap[fieldId]!!
//
//    }

    /**
     * Attaches the form to a view. When the view is clicked, validation is performed. If
     * validation passes, the given callback is invoked.
     */
    fun submitWith(
        view: View,
        onSubmit: (FormResult) -> Unit,
    ) = view.setOnClickListener {
        val result = validate()
        if (result.success()) {
            onSubmit(result)
        }
    }

    suspend fun startValidation()  {
        container.debouncedFlow.collect {
            validate(it)
        }
    }
    /** Signals that the form is finished being built. */
    @CheckResult
    suspend fun startRealTime(): Form {
//        if (useRealTimeValidation) {
//            // Notify all fields that we are using real time validation and they should start observing.
//            fields.forEach { it.startRealTimeValidation(realTimeValidationDebounce) }
//            // Immediately do a validation to see if the submit button should initially be disabled.
//            if (realTimeDisableSubmit && submitWith?.isEnabled == true) {
//                // Silent so that we do not show errors visually.
//                validate(silent = true)
//            }
//        }
        return this
    }
}
