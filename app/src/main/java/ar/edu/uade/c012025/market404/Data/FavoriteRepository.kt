package ar.edu.uade.c012025.market404.Data


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FavoriteRepository {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun getFavorites(onResult: (List<FavoriteProduct>) -> Unit) {
        val userId = auth.currentUser?.uid ?: return
        db.collection("users").document(userId).collection("Favoritos")
            .get()
            .addOnSuccessListener { result ->
                val favoritos = result.mapNotNull { it.toObject(FavoriteProduct::class.java) }
                onResult(favoritos)
            }
    }

    fun addFavorite(product: FavoriteProduct) {
        val userId = auth.currentUser?.uid ?: return
        db.collection("users").document(userId).collection("Favoritos")
            .document(product.productId)
            .set(product)
    }

    fun removeFavorite(productId: String) {
        val userId = auth.currentUser?.uid ?: return
        db.collection("users").document(userId).collection("Favoritos")
            .document(productId)
            .delete()
    }
}
