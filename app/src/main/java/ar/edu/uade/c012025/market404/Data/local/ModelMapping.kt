package ar.edu.uade.c012025.market404.Data.local

import ar.edu.uade.c012025.market404.Data.Product
import ar.edu.uade.c012025.market404.Data.Rating

fun Product.toEntity(): ProductEntity = ProductEntity(
    id = id,
    title = title,
    description = description,
    price = price,
    category = category,
    image = image,
    ratingRate = rating?.rate,
    ratingCount = rating?.count
)

fun ProductEntity.toProduct(): Product = Product(
    id = id,
    title = title,
    description = description,
    price = price,
    category = category,
    image = image,
    rating = ratingRate?.let { rate ->
        ratingCount?.let { count ->
            Rating(rate, count)
        }
    }
)

