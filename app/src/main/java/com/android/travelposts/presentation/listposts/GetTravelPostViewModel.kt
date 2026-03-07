package com.android.travelposts.presentation.listposts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.travelposts.data.remote.Children
import com.android.travelposts.data.remote.TravelPostDto
import com.android.travelposts.domain.GetTravelPostUseCase
import com.android.travelposts.presentation.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GetTravelPostViewModel @Inject constructor (
    private val useCase: GetTravelPostUseCase
) :  ViewModel() {

    private val _uiState = MutableStateFlow<UiState<TravelPostDto>>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery : StateFlow<String> = _searchQuery.asStateFlow()

    init {
        loadPosts()
    }


    val posts : StateFlow<List<Children>> = _uiState.mapNotNull { state ->
        (state as? UiState.Success)?.data?.data?.children
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        emptyList())

    val filteredList : StateFlow<List<Children>> = combine(posts,_searchQuery){ list , query ->
          if(query.isEmpty()){
              list
          } else {
              list.filter { it.data.name.contains(query) }
          }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )


    fun updateSearchQuery(query : String) {
        _searchQuery.value = query
    }
    fun loadPosts(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _uiState.value =  UiState.Success(useCase.invoke())
            }

        }
    }

}