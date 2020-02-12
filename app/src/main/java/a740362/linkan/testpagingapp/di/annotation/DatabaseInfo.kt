package a740362.linkan.testpagingapp.di.annotation

import kotlin.annotation.AnnotationTarget;

import javax.inject.Qualifier

@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
annotation class DatabaseInfo
