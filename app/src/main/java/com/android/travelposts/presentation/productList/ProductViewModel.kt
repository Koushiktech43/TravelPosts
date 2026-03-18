package com.android.travelposts.presentation.productList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.travelposts.core.DispatcherProvider
import com.android.travelposts.data.remote.GetProductAPIStatus
import com.android.travelposts.data.remote.Product
import com.android.travelposts.domain.GetProductUseCase
import com.android.travelposts.presentation.core.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
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
class ProductViewModel @Inject constructor(
    private val useCase: GetProductUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Product>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Product>>> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory = _selectedCategory.asStateFlow()

    val productList: StateFlow<List<Product>> = _uiState.mapNotNull { state ->
        (state as? UiState.Success)?.data
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val categoryList: StateFlow<List<String>> = productList.mapNotNull { products ->
        listOf("All") + products.map { it.category }.distinct()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = listOf("All")
    )

    val filteredProductList: StateFlow<List<Product>> = combine(
        productList, _searchQuery, _selectedCategory
    ) { list, query, selectedCategory ->
        list.filter {
            (selectedCategory == "All" || selectedCategory == it.category) && (it.title.contains(
                query,
                ignoreCase = true
            ))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

   private val _productDetail  = MutableStateFlow<Product?>(null)
   val productDetail : StateFlow<Product?> = _productDetail.asStateFlow()

    private val limit = 10
    private var skip = 0

    var isLoading by mutableStateOf(false)
        private set
    var hasMore = true
        private set

    var currentList = mutableListOf<Product>()




    init {
        loadProducts()
    }

    fun loadProducts() {
        if (isLoading || !hasMore) return // guard here
        viewModelScope.launch {


            _uiState.value = UiState.Loading
            isLoading = true
            val data = withContext(
                dispatcherProvider.io
            ) {
                useCase.invoke(limit,skip)
            }
            when (data) {
                is GetProductAPIStatus.Error -> _uiState.value = UiState.Error(data.message)
                is GetProductAPIStatus.Success -> {
                    val newList = data.data.products
                    currentList.addAll(newList)

                    skip+= limit

                    if(currentList.size >= data.data.total){
                        hasMore = false
                    }
                    _uiState.value = UiState.Success(currentList)

                }
            }
            isLoading = false
        }
    }


    fun setSearchQuery(query: String) {
        this._searchQuery.value = query
    }

    fun loadProductDetailByID(id: Int) {
        _productDetail.value = productList.value.find { it.id == id }
    }

    fun setSelectedCategory(category: String) {
        this._selectedCategory.value = category
    }
}