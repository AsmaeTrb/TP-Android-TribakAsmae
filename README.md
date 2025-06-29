# 🛍️ APPLICATION MOBILE E-COMMERCE - TRIBAK STORE

Une application mobile de e-commerce développée en **Kotlin avec Jetpack Compose**, permettant aux utilisateurs de parcourir des produits, consulter les détails, ajouter au panier, passer commande et gérer leurs favoris.

> Projet réalisé dans le cadre du Master DevOps & Cloud Computing  
> Université Abdelmalek Essaâdi — FPL Larache  
> Développé par **Mohamed Tribak**

---

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
![Écran d invitation](https://github.com/user-attachments/assets/fc4a8b93-fcc9-4c83-8247-381c3e93eff1)
---

 🔐 Authentification
![Écran de connexion](https://github.com/user-attachments/assets/3476d6a9-65ce-4765-9587-56c246eed795)
![Écran d’inscription](https://github.com/user-attachments/assets/9b9c84b3-90ca-48bc-b7c1-c88c2ff126eb)
- Inscription (email, mot de passe, prénom, nom)  
- Connexion (email + mot de passe)

---

 🛍️ Catalogue
- Parcourir la liste des produits (avec image, nom)  
- Produits classés par catégories (avec possibilité de recherche)
![Catalogue](https://github.com/user-attachments/assets/e14ab993-17ef-4ecf-9d23-0de3305e549d)
![dresses](https://github.com/user-attachments/assets/05e35f1f-ee80-4a4a-b4c6-25a212d1b27f)
![jumpsuits](https://github.com/user-attachments/assets/976951dc-7d5e-40e2-9baf-2bf5711c5d90)
![tops](https://github.com/user-attachments/assets/4517c401-37cd-4162-8e16-eb4f3b55eb5f)




---

 📄 Détail produit
- Affiche l’image, le titre, la description, la disponibilité et le prix  
- Sélection de taille et de quantité via un `BottomSheet`
![detail1](https://github.com/user-attachments/assets/fa395188-7df4-4cac-8cd8-c817ca530350)
![detail2](https://github.com/user-attachments/assets/6e88de24-6f71-44b6-b537-206538cedc91)


---

🎯 Sélection taille & quantité
- Modal interactif pour choisir la taille  
- Affichage du stock disponible par taille  
- Vérification des quantités avant ajout au panier
![quantity](https://github.com/user-attachments/assets/80c76b49-a7ee-4606-bafe-90b509b7bb44)

---

### 🛒 Panier & Checkout
- Aperçu rapide du panier avec quantités et tailles choisies
![panier1](https://github.com/user-attachments/assets/11af0265-5bd6-49ea-b0cd-534f35668673)
![panier2](https://github.com/user-attachments/assets/aa7eaf1d-7da7-47c2-be72-ca64248cd6ee)
- Sélection du mode de paiement :
  - Carte bancaire : formulaire (nom, numéro, date, CVV)
  - Redirection vers PayPal (simulée)  
- Calcul du total (produits  + frais de livraison)  
- Mise à jour automatique du stock après validation
![image](https://github.com/user-attachments/assets/20a480f2-e929-42dc-b280-44ee6b2884e9)
![image](https://github.com/user-attachments/assets/0d9c594a-8b2c-466f-bee0-e1da5fbf6449)
![image](https://github.com/user-attachments/assets/8ed9e689-3118-4a4f-bc67-2a5dc1109593)
![image](https://github.com/user-attachments/assets/f9c125ed-c8c2-44d7-a315-3d918ab76c76)
![image](https://github.com/user-attachments/assets/7a44c8e7-4f7d-4489-9b75-a25aac19dced)
![image](https://github.com/user-attachments/assets/a7b07ca2-a490-4165-b329-afe670f6db95)


---

 👤 Profil & Historique
- Affichage de l’e-mail de l’utilisateur connecté  
- Historique des commandes avec statut (ou message « Aucune commande »)  
- Accès aux favoris
![image](https://github.com/user-attachments/assets/497dd376-6df6-41db-b7de-81959eccbe4f)
![image](https://github.com/user-attachments/assets/e4e8ef52-8a8d-4a19-b7ef-dffabb22a0e7)
![image](https://github.com/user-attachments/assets/9b57803a-cc8f-4078-92d8-914eb87b211b)




---

❤️ Favoris
![Favoris](./assets/wishlist.png)
- Ajouter ou retirer un produit des favoris  
- Visualisation des articles aimés depuis l’onglet dédié

---


