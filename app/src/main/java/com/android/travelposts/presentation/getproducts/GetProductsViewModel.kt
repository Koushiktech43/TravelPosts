package com.android.travelposts.presentation.getproducts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.travelposts.data.remote.ProductDTO
import com.android.travelposts.domain.usecase.GetProductsUseCase
import com.android.travelposts.presentation.getproducts.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetProductsViewModel @Inject constructor(
    private val useCase: GetProductsUseCase
) :  ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<ProductDTO>>>(UiState.Loading)
    val uiState : StateFlow<UiState<List<ProductDTO>>> = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = UiState.Success(useCase.invoke())
        }
    }
}