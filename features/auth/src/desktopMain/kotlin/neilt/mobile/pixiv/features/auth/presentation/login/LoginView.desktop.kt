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

package neilt.mobile.pixiv.features.auth.presentation.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.multiplatform.webview.request.RequestInterceptor
import com.multiplatform.webview.request.WebRequest
import com.multiplatform.webview.request.WebRequestInterceptResult
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.WebViewNavigator
import com.multiplatform.webview.web.rememberWebViewNavigator
import com.multiplatform.webview.web.rememberWebViewState
import dev.datlag.kcef.KCEF
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import neilt.mobile.pixiv.domain.provider.PKCEProvider
import org.koin.compose.koinInject
import java.io.File
import kotlin.math.max

@Composable
internal actual fun LoginView(
    viewModel: LoginViewModel,
) {
    var restartRequired by remember { mutableStateOf(false) }
    var downloading by remember { mutableStateOf(0F) }
    var initialized by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            KCEF.init(
                builder = {
                    installDir(File("kcef-bundle"))
                    progress {
                        onDownloading {
                            downloading = max(it, 0F)
                        }
                        onInitialized {
                            initialized = true
                        }
                    }
                    settings {
                        cachePath = File("cache").absolutePath
                    }
                },
                onError = {
                    it?.printStackTrace()
                },
                onRestartRequired = {
                    restartRequired = true
                },
            )
        }
    }

    if (restartRequired) {
        Text(text = "Restart required.")
    } else {
        if (initialized) {
            val state = rememberWebViewState(url = koinInject<PKCEProvider>().getProvisionalAccountUrl())
            val webNav = rememberWebViewNavigator(
                requestInterceptor = object : RequestInterceptor {
                    override fun onInterceptUrlRequest(
                        request: WebRequest,
                        navigator: WebViewNavigator,
                    ): WebRequestInterceptResult {
                        if (request.url.startsWith("pixiv://")) {
                            val code = request.url.substringAfter("code=").substringBefore("&")
                            viewModel.navigateToAuthScreen(code = code)
                            return WebRequestInterceptResult.Reject
                        }
                        return WebRequestInterceptResult.Allow
                    }
                },
            )
            WebView(
                modifier = Modifier.fillMaxSize(),
                state = state,
                navigator = webNav,
            )
        } else {
            Text(text = "Downloading $downloading%")
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            KCEF.disposeBlocking()
        }
    }
}
