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

package neilt.mobile.pixiv.data.sources.illustration

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import neilt.mobile.pixiv.data.mapper.details.illustration.toModel
import neilt.mobile.pixiv.data.mapper.home.toModel
import neilt.mobile.pixiv.data.remote.services.details.illustration.IllustrationService
import neilt.mobile.pixiv.domain.models.details.illustration.IllustrationDetails
import neilt.mobile.pixiv.domain.models.home.Illustration

class IllustrationRemoteDataSource(
    private val illustrationService: IllustrationService,
) {
    suspend fun getIllustration(illustrationId: Int): IllustrationDetails {
        return withContext(Dispatchers.IO) {
            illustrationService.fetchIllustration(illustrationId).illustrationDetails.toModel()
        }
    }

    suspend fun getRelatedIllustrations(illustrationId: Int, offset: Int): List<Illustration> {
        return withContext(Dispatchers.IO) {
            illustrationService.fetchRelatedIllustrations(illustrationId, offset).illustrations.map {
                it.toModel()
            }
        }
    }

    suspend fun getIllustrationFile(url: String): ByteArray {
        return withContext(Dispatchers.IO) {
            illustrationService.downloadIllustration(url)
        }
    }
}
