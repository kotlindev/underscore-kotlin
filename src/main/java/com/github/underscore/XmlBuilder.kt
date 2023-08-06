/*
 * The MIT License (MIT)
 *
 * Copyright 2023 Valentyn Kolesnikov
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.underscore

import com.github.underscore.Json.JsonStringBuilder
import com.github.underscore.Xml.Document.createDocument
import com.github.underscore.Xml.XmlValue.getMapKey
import com.github.underscore.Xml.toXml
import org.w3c.dom.Document

open class XmlBuilder internal constructor(rootName: String) {
    private val data: MutableMap<String?, Any?>
    private var path: String
    private var savedPath: String? = null

    init {
        data = LinkedHashMap()
        val value: MutableMap<String, Any> = LinkedHashMap()
        value[SELF_CLOSING] = TRUE
        data[rootName] = value
        path = rootName
    }

    fun e(elementName: String): XmlBuilder {
        U.remove<Any>(data, path + "." + SELF_CLOSING)
        val value: MutableMap<String, Any> = LinkedHashMap()
        value[SELF_CLOSING] = TRUE
        val `object` = U.get<Any>(data, "$path.$elementName")
        if (`object` is Map<*, *>) {
            val list: MutableList<Any> = ArrayList()
            list.add(`object`)
            list.add(value)
            U.set<Any>(data, "$path.$elementName", list)
            path += ".$elementName.1"
            savedPath = path
        } else if (`object` is List<*>) {
            path += "." + elementName + "." + (`object` as List<Any?>).size
            savedPath = path
            (`object` as MutableList<Any?>).add(value)
        } else {
            U.set<Any>(data, "$path.$elementName", value)
            path += ".$elementName"
        }
        return this
    }

    fun a(attributeName: String, value: String?): XmlBuilder {
        U.remove<Any>(data, path + "." + SELF_CLOSING)
        U.set<Any>(data, "$path.-$attributeName", value)
        return this
    }

    fun c(comment: String?): XmlBuilder {
        U.remove<Any>(data, path + "." + SELF_CLOSING)
        U.update<Any>(data, "$path.#comment", comment)
        return this
    }

    fun i(target: String, value: String?): XmlBuilder {
        U.remove<Any>(data, path + "." + SELF_CLOSING)
        U.set<Any>(data, "?$target", value)
        return this
    }

    fun d(cdata: String?): XmlBuilder {
        U.remove<Any>(data, path + "." + SELF_CLOSING)
        U.update<Any>(data, "$path.#cdata-section", cdata)
        return this
    }

    fun t(text: String?): XmlBuilder {
        U.remove<Any>(data, path + "." + SELF_CLOSING)
        U.update<Any>(data, "$path.#text", text)
        return this
    }

    fun importXmlBuilder(xmlBuilder: XmlBuilder): XmlBuilder {
        data.putAll(xmlBuilder.data)
        return this
    }

    fun up(): XmlBuilder {
        if (path == savedPath) {
            path = path.substring(0, path.lastIndexOf("."))
        }
        path = path.substring(0, path.lastIndexOf("."))
        return this
    }

    fun root(): XmlBuilder {
        val index = path.indexOf(".")
        val xmlBuilder = XmlBuilder(if (index == -1) path else path.substring(0, index))
        xmlBuilder.setData(data)
        return xmlBuilder
    }

    val document: Document
        get() = try {
            createDocument(asString()!!)
        } catch (ex: Exception) {
            throw IllegalArgumentException(ex)
        }

    operator fun set(path: String?, value: Any?): XmlBuilder {
        U.set<Any>(data, path, value)
        return this
    }

    fun remove(key: String?): XmlBuilder {
        U.remove<Any>(data, key)
        return this
    }

    fun build(): Map<String, Any> {
        return U.deepCopyMap(data)
    }

    fun clear(): XmlBuilder {
        data.clear()
        return this
    }

    val isEmpty: Boolean
        get() = data.isEmpty()

    fun size(): Int {
        return data.size
    }

    open fun asString(): String? {
        return U.toXml(data)
    }

    fun toXml(identStep: Xml.XmlStringBuilder.Step?): String {
        return toXml(data, identStep!!)
    }

    fun toXml(): String {
        return U.toXml(data)
    }

    fun toJson(identStep: JsonStringBuilder.Step?): String {
        return Json.toJson(data, identStep)
    }

    fun toJson(): String {
        return U.toJson(data)
    }

    private fun setData(newData: Map<String?, Any?>) {
        data.clear()
        data.putAll(newData)
    }

    companion object {
        private const val SELF_CLOSING = "-self-closing"
        private const val TRUE = "true"
        @JvmStatic
        fun create(rootName: String): XmlBuilder {
            return XmlBuilder(rootName)
        }

        @JvmStatic
        fun parse(xml: String?): XmlBuilder {
            val xmlData = U.fromXmlMap(xml)
            val xmlBuilder = XmlBuilder(getMapKey(xmlData))
            xmlBuilder.setData(xmlData)
            return xmlBuilder
        }
    }
}
