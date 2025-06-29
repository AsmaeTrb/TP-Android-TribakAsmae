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
## 📸 Captures d’écran

Voici un aperçu visuel des principales fonctionnalités de l'application. Chaque écran illustre une étape clé de l'expérience utilisateur, depuis la connexion jusqu'à la finalisation d'une commande.


 🏠 Écran d’
- Invitation à s’inscrire ou se connecter pour une expérience personnalisée.
![Écran d invitation](https://raw.githubusercontent.com/AsmaeTrb/TP-Android-TribakAsmae/refs/heads/master/loginregister.jpeg)
---

 🔐 Authentification
- Inscription (email, mot de passe, prénom, nom)  
- Connexion (email + mot de passe)
![Écran de connexion](https://raw.githubusercontent.com/AsmaeTrb/TP-Android-TribakAsmae/refs/heads/master/loginpage.jpeg)
![Écran d’inscription](https://raw.githubusercontent.com/AsmaeTrb/TP-Android-TribakAsmae/refs/heads/master/registerpage.jpeg)
---

 🛍️ Catalogue
- Parcourir la liste des produits (avec image, nom)  
- Produits classés par catégories (avec possibilité de recherche)
![Catalogue](https://raw.githubusercontent.com/AsmaeTrb/TP-Android-TribakAsmae/refs/heads/master/page%20home.jpeg)
![dresses](https://raw.githubusercontent.com/AsmaeTrb/TP-Android-TribakAsmae/refs/heads/master/catalogue2.jpeg)
![jumpsuits](https://github.com/user-attachments/assets/976951dc-7d5e-40e2-9baf-2bf5711c5d90)
![tops](https://raw.githubusercontent.com/AsmaeTrb/TP-Android-TribakAsmae/refs/heads/master/catalogue3.jpeg)
![bags](https://raw.githubusercontent.com/AsmaeTrb/TP-Android-TribakAsmae/refs/heads/master/ctalogue4.jpeg)





---

 📄 Détail produit
- Affiche l’image, le titre, la description, la disponibilité et le prix  
- Sélection de taille et de quantité via un `BottomSheet`
![detail1](https://raw.githubusercontent.com/AsmaeTrb/TP-Android-TribakAsmae/refs/heads/master/pagedetail1.jpeg)
![detail2](https://github.com/user-attachments/assets/6e88de24-6f71-44b6-b537-206538cedc91)


---

🎯 Sélection taille & quantité
- Modal interactif pour choisir la taille  
- Affichage du stock disponible par taille  
- Vérification des quantités avant ajout au panier
![quantity](https://raw.githubusercontent.com/AsmaeTrb/TP-Android-TribakAsmae/refs/heads/master/taille-quantity.jpeg)


---

### 🛒 Panier & Checkout
- Aperçu rapide du panier avec quantités et tailles choisies
![panier1](https://raw.githubusercontent.com/AsmaeTrb/TP-Android-TribakAsmae/refs/heads/master/paniervide.jpeg)
![panier2](https://raw.githubusercontent.com/AsmaeTrb/TP-Android-TribakAsmae/refs/heads/master/panierajouter.jpeg)
- Sélection du mode de paiement :
  - Carte bancaire : formulaire (nom, numéro, date, CVV)
  - Redirection vers PayPal (simulée)  
- Calcul du total (produits  + frais de livraison)  
- Mise à jour automatique du stock après validationhttps:
![image]https://raw.githubusercontent.com/AsmaeTrb/TP-Android-TribakAsmae/refs/heads/master/checkout-shipping.jpeg
![image](https://raw.githubusercontent.com/AsmaeTrb/TP-Android-TribakAsmae/refs/heads/master/bagdetails.jpeg)
![image](https://raw.githubusercontent.com/AsmaeTrb/TP-Android-TribakAsmae/refs/heads/master/checkout-shipping1.jpeg)
![image](https://raw.githubusercontent.com/AsmaeTrb/TP-Android-TribakAsmae/refs/heads/master/checkoutpayment.jpeg)
![image](https://raw.githubusercontent.com/AsmaeTrb/TP-Android-TribakAsmae/refs/heads/master/shippingmethodcard.jpeg)
![image](https://raw.githubusercontent.com/AsmaeTrb/TP-Android-TribakAsmae/refs/heads/master/shippingmethodepaypal.jpeg)
![image](https://raw.githubusercontent.com/AsmaeTrb/TP-Android-TribakAsmae/refs/heads/master/checkoutconfirmation.jpeg


---

 👤 Profil & Historique
- Affichage de l’e-mail de l’utilisateur connecté  
- Historique des commandes avec statut (ou message « Aucune commande »)  
- Accès aux favoris
![image](https://raw.githubusercontent.com/AsmaeTrb/TP-Android-TribakAsmae/refs/heads/master/profil.jpeg)
![image](https://raw.githubusercontent.com/AsmaeTrb/TP-Android-TribakAsmae/refs/heads/master/profil-historiquecommmande.jpeg)
![image](https://raw.githubusercontent.com/AsmaeTrb/TP-Android-TribakAsmae/refs/heads/master/favorisnonconnecte.jpeg)




---

❤️ Favoris
![Favoris](./assets/wishlist.png)
- Ajouter ou retirer un produit des favoris  
- Visualisation des articles aimés depuis l’onglet dédié

---


