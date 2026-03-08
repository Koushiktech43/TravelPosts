package com.android.travelposts.presentation.getproducts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.travelposts.data.remote.ProductDTO
import com.android.travelposts.domain.usecase.GetProductsUseCase
import com.android.travelposts.presentation.getproducts.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetProductsViewModel @Inject constructor(
    private val useCase: GetProductsUseCase
) :  ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<ProductDTO>>>(UiState.Loading)
    val uiState : StateFlow<UiState<List<ProductDTO>>> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow<String>("")
    val searchQuery : StateFlow<String> = _searchQuery.asStateFlow()

     val productList : StateFlow<List<ProductDTO>> = _uiState.mapNotNull { state ->
         (state as? UiState.Success)?.data
     }.stateIn(
         scope = viewModelScope,
         started = SharingStarted.WhileSubscribed(5000),
         initialValue = emptyList()
     )

    val filteredList : StateFlow<List<ProductDTO>> = combine(
        productList,_searchQuery
    ) { product , query ->
        if(query.isNotEmpty()){
            product.filter { it.title.contains(query,ignoreCase = true) }
        } else{
            product
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = UiState.Success(useCase.invoke())
        }
    }

    fun updateSearch(query : String){
        this._searchQuery.value = query
    }

    fun getProductsById(id : Int) : ProductDTO? {
        return productList.value.find { it.id==id }
    }
}