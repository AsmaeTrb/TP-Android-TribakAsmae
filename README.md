# 🛍️ APPLICATION MOBILE E-COMMERCE - TRIBAK STORE

Une application mobile de e-commerce développée en **Kotlin avec Jetpack Compose**, permettant aux utilisateurs de parcourir des produits, consulter les détails, ajouter au panier, passer commande et gérer leurs favoris.

> Projet réalisé dans le cadre du Master DevOps & Cloud Computing  
> Université Abdelmalek Essaâdi — FPL Larache  
> Développé par **Mohamed Tribak**

## ✨ Fonctionnalités principales

- 🛒 Parcourir la liste des produits (avec image, nom)
- 🔍 Voir les détails d’un produit (description, tailles disponibles, stock)
- ➕ Ajouter un produit au panier (par taille, avec quantité)
- 🧾 Passer une commande et voir le total avec taxes & livraison
- 🧑‍💻 Authentification (inscription / connexion)
- 📦 Suivre l’historique de commandes
- ❤️ Ajouter ou retirer des favoris
- 📲 Interface fluide, moderne et responsive avec Jetpack Compose

---

## 🛠️ Technologies utilisées

| Côté Android (Jetpack Compose)       | Backend (Node.js + MongoDB)      |
|--------------------------------------|----------------------------------|
| Kotlin, Jetpack Compose, Material3   | Node.js, Express.js              |
| MVI (Model–View–Intent)              | MongoDB Atlas                    |
| Hilt (Injection de dépendances)      | Mongoose                         |
| Navigation Compose                   | API REST sécurisée (JWT optionnel) |
| Retrofit & Gson (API)                | Nodemon pour le dev              |
| Coil (images)                        |                                  |
| Lottie (animations)                  |                                  |
| Room / DataStore (si stockage local) |                                  |

---

## 📦 Extrait des dépendances `build.gradle.kts`

```kotlin
// UI & navigation
implementation("androidx.compose.ui:ui:1.4.0")
implementation("androidx.compose.material3:material3:1.0.1")
implementation("androidx.navigation:navigation-compose:2.7.5")
implementation("androidx.compose.material:material-icons-extended:1.6.0")

// Hilt
implementation("com.google.dagger:hilt-android:2.56.1")
ksp("com.google.dagger:hilt-compiler:2.56.1")

// API
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

// Images & animation
implementation("io.coil-kt:coil-compose:2.2.2")
implementation("com.airbnb.android:lottie-compose:6.1.0")

---


