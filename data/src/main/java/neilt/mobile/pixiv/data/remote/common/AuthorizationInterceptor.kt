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

package neilt.mobile.pixiv.data.remote.common

import neilt.mobile.pixiv.domain.repositories.auth.TokenProvider
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation

class AuthorizationInterceptor(private val tokenProvider: TokenProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // Check if authorization is required for the request
        val invocationTag = originalRequest.tag(Invocation::class.java)
        val isAuthorizationRequired = invocationTag
            ?.method()
            ?.getAnnotation(Authorization::class.java) != null

        if (!isAuthorizationRequired) {
            // If no authorization is required, proceed with the request
            return chain.proceed(originalRequest)
        }

        // Retrieve the token from the token provider
        val token = tokenProvider.getToken()
        if (token.isNullOrEmpty()) {
            // Throw an exception if the token is missing
            throw IllegalStateException("Authorization failed: Missing token for authenticated request")
        }

        // Add the Authorization header to the request
        val authorizedRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        // Proceed with the modified request
        return chain.proceed(authorizedRequest)
    }
}
