package com.example.myapplication.data.Repository
import com.example.myapplication.data.Entities.Product
import kotlinx.coroutines.delay
import javax.inject.Inject
import com.example.myapplication.data.Api.ProductApi

class ProductRepository @Inject constructor(
    private val api: ProductApi
) {
    suspend fun getProducts(): List<Product> {
        return api.getProducts()
    }
}
