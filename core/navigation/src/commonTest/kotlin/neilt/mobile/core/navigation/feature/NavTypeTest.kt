/*
 * MIT License
 *
 * Copyright (c) 2024 AnyGogin31
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package neilt.mobile.core.navigation.feature

import android.os.Bundle
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class NavTypeTest {

    @Serializable
    data class TestData(val id: Int, val name: String)

    private val testSerializer = Json.serializersModule.serializer<TestData>()
    private val navType = NavType(testSerializer)

    @Test
    fun `get retrieves object from Bundle`() {
        val bundle = Bundle()
        val testData = TestData(1, "Test Name")
        val encodedValue = Json.encodeToString(testSerializer, testData)
        bundle.putString("key", encodedValue)

        val retrievedValue = navType[bundle, "key"]

        assertNotNull(retrievedValue)
        assertEquals(testData, retrievedValue)
    }

    @Test
    fun `put stores serialized object in Bundle`() {
        val bundle = Bundle()
        val testData = TestData(2, "Another Test")

        navType.put(bundle, "key", testData)
        val storedValue = bundle.getString("key")

        assertNotNull(storedValue)
        assertEquals(Json.encodeToString(testSerializer, testData), storedValue)
    }

    @Test
    fun `parseValue decodes from string`() {
        val testData = TestData(3, "Parsed Test")
        val encodedValue = Json.encodeToString(testSerializer, testData)

        val parsedValue = navType.parseValue(encodedValue)

        assertNotNull(parsedValue)
        assertEquals(testData, parsedValue)
    }

    @Test
    fun `serializeAsValue encodes to string`() {
        val testData = TestData(4, "Serialized Test")
        val serializedValue = navType.serializeAsValue(testData)

        assertEquals(Json.encodeToString(testSerializer, testData), serializedValue)
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun `name returns correct serializer descriptor name`() {
        assertEquals(testSerializer.descriptor.serialName, navType.name)
    }
}
