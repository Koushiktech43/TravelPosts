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
class ProductListViewModel @Inject constructor(
    private val useCase: GetProductUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Product>>>(UiState.Loading)
    val uiState :  StateFlow<UiState<List<Product>>> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory = _selectedCategory.asStateFlow()

    val productList : StateFlow<List<Product>> = _uiState.mapNotNull { state->
        (state as? UiState.Success)?.data
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val categoryList : StateFlow<List<String>> = productList.mapNotNull {  products->
        listOf("All") + products.map { it.category }.distinct()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = listOf("All")
    )

    val filteredProductList : StateFlow<List<Product>> = combine(
        productList,_searchQuery, _selectedCategory
    ) { list , query ,selectedCategory ->
        list.filter { (selectedCategory == "All" || selectedCategory == it.category) && (it.title.contains(query,ignoreCase = true)) }
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

    fun setSearchQuery(query : String) {
        this._searchQuery.value = query
    }

    fun setSelectedCategory(category : String) {
        this._selectedCategory.value = category
    }
}