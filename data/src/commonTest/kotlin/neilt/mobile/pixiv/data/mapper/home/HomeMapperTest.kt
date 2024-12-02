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

package neilt.mobile.pixiv.data.mapper.home

import neilt.mobile.pixiv.data.remote.responses.common.ImageUrlsResponse
import neilt.mobile.pixiv.data.remote.responses.home.IllustrationResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class HomeMapperTest {
    @Test
    fun `ImageUrlsResponse toModel maps correctly`() {
        val imageUrlsResponse = ImageUrlsResponse(
            squareMediumUrl = "square_url",
            mediumUrl = "medium_url",
            largeUrl = "large_url",
        )

        val imageUrls = imageUrlsResponse.toModel()

        assertEquals("Square medium URL should be mapped correctly", imageUrlsResponse.squareMediumUrl, imageUrls.squareMediumUrl)
        assertEquals("Medium URL should be mapped correctly", imageUrlsResponse.mediumUrl, imageUrls.mediumUrl)
        assertEquals("Large URL should be mapped correctly", imageUrlsResponse.largeUrl, imageUrls.largeUrl)
    }

    @Test
    fun `IllustrationResponse toModel maps correctly`() {
        val illustrationResponse = IllustrationResponse(
            id = 1,
            title = "Test Illustration",
            type = "type1",
            imageUrls = ImageUrlsResponse(
                squareMediumUrl = "square_url",
                mediumUrl = "medium_url",
                largeUrl = "large_url",
            ),
        )

        val illustration = illustrationResponse.toModel()

        assertEquals("ID should be mapped correctly", illustrationResponse.id, illustration.id)
        assertEquals("Title should be mapped correctly", illustrationResponse.title, illustration.title)
        assertEquals("Type should be mapped correctly", illustrationResponse.type, illustration.type)
        assertEquals(
            "Image URLs should be mapped correctly",
            illustrationResponse.imageUrls.toModel(),
            illustration.imageUrls,
        )
    }
}
