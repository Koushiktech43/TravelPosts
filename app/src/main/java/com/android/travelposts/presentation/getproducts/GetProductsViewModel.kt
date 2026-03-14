package com.android.travelposts.presentation.getproducts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.travelposts.core.DispatcherProvider
import com.android.travelposts.data.remote.ProductDTO
import com.android.travelposts.domain.usecase.GetProductsUseCase
import com.android.travelposts.presentation.getproducts.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GetProductsViewModel @Inject constructor(
    private val useCase: GetProductsUseCase,
    private val dispatcherProvider : DispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<ProductDTO>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<ProductDTO>>> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory = _selectedCategory.asStateFlow()


    // Observing Success Product List
    val productList: StateFlow<List<ProductDTO>> = _uiState.mapNotNull { state ->
        (state as? UiState.Success)?.data
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )


  // Listening for search query and update filter
    val filteredList: StateFlow<List<ProductDTO>> = combine(
        productList, _searchQuery,_selectedCategory
    ) { list, query, category ->
      list.filter {
          ((it.category == category) || (category == "All")) &&
                  ((query.isEmpty()) || (it.title.contains(query, ignoreCase = true)))
      }.sortedBy { it.price }
  }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Whenever product list changes Category list gets updated
    val categoryList : StateFlow<List<String>> = productList.mapNotNull { products ->
        listOf("All") +  products.map { it.category }.distinct()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(1000),
        initialValue = listOf("All")
    )


    fun loadProducts() {
        _uiState.value = UiState.Loading
        viewModelScope.launch(dispatcherProvider.io) {
            try {
                val data = withContext(dispatcherProvider.io){
                    useCase.invoke()
                }
                _uiState.value = UiState.Success(data)
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.value = UiState.Error(e.message.toString())
            }

        }
    }

    fun getProductByID(id : Int) : ProductDTO? {
        return productList.value.find { it.id == id }
    }

    fun setSearchQuery(query : String) {
        this._searchQuery.value = query
    }

    fun setSelectedCategory(category : String) {
        _selectedCategory.value = category
    }


}