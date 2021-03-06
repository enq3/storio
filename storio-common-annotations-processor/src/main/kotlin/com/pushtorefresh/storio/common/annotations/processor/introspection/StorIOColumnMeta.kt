package com.pushtorefresh.storio.common.annotations.processor.introspection

import java.lang.Character.isUpperCase
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind

open class StorIOColumnMeta<out ColumnAnnotation : Annotation>(
        val enclosingElement: Element,
        val element: Element,
        val elementName: String,
        val javaType: JavaType,
        val storIOColumn: ColumnAnnotation) {

    val isMethod: Boolean
        get() = element.kind == ElementKind.METHOD

    val realElementName: String
        get() = when {
            elementName.startsWith("get") && isUpperCase(elementName[3]) -> decapitalize(elementName.substring(3))
            elementName.startsWith("is") && isUpperCase(elementName[2]) -> decapitalize(elementName.substring(2))
            else -> elementName
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as StorIOColumnMeta<*>

        if (enclosingElement != other.enclosingElement) return false
        if (element != other.element) return false
        if (elementName != other.elementName) return false
        if (javaType != other.javaType) return false
        if (storIOColumn != other.storIOColumn) return false

        return true
    }

    override fun hashCode(): Int {
        var result = enclosingElement.hashCode()
        result = 31 * result + element.hashCode()
        result = 31 * result + elementName.hashCode()
        result = 31 * result + javaType.hashCode()
        result = 31 * result + storIOColumn.hashCode()
        return result
    }

    override fun toString() = "StorIOColumnMeta(enclosingElement=$enclosingElement, element=$element, elementName='$elementName', javaType=$javaType, storIOColumn=$storIOColumn)"

    private fun decapitalize(str: String) = when {
        str.length > 1 -> Character.toLowerCase(str[0]) + str.substring(1)
        else -> str.toLowerCase()
    }
}