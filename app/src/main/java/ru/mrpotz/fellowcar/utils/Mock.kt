package ru.mrpotz.fellowcar.utils

/**
 * Is used to denote entities related to mocking. Those entities should be replaced or their usage
 * should be modified after given functionality becomes available on the server
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS,
    AnnotationTarget.FIELD,
    AnnotationTarget.EXPRESSION,
    AnnotationTarget.FILE,
    AnnotationTarget.TYPE)
annotation class Mock {
}
