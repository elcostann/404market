package ar.edu.uade.c012025.market404.ui


sealed class Screens(val route: String) {
    object Splash        : Screens("splash")
    object Login         : Screens("login")
    object Home          : Screens("productlist")
    object ProductDetail : Screens("productDetail/{productId}") {
        /** Helper para generar la ruta con parámetro */
        fun createRoute(productId: Int) = "productDetail/$productId"
    }
    object Cart :Screens("cart")
    object Success : Screens("success")


}
