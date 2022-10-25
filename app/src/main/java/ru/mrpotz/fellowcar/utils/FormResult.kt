package ru.mrpotz.fellowcar.utils

/**
 * Holds the validation result of a whole form.
 *
 * @author Aidan Follestad (@afollestad)
 */
class FormResult {
    private val errors = mutableListOf<FieldError>()
//    private val values = mutableMapOf<String, FieldValue<*>>()

    /** Returns true if validation passed with no errors. */
    fun success() = errors.isEmpty()

    /** Returns true if validation failed with errors. */
    fun hasErrors() = errors.isNotEmpty()

    /** Returns errors that occurred during validation. */
    fun errors(): List<FieldError> = errors

    /** Gets all form field values as a list. */
//    fun values(): List<FieldValue<*>> = values.values.toList()

    /** Retrieves the snapshot value of a field in the form. */
//    operator fun get(name: String): FieldValue<*>? = values[name]

    /** Merges a [FieldResult] into this result. */
    operator fun plusAssign(fieldResult: ValidationResult) {
        when (fieldResult) {
            is ValidationResult.FieldResult<*> -> errors.addAll(fieldResult.errors())
            ValidationResult.OK -> Unit
        }
//        fieldResult.value?.let { values.put(fieldResult.name, it) }
    }
}

