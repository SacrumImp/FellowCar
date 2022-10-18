package ru.mrpotz.fellowcar.utils

import android.util.Log
import androidx.compose.runtime.Immutable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class AssociatedValueContainer<
        V, T : Any, D : ValueContainer.DataClass<T>>(
    val validationContainer: ValidationContainer,
    val valueContainer: V,
    val fieldId: FieldId = idIssuer.createIdString().asId(),
    private val defaultValue: T,
) : ((T?) -> Unit) where V : ValueContainer<T> {
//    private val _value = MutableStateFlow(valueContainer.value ?: defaultValue)
//    private val _errorValue: MutableStateFlow<String?> =
//        MutableStateFlow(valueContainer.error?.description)
    private val _flow: MutableStateFlow<D> by lazy {
        MutableStateFlow(toDataClass() as D)
    }

    protected abstract fun changeValue(newValue: T?)
    protected abstract fun changeError(newError: FieldError?)

    init {
        valueContainer.onError = {
            changeError(it)
            _flow.value = toDataClass() as D
        }
    }

    override fun invoke(p1: T?) {
        changeValue(p1)
        validationContainer.markChanged(fieldId)
        _flow.value = toDataClass()
    }

    fun <T2> asConverter(converter: (T2) -> T): ((T2) -> Unit) {
        return {
            this(converter(it))
        }
    }

    open fun toDataClass() : D {
        val dataClass = valueContainer.toDataClass()
        if (dataClass.callback == null) {
            dataClass.callback = {
                this.invoke(it)
            }
        }
        return dataClass as D
    }

    open val flow: StateFlow<D>
        get() {
            return _flow
        }

    companion object {
        val idIssuer: IdIssuer = IdIssuer()
    }
}

class TextAssociatedContainer(validationContainer: ValidationContainer) :
    AssociatedValueContainer<TextContainer, CharSequence, TextContainer.DataClass>(validationContainer = validationContainer,
        valueContainer = TextContainer(),
        defaultValue = "") {
    override fun changeValue(newValue: CharSequence?) {
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
    AssociatedValueContainer<CheckableContainer, Boolean, CheckableContainer.DataClass>(validationContainer = validationContainer,
        valueContainer = CheckableContainer(),
        defaultValue = defaultChecked) {
    override fun changeValue(newValue: Boolean?) {
        valueContainer.value = newValue ?: false
    }
    override fun changeError(newError: FieldError?) {
        valueContainer.error = newError
    }
}
