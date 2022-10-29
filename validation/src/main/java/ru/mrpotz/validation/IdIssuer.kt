package ru.mrpotz.validation

class IdIssuer() {
    private var uniqueIdCounter: Long = 0L
    private val associatedNames: MutableMap<String, Long> = mutableMapOf()

    fun cleanNamed() {
        associatedNames.clear()
    }

    fun createId(): Long {
        return uniqueIdCounter++
    }

    fun createIdString(): String {
        return createId().toString()
    }

    fun createIdString(name: String): String {
        return createId(name).toString()
    }

    fun isIdOfName(string: String, id: String): Boolean {
        return associatedNames[string]?.let {
            it.toString() == id
        } ?: false
    }

    fun createId(name: String): Long {
        val existingId = associatedNames[name]
        if (existingId != null) {
            return existingId
        }
        return uniqueIdCounter++.also {
            associatedNames[name] = it
        }
    }
}
