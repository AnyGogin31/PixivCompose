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

package neilt.mobile.pixiv.data.remote.requests.auth

data class AuthorizationRequest(
    val clientId: String,
    val clientSecret: String,
    val grantType: String,
    val codeAuthorization: String,
    val codeVerifier: String,
    val redirectUri: String,
    val includePolicy: Boolean,
)

data class UpdateTokenRequest(
    val clientId: String,
    val clientSecret: String,
    val grantType: String,
    val refreshToken: String,
    val includePolicy: Boolean,
)

fun AuthorizationRequest.toFieldMap(): Map<String, Any> {
    return mapOf(
        "client_id" to clientId,
        "client_secret" to clientSecret,
        "grant_type" to grantType,
        "code" to codeAuthorization,
        "code_verifier" to codeVerifier,
        "redirect_uri" to redirectUri,
        "include_policy" to includePolicy,
    )
}

fun UpdateTokenRequest.toFieldMap(): Map<String, Any> {
    return mapOf(
        "client_id" to clientId,
        "client_secret" to clientSecret,
        "grant_type" to grantType,
        "refresh_token" to refreshToken,
        "include_policy" to includePolicy,
    )
}
