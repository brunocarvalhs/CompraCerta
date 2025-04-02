package br.com.brunocarvalhs.compracerta.features.shoppingList.app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import br.com.brunocarvalhs.compracerta.commons.database.AppDatabase
import br.com.brunocarvalhs.compracerta.features.shoppingList.app.data.model.ProductModel
import br.com.brunocarvalhs.compracerta.features.shoppingList.app.data.repositories.ProductRepositoryImpl
import br.com.brunocarvalhs.compracerta.features.shoppingList.app.domain.model.Product
import br.com.brunocarvalhs.compracerta.features.shoppingList.app.domain.useCase.AddProductUseCase
import br.com.brunocarvalhs.compracerta.features.shoppingList.app.domain.useCase.DeleteProductUseCase
import br.com.brunocarvalhs.compracerta.features.shoppingList.app.domain.useCase.GetProductsUseCase
import br.com.brunocarvalhs.compracerta.features.shoppingList.app.domain.useCase.UpdateProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

internal class ShoppingListViewModel(
    private val updateProductUseCase: UpdateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val addProductUseCase: AddProductUseCase,
    private val getProductsUseCase: GetProductsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ShoppingListUiState>(ShoppingListUiState.Loading)
    val uiState: StateFlow<ShoppingListUiState> = _uiState.asStateFlow()

    private var groupId by Delegates.notNull<Long>()

    fun handleIntent(intent: ShoppingListIntent) {
        when (intent) {
            is ShoppingListIntent.FetchData -> fetchData(groupId = intent.groupId)
            is ShoppingListIntent.UpdateItem -> updateItem(item = intent.item)
            is ShoppingListIntent.AddItem -> addItem(
                groupId = intent.groupId,
                price = intent.price,
                quantity = intent.quantity
            )
            is ShoppingListIntent.DeleteItem -> deleteItem(item = intent.item)
        }
    }

    private fun fetchData(groupId: Long) {
        viewModelScope.launch {
            getProductsUseCase.invoke(groupId)
                .onSuccess { products ->
                    this@ShoppingListViewModel.groupId = groupId
                    _uiState.value = ShoppingListUiState.Success(products)
                }.onFailure {
                    _uiState.value = ShoppingListUiState.Error(it.message.orEmpty())
                }
        }
    }

    private fun updateItem(item: Product) {
        viewModelScope.launch {
            updateProductUseCase.invoke(item)
                .onSuccess { fetchData(groupId) }
                .onFailure {
                    _uiState.value = ShoppingListUiState.Error(it.message.orEmpty())
                }
        }
    }

    private fun addItem(groupId : Long, price: String, quantity: Int) {
        viewModelScope.launch {
            val newItem = ProductModel(
                name = "Novo Produto",
                price = price.replace(",", ".").toDouble(),
                quantity = quantity,
                groupId = groupId
            )
            addProductUseCase.invoke(newItem)
                .onSuccess { fetchData(groupId) }
                .onFailure {
                    _uiState.value = ShoppingListUiState.Error(it.message.orEmpty())
                }
        }
    }

    private fun deleteItem(item: Product) {
        viewModelScope.launch {
            deleteProductUseCase.invoke(item)
                .onSuccess { fetchData(groupId) }
                .onFailure {
                    _uiState.value = ShoppingListUiState.Error(it.message.orEmpty())
                }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) {
                    "Application is required to initialize ViewModel"
                }
                val database = AppDatabase.getInstance(context = application.applicationContext)
                val repository = ProductRepositoryImpl(database.productDao())

                val updateProductUseCase = UpdateProductUseCase(repository)
                val deleteProductUseCase = DeleteProductUseCase(repository)
                val addProductUseCase = AddProductUseCase(repository)
                val getProductsUseCase = GetProductsUseCase(repository)

                ShoppingListViewModel(
                    updateProductUseCase = updateProductUseCase,
                    deleteProductUseCase = deleteProductUseCase,
                    addProductUseCase = addProductUseCase,
                    getProductsUseCase = getProductsUseCase,
                )
            }
        }
    }
}