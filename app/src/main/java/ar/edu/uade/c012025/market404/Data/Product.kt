package ar.edu.uade.c012025.market404.Data


data class Product(
    val id: Int= 0,
    val title: String= "",
    val price: Double= 0.0,
    val description: String= "",
    val category: String= "",
    val image: String= "",
    val rating: Rating= Rating()
)
