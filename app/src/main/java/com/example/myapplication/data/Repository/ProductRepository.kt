package com.example.myapplication.data.Repository
import com.example.myapplication.data.Entities.Product
import kotlinx.coroutines.delay
import javax.inject.Inject
import com.example.myapplication.data.Api.ProductApi




class ProductRepository @Inject constructor(
    private val api: ProductApi
)  {
    // Fonction suspendue simulant un appel réseau ou base de données
    suspend fun getProducts(): List<Product> {
        delay(2000) // Simulation d'un chargement

        return listOf(
            Product(
                id = "0",
                nameProduct = "Effaclar DUO+M",
                brand = "La Roche-Posay",
                price = 129.99,
                description = "Soin complet contre les imperfections sévères.",
                image = "image1",
                ingredients = listOf("Niacinamide", "Procerad", "LHA", "Acide salicylique"),
                howToUse = "Appliquer matin et soir sur le visage nettoyé.",
                quantity = 2
            ),
            Product(
                id = "1",
                nameProduct = "Hyalu B5 Sérum",
                brand = "La Roche-Posay",
                price = 169.99,
                description = "Sérum réparateur anti-rides repulpant.",
                image = "image2",
                ingredients = listOf("Acide hyaluronique", "Vitamine B5", "Madecassoside"),
                howToUse = "Appliquer 2 gouttes matin et soir avant la crème.",
                quantity = 10
            ),
            Product(
                id = "2",
                nameProduct = "Hydreane Légère",
                brand = "La Roche-Posay",
                price = 89.99,
                description = "Crème hydratante pour peaux normales à mixtes.",
                image = "image3",
                ingredients = listOf("Eau thermale", "Glycérine", "Squalane"),
                howToUse = "Appliquer matin et/ou soir sur peau propre.",
                quantity = 15
            )
        )
        return api.getProducts()
    }
}
