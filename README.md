
**#🎬 Movie Catalogue**

A Spring Boot + Thymeleaf web application that allows users to browse movies (via TMDb API), search, view details, mark favorites, and set a **highlight** movie.
Built as a course project for **CSD-XXXX** at Lambton College.

---

## Features

- **Home Page (Trending Movies)** – Displays trending movies of the week from TMDb.
- **Search** – Find movies by title using TMDb search API.
- **Movie Details** – View synopsis, release date, rating, and poster.
- **Favorites** – Add/remove movies to your personal favorites list (persisted in the database).
- **Highlight Movie** – Mark one movie as a highlight (only one highlight at a time).
- **Highlights Page** – View your selected highlight movie in a dedicated section.
- **Dark Mode UI** – Simple and modern dark-themed design.

---

## 📂 Project Structure

src/main/java/com/example/moviecatalogue

│

├── controller/ # Web controllers (FavoriteController, MovieController, etc.)

├── entity/ # JPA entities (FavoriteMovie)

├── repository/ # Spring Data JPA repositories

├── service/ # Business logic services

└── MovieCatalogueApplication.java


src/main/resources/templates/

│

├── index.html         # Home / Search results

├── favorites.html     # Favorites list

├── fragments/header.html # Navigation bar

└── movie-details.html # Movie detail page


## 🛠 Tech Stack

* **Backend:** Java 17, Spring Boot, Spring Data JPA
* **Frontend:** Thymeleaf, HTML/CSS (inline styling for simplicity)
* **Database:** H2 (in-memory, persistent for session)
* **API:**[The Movie Database (TMDb)]()
* **Build Tool:** Maven

---

## ⚙️ Setup Instructions

1. **Clone the repository**

   git clone https://github.com/rishabh-kalathiya/movie-catalogue
   cd movie-catalogue
2. **Set your TMDb API key**

   Open `src/main/resources/application.properties` and add:

   tmdb.api.key=YOUR_API_KEY_HERE
3. **Run the application**

   ./mvnw spring-boot:run
4. **Access in browser**

   http://localhost:8080
