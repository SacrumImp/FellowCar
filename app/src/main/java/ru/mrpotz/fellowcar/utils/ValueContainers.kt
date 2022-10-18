package ru.mrpotz.fellowcar.utils

import kotlinx.coroutines.flow.MutableStateFlow

abstract class AssociatedValueContainer<V, T : Any>(
    val validationContainer: ValidationContainer,
    val valueContainer: V,
    val fieldId: FieldId = idIssuer.createIdString().asId(),
    private val defaultValue: T,
) : ((T) -> Unit) where V : ValueContainer<T> {
    private val _value = MutableStateFlow(valueContainer.value ?: defaultValue)
    private val _errorValue: MutableStateFlow<String?> =
        MutableStateFlow(valueContainer.error?.description)

    protected abstract fun changeValue(newValue: T)
    protected abstract fun changeError(newError: FieldError?)

    init {
        valueContainer.onError = {
            changeError(it)
            _errorValue.value = valueContainer.error?.description
        }
    }

    override fun invoke(p1: T) {
        changeValue(p1)
        validationContainer.markChanged(fieldId)
        _value.value = valueContainer.value ?: defaultValue
    }

    fun <T2> asConverter(converter: (T2) -> T): ((T2) -> Unit) {
        return {
            this(converter(it))
        }
    }

    val value: MutableStateFlow<T>
        get() {
            return _value
        }
    val errorValue: MutableStateFlow<String?>
        get() {
            return _errorValue
        }

    companion object {
        val idIssuer: IdIssuer = IdIssuer()
    }
}

class TextAssociatedContainer(validationContainer: ValidationContainer) :
    AssociatedValueContainer<TextContainer, CharSequence>(validationContainer = validationContainer,
        valueContainer = TextContainer(),
        defaultValue = "") {
    override fun changeValue(newValue: CharSequence) {
        valueContainer.value = newValue
    }

    override fun changeError(newError: FieldError?) {
        valueContainer.error = newError
    }
}

class CheckedAssociatedContainer(
    validationContainer: ValidationContainer,
    defaultChecked: Boolean = false,
) :
    AssociatedValueContainer<CheckableContainer, Boolean>(validationContainer = validationContainer,
        valueContainer = CheckableContainer(),
        defaultValue = defaultChecked) {
    override fun changeValue(newValue: Boolean) {
        valueContainer.value = newValue
    }

    override fun changeError(newError: FieldError?) {
        valueContainer.error = newError
    }
}
