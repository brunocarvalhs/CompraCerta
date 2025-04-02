package br.com.brunocarvalhs.compracerta.features.shoppingList.app.data.repositories

import br.com.brunocarvalhs.compracerta.features.shoppingList.app.data.dataSource.ProductDao
import br.com.brunocarvalhs.compracerta.features.shoppingList.app.data.model.ProductModel
import br.com.brunocarvalhs.compracerta.features.shoppingList.app.domain.model.Product
import br.com.brunocarvalhs.compracerta.features.shoppingList.app.domain.repositories.ProductRepository
import timber.log.Timber

class ProductRepositoryImpl(
    private val dao: ProductDao
) : ProductRepository {

    override suspend fun getProducts(groupId: Long): List<Product> {
        return try {
            dao.getProductsByGroup(groupId)
        } catch (e: Exception) {
            Timber.e("Error fetching products: ${e.message}", e)
            emptyList()
        }
    }

    override suspend fun addProduct(product: Product) {
        dao.insertProduct(product as ProductModel)
    }

    override suspend fun deleteProduct(product: Product) {
        dao.deleteProduct(product as ProductModel)
    }

    override suspend fun updateProduct(product: Product) {
        dao.updateProduct(product as ProductModel)
    }
}