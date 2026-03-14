package com.android.travelposts.presentation.productList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.travelposts.data.remote.GetProductAPIStatus
import com.android.travelposts.data.remote.Product
import com.android.travelposts.domain.GetProductUseCase
import com.android.travelposts.presentation.core.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val useCase: GetProductUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Product>>>(UiState.Loading)
    val uiState :  StateFlow<UiState<List<Product>>> = _uiState.asStateFlow()


    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
           withContext(
               Dispatchers.IO
           ) {
               val data = useCase.invoke()
               withContext(Dispatchers.Main){
                   when(data) {
                       is GetProductAPIStatus.Error -> _uiState.value = UiState.Error(data.message)
                       is GetProductAPIStatus.Success -> {
                            _uiState.value = UiState.Success(data.data.products)
                       }
                   }
               }
           }
        }
    }
}