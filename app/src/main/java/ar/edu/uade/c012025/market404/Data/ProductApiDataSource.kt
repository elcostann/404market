package ar.edu.uade.c012025.market404.Data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class ProductApiDataSource {
    private val api = RetrofitInstance.api

    suspend fun getAllProducts(): List<Product> {
        return api.getProducts()
    }

    suspend fun getProductById(id: Int): Product {
        val db = FirebaseFirestore.getInstance()
        Log.d("ProductDB", "getProductById")

        return try {


            val docSnapshot = db.collection("Productos").document(id.toString()).get().await()
            val product = docSnapshot.toObject(Product::class.java)

            if (product != null) {
                Log.d("ProductDB", "Encontrado en Firestore")

                product
            } else {
                Log.d("ProductDB", "No encontrado en Firestore, buscando en API...")
                val fetchedProduct = api.getProduct(id)
                db.collection("Productos").document(id.toString()).set(fetchedProduct)


                fetchedProduct
            }
        } catch (e: Exception) {
            Log.e("ProductDB", "Error al obtener el producto: ${e.message}", e)
            throw e
        }
    }

    suspend fun getProductsByCategory(category: String): List<Product> {
        return api.getProductsByCategory(category)
    }

    suspend fun getCategories(): List<String> {
        return api.getCategories()
    }


    }



