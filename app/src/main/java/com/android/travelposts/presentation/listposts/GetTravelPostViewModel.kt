package com.android.travelposts.presentation.listposts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.travelposts.data.remote.TravelPostDto
import com.android.travelposts.domain.GetTravelPostUseCase
import com.android.travelposts.presentation.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GetTravelPostViewModel @Inject constructor (
    private val useCase: GetTravelPostUseCase
) :  ViewModel() {

    private val _uiState = MutableStateFlow<UiState<TravelPostDto>>(UiState.Initial)
    val uiSate = _uiState.asStateFlow()

    init {
        loadPosts()
    }

     fun loadPosts(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _uiState.value =  UiState.Success(useCase.invoke())
            }

        }
    }

}