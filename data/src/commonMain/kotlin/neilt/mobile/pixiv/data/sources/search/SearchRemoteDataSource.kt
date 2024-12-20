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

package neilt.mobile.pixiv.data.sources.search

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import neilt.mobile.pixiv.data.mapper.details.illustration.toModel
import neilt.mobile.pixiv.data.mapper.home.toModel
import neilt.mobile.pixiv.data.remote.services.search.SearchService
import neilt.mobile.pixiv.domain.models.details.illustration.Tag
import neilt.mobile.pixiv.domain.models.home.Illustration

class SearchRemoteDataSource(
    private val searchService: SearchService,
) {
    suspend fun getSearchIllustrations(query: String): List<Illustration> {
        return withContext(Dispatchers.IO) {
            searchService.fetchSearchIllustrations(mapOf("word" to query)).illustrations.map { it.toModel() }
        }
    }

    suspend fun getSearchManga(query: String): List<Illustration> {
        return withContext(Dispatchers.IO) {
            searchService.fetchSearchIllustrations(mapOf("word" to query)).illustrations
                .filter { it.type == "manga" }
                .map { it.toModel() }
        }
    }

    suspend fun getSearchPredictionTags(query: String): List<Tag> {
        return withContext(Dispatchers.IO) {
            searchService.fetchSearchPredictionTags(query).tags.map { it.toModel() }
        }
    }
}
