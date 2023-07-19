/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package net.sevendays.android.code_check.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import net.sevendays.android.code_check.api.GitHubApiService
import net.sevendays.android.code_check.api.RepositorySearchResponse
import net.sevendays.android.code_check.item.RepositoryItem
import javax.inject.Inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@HiltViewModel
internal class ItemsViewModel @Inject constructor(
    private val service: GitHubApiService
) : ViewModel() {

    private val _items = MutableStateFlow(listOf<RepositoryItem>())
    val items = _items

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery

    private val _searches = MutableStateFlow(listOf<String>())
    val searches = _searches

    private val _viewState = MutableStateFlow(ViewState.DONE)
    val viewState = _viewState

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun fetchResults(inputText: String) {
        _viewState.value = ViewState.LOADING
        val call = service.searchRepositories(
            "application/vnd.github.text-match+json",
            inputText
        )
        call.enqueue(object : Callback<RepositorySearchResponse> {
            override fun onResponse(
                call: Call<RepositorySearchResponse>,
                response: Response<RepositorySearchResponse>
            ) {
                viewModelScope.launch {
                    if (response.isSuccessful) {
                        response.body()?.repositories?.let {
                            _items.emit(it)
                            _viewState.emit(ViewState.DONE)
                        }
                    } else {
                        _viewState.emit(ViewState.ERROR)
                    }
                }
            }

            override fun onFailure(call: Call<RepositorySearchResponse>, t: Throwable) {
                _viewState.value = ViewState.ERROR
            }
        })
        viewModelScope.launch {
            updateSearches(inputText)
        }
    }

    private suspend fun updateSearches(search: String) {
        if (search.isNotBlank()) {
            val list = searches.value + search
            _searches.emit(list.toSet().toList())
        }
    }

}

enum class ViewState {

    LOADING,
    ERROR,
    DONE,

}