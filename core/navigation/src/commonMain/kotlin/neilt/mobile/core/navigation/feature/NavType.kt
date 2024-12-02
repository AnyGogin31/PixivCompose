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

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

/**
 * A custom [NavType] for handling serialization and deserialization of navigation arguments using Kotlin Serialization.
 *
 * @param T The type of the object to be serialized/deserialized.
 * @property serializer The [KSerializer] used to serialize and deserialize the object.
 * @property isNullableAllowed Specifies whether the navigation argument is allowed to be nullable.
 */
class NavType<T : Any>(
    private val serializer: KSerializer<T>,
    override val isNullableAllowed: Boolean = false,
) : NavType<T>(isNullableAllowed = isNullableAllowed) {
    @ExperimentalSerializationApi
    override val name: String get() = serializer.descriptor.serialName

    /**
     * Retrieves the object from the [Bundle] using the specified key.
     *
     * @param bundle The [Bundle] containing the arguments.
     * @param key The key used to retrieve the object.
     * @return The deserialized object, or `null` if the value is not present.
     */
    override fun get(bundle: Bundle, key: String): T? {
        return bundle.getString(key)?.let { Json.decodeFromString(serializer, it) }
    }

    /**
     * Puts the serialized object into the [Bundle] with the specified key.
     *
     * @param bundle The [Bundle] to put the object into.
     * @param key The key to associate with the object.
     * @param value The object to serialize and put into the bundle.
     */
    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putString(key, Json.encodeToString(serializer, value))
    }

    /**
     * Parses a value from a string, decoding it from its serialized form.
     *
     * @param value The string value to parse.
     * @return The deserialized object.
     */
    override fun parseValue(value: String): T {
        return Json.decodeFromString(serializer, Uri.decode(value))
    }

    /**
     * Serializes the object to a string value.
     *
     * @param value The object to serialize.
     * @return The serialized string value.
     */
    override fun serializeAsValue(value: T): String {
        return Uri.encode(Json.encodeToString(serializer, value))
    }
}
