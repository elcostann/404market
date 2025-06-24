package ar.edu.uade.c012025.market404.Data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class FirebaseFavoriteRepository(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : FavoriteRepository {

    override suspend fun addFavorite(productId: Int) {
        val uid = auth.currentUser?.uid ?: return
        db.collection("users").document(uid)
            .collection("favorites")
            .document(productId.toString())
            .set(Favorite(productId))
            .await()
    }

    override suspend fun removeFavorite(productId: Int) {
        val uid = auth.currentUser?.uid ?: return
        db.collection("users").document(uid)
            .collection("favorites")
            .document(productId.toString())
            .delete()
            .await()
    }

    override suspend fun isFavorite(productId: Int): Boolean {
        val uid = auth.currentUser?.uid ?: return false
        val doc = db.collection("users").document(uid)
            .collection("favorites")
            .document(productId.toString())
            .get().await()
        return doc.exists()
    }

    override suspend fun getAllFavorites(): List<Int> {
        val uid = auth.currentUser?.uid ?: return emptyList()
        val snapshot = db.collection("users").document(uid)
            .collection("favorites")
            .get().await()
        return snapshot.documents.mapNotNull { it.getLong("productId")?.toInt() }
    }
}
