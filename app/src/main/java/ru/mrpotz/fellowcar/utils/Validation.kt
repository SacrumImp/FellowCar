package ru.mrpotz.fellowcar.utils

import android.util.Log
import android.util.Patterns
import androidx.annotation.CheckResult
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlin.reflect.KClass

interface ValueContainer<T> {
    val value: T?
    var error: FieldError?
    var onError: ((FieldError?) -> Unit)?

    fun toDataClass(): DataClass<T>

    interface DataClass<T> {
        val value: T?
        val error: FieldError?
        var callback: ((T?) -> Unit)?
    }
}

open class TextContainer() : ValueContainer<CharSequence> {
    override var value: CharSequence? = ""
    override var error: FieldError? = null
    override var onError: ((FieldError?) -> Unit)? = null
    private var localDataClass: DataClass? = null

    class DataClass constructor(
        override val value: CharSequence?,
        override val error: FieldError?,
        override var callback: ((CharSequence?) -> Unit)? = null,
    ) :
        ValueContainer.DataClass<CharSequence> {
    }

    override fun toDataClass(): ValueContainer.DataClass<CharSequence> {
        return DataClass(value, error, null)
    }
}


open class CheckableContainer() : ValueContainer<Boolean> {
    override var value: Boolean = false
    override var error: FieldError? = null
    override var onError: ((FieldError?) -> Unit)? = null
    private var localDataClass: DataClass? = null

    class DataClass constructor(
        override val value: Boolean?,
        override val error: FieldError?,
        override var callback: ((Boolean?) -> Unit)?,
    ) :
        ValueContainer.DataClass<Boolean> {
    }

    override fun toDataClass(): ValueContainer.DataClass<Boolean> {
        return DataClass(value, error, null)
    }
}

abstract class Assertion<T, A> where A : Assertion<T, A> {
    internal var conditions: ConditionList? = null
    internal var description: String? = null
    abstract fun isValid(valueContainer: T): Boolean

    fun description(): String = description ?: defaultDescription()

    @Suppress("UNCHECKED_CAST")
    fun description(value: String?): A {
        this.description = value
        return this as A
    }

    /** A short defaultDescription of what the assertion tests. */
    abstract fun defaultDescription(): String

    /** Returns true if the assertion's condition returns true, or if there is no condition. */
    internal fun isConditionMet() = conditions.isAllMet()
}

typealias Condition = () -> Boolean

internal typealias ConditionList = List<Condition>

/** @author Aidan Follestad (@afollestad) */
internal class ConditionStack {
    private val stack = mutableListOf<Condition>()

    /** Pushes a condition into the stack. */
    fun push(condition: Condition) {
        stack.add(condition)
    }

    /** Pops the stack. */
    fun pop(): Condition {
        val top = peek()
        stack.removeAt(stack.size - 1)
        return top
    }

    /** Peeks the top item on the stack. */
    fun peek() = stack[stack.size - 1]

    /** Converts the current state of the stack to a immutable List. */
    fun asList(): List<Condition> = stack.toList()
}

/** Returns true if all conditions in the List return true. */
internal fun ConditionList?.isAllMet(): Boolean {
    return if (this == null || this.isEmpty()) {
        true
    } else {
        this.all { it() }
    }
}

/** @author Aidan Follestad (@afollestad) */
class EmailAssertion internal constructor() : Assertion<TextContainer, EmailAssertion>() {
    private val regex = Patterns.EMAIL_ADDRESS

    override fun isValid(valueContainer: TextContainer): Boolean =
        regex.matcher(valueContainer.value ?: "").matches()

    override fun defaultDescription() = "must be a valid email address"
}


/** @author Aidan Follestad (@afollestad) */
class NotEmptyAssertion internal constructor() : Assertion<TextContainer, NotEmptyAssertion>() {
    override fun isValid(valueContainer: TextContainer): Boolean =
        valueContainer.value?.isNotEmpty() ?: false

    override fun defaultDescription() = "cannot be empty"
}


/** @author Aidan Follestad (@afollestad) */
class CustomMatchAssertion<T>(
    assertionDescription: String,
    private val assertion: (T) -> Boolean,
) : Assertion<T, CustomMatchAssertion<T>>() {
    init {
        if (assertionDescription.trim().isEmpty()) {
            error("Custom assertion descriptions should not be empty.")
        }
        description(assertionDescription)
    }

    override fun isValid(valueContainer: T): Boolean {
        return assertion(valueContainer)
    }

    override fun defaultDescription() = "no description set"

}

open class FieldValue<T : Any>(open val value: T?, open val valueType: KClass<T>) {
    val valueOrThrow: T
        get() = checkNotNull(value)
}

open class TextFieldValue(
    override val value: CharSequence?,
    override val valueType: KClass<CharSequence>,
) : FieldValue<CharSequence>(value, valueType)

open class BooleanFieldValue(
    override val value: Boolean,
    override val valueType: KClass<Boolean>,
) : FieldValue<Boolean>(value, valueType)


@JvmInline
value class FieldId(val value: String)

fun String.asId(): FieldId {
    return FieldId(this)
}

/** @author Aidan Follestad (@afollestad) */
data class FieldError(
    /** The view ID that the error is for. */
    val id: FieldId,
    /** The defaultDescription of the error. */
    val description: String,
    /** The type of assertion that created this error. */
    val assertionType: KClass<out Assertion<*, *>>,
) {
    /** Returns the [description]. */
    override fun toString() = description
}

sealed class ValidationResult {
    object OK : ValidationResult()

    /**
     * Holds the validation result of a single field.
     *
     * @author Aidan Follestad (@afollestad)
     */
    open class FieldResult<T : Any>(
        val fieldId: FieldId,
        /** The snapshot value of the field when validation executed. */
        val value: FieldValue<T>?,
    ) : ValidationResult() {
        private val errors = mutableListOf<FieldError>()

        /** Returns true if validation passed with no errors. */
        @CheckResult
        fun success() = errors.isEmpty()

        /** Returns true if validation failed with errors. */
        @CheckResult
        fun hasErrors() = errors.isNotEmpty()

        /** Returns errors that occurred during validation. */
        @CheckResult
        fun errors(): List<FieldError> = errors

        /** Appends an error to the validation result. */
        @CheckResult
        internal fun addError(error: FieldError) = errors.add(error)

        internal fun addErrors(vararg errors: FieldError) = this.errors.addAll(errors)

        override fun toString(): String {
            return if (success()) {
                "Success"
            } else {
                "${errors.size} errors"
            }
        }
    }
}

interface ValidationContainer {
    fun getFieldName(id: FieldId): String
    fun markChanged(id: FieldId): Unit
    val debouncedFlow : Flow<List<FieldId>>
}

class ValidationContainerImpl() : ValidationContainer {
    private val mutableSharedFlow =
        MutableSharedFlow<FieldId>(
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
            extraBufferCapacity = 1
        )
    override val debouncedFlow: Flow<List<FieldId>> = mutableSharedFlow.map { it: FieldId ->
        it
    }
        .timedChunk(400L).map {
            it
        }

    override fun getFieldName(id: FieldId): String {
        return ""
    }

    override fun markChanged(id: FieldId) {
        mutableSharedFlow.tryEmit(id)
    }
}

abstract class FormField<F, V, T : Any>(
    val container: ValidationContainer,
    val valueContainer: V,
    val fieldId: FieldId,
) where F : FormField<F, V, T>, V : ValueContainer<T> {
    lateinit var form: Form

    private val assertions = mutableListOf<Assertion<V, *>>()
    private val conditionStack = ConditionStack()
    private val errorsTempList: MutableList<FieldError> = mutableListOf()

    fun <T : Assertion<V, *>> assert(assertion: T): T {
        assertions.add(assertion.apply {
            this.conditions = conditionStack.asList()
        })
        return assertion
    }

    @CheckResult
    fun assertions(): List<Assertion<V, *>> = assertions

    fun conditional(
        condition: Condition,
        builder: F.() -> Unit,
    ): FormField<F, V, T> = apply {
        conditionStack.push(condition)
        @Suppress("UNCHECKED_CAST")
        (this as F).builder()
        conditionStack.pop()
    }

    @CheckResult
    fun validate(): ValidationResult {
        val value = obtainValue()

        errorsTempList.clear()
        for (assertion in assertions()) {
            if (!assertion.isConditionMet()) {
                // If conditions aren't met, the assertion is ignored
                continue
            } else if (!assertion.isValid(valueContainer)) {
                val error = FieldError(
                    id = fieldId,
                    description = assertion.description(),
                    assertionType = assertion::class
                )
                errorsTempList.add(error)
            }
        }
        val result = if (errorsTempList.isEmpty()) {
            valueContainer.onError?.invoke(null)
            ValidationResult.OK
        } else {
            ValidationResult.FieldResult(
                fieldId = fieldId,
                value = value,
            ).apply {
                valueContainer.onError?.invoke(errorsTempList[0])
                addErrors(*errorsTempList.toTypedArray())
            }
        }
        errorsTempList.clear()
        return result
    }

    @CheckResult
    protected abstract fun obtainValue(): FieldValue<T>?

    open fun startRealTimeValidation(debounce: Int) {}
}

class CheckedStateAssertion internal constructor(
    private val checked: Boolean,
) : Assertion<CheckableContainer, CheckedStateAssertion>() {
    override fun isValid(valueContainer: CheckableContainer): Boolean =
        valueContainer.value == checked

    override fun defaultDescription() = if (checked) {
        "must be checked"
    } else {
        "must be not checked"
    }
}

class CheckableField(
    validationContainer: ValidationContainer,
    valueContainer: CheckableContainer,
    fieldId: FieldId,
) : FormField<CheckableField, CheckableContainer, Boolean>(validationContainer,
    valueContainer,
    fieldId) {
    fun isChecked() = assert(CheckedStateAssertion(true))
    fun isNotChecked() = assert(CheckedStateAssertion(false))
    fun assert(
        description: String,
        matcher: (CheckableContainer) -> Boolean,
    ) = assert(CustomMatchAssertion(description, matcher))

    override fun obtainValue(): FieldValue<Boolean> {
        return BooleanFieldValue(
            valueType = Boolean::class,
            value = valueContainer.value
        )
    }
}

open class InputField(
    validationContainer: ValidationContainer,
    textContainer: TextContainer,
    fieldID: FieldId,
) : FormField<InputField, TextContainer, CharSequence>(validationContainer,
    textContainer,
    fieldID) {

    fun isEmptyOr(builder: InputField.() -> Unit) = conditional(
        condition = {
            valueContainer.value?.trim()
                ?.isNotEmpty() ?: false
        },
        builder = builder
    )

    fun isNotEmpty() = assert(NotEmptyAssertion())
    fun isEmail() = assert(EmailAssertion())
    fun assert(
        description: String,
        matcher: (TextContainer) -> Boolean,
    ) = assert(CustomMatchAssertion(description, matcher))


    override fun obtainValue(): FieldValue<CharSequence>? {
        val currentValue = valueContainer.value ?: return null
        return TextFieldValue(
            value = currentValue,
            valueType = CharSequence::class
        )
    }
}
