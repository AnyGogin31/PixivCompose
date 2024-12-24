package neilt.mobile.pixiv.features.search.presentation.result

import neilt.mobile.pixiv.core.state.ViewState
import neilt.mobile.pixiv.core.state.onState
import neilt.mobile.pixiv.core.state.whenState

internal data object EmptyState : ViewState

internal inline fun <reified T : Any> ViewState.whenStateExtended(
    onLoading: () -> Unit = {},
    onEmpty: () -> Unit = {},
    onLoaded: (T) -> Unit = {},
    onError: (String) -> Unit = {},
) {
    whenState<T>(
        onLoading = onLoading,
        onLoaded = onLoaded,
        onError = onError,
    )
    onState<EmptyState> {
        onEmpty()
    }
}
