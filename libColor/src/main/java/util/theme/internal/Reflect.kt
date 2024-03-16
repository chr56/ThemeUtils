package util.theme.internal

import java.lang.reflect.Field
import kotlin.jvm.Throws

@Throws(NoSuchFieldException::class, SecurityException::class)
inline fun <reified T, reified F> T.reflectDeclaredField(fieldName: String): F {
    val f = T::class.java.getDeclaredField(fieldName)
    f.isAccessible = true
    return f.get(this) as F
}

inline fun <reified T> Class<T>.declaredField(fieldName: String): Field {
    val f = getDeclaredField(fieldName)
    f.isAccessible = true
    return f
}

fun isInClassPath(clsName: String): Boolean {
    return try {
        inClassPath(clsName) != null
    } catch (t: Throwable) {
        false
    }
}

fun inClassPath(clsName: String): Class<*>? =
    try {
        Class.forName(clsName)
    } catch (t: Throwable) {
        throw IllegalStateException(
            "$clsName is not in your class path! You must include the associated library."
        )
    }
