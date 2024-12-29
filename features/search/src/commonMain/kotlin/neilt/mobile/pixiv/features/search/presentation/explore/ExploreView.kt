package neilt.mobile.pixiv.features.search.presentation.explore

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import neilt.mobile.pixiv.domain.models.details.illustration.Tag
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ExploreView(
    exploreType: ExploreType,
    query: String?,
    viewModel: ExploreViewModel = koinViewModel(),
) {
    val predictionTags by viewModel.predictionTags.collectAsState(emptyList())

    SearchInput(
        onSearch = {
            viewModel.search(exploreType, it)
        },
        initialQuery = query.orEmpty(),
        onQueryChange = viewModel::fetchTagsPrediction,
        isExpanded = true,
        onBack = viewModel::navigateUp,
        content = {
            TagsPredictionList(
                tags = predictionTags,
                onTagSelected = {
                    viewModel.search(exploreType, it)
                },
            )
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchInput(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,
    initialQuery: String = "",
    onQueryChange: (String) -> Unit,
    isExpanded: Boolean,
    onBack: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    var query by rememberSaveable { mutableStateOf(initialQuery) }

    SearchBar(
        modifier = modifier.fillMaxWidth(),
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = {
                    query = it
                    onQueryChange(it)
                },
                onSearch = onSearch,
                expanded = isExpanded,
                onExpandedChange = { if (it.not()) onBack() },
                placeholder = {},
            )
        },
        expanded = isExpanded,
        onExpandedChange = { if (it.not()) onBack() },
        content = content,
    )
}

@Composable
private fun TagsPredictionList(
    tags: List<Tag>,
    onTagSelected: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
    ) {
        items(tags) { tag ->
            Text(
                text = buildString {
                    append(tag.name)
                    tag.translatedName?.let { append(" ($it)") }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onTagSelected(tag.name) }
                    .padding(16.dp),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}
