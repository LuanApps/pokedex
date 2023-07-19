# Pokedex - Made by Luan Ramos

## Project Libraries

This project utilizes the following libraries:

- Retrofit: Used for making API calls to an HTTP web service.
- Moshi: Handles the deserialization of the returned JSON into Kotlin data objects.
- Glide: Utilized for loading and caching images by URL.
- Room: Enables local database storage.

## Jetpack Library Components

The app implements various Jetpack library components, enhancing its functionality and design:

- MVVM architecture design: Promotes separation of concerns, making the app more maintainable and scalable.
- LiveData: Allows for reactive programming, updating the UI whenever data changes.
- Data Binding: Simplifies UI components' interaction with the data, enhancing code readability.
- Navigation: Facilitates in-app navigation between different fragments and activities.
- RecyclerView: Makes it easy and efficient to display large datasets.
- MotionLayout: Adds engaging animations to improve user experience.

## App Usage Demonstration

Below is an accelerated video demonstrating how to use the Pokedex app:

![Accelerated gif demonstrating the app usage](Pokedex_video.gif)

Please note that Pokemon is a trademark of Nintendo Company.

<<<<<<< HEAD
**Disclaimer:** This app and its content are purely for educational purposes and are not affiliated with or endorsed by Nintendo Company or the Pokemon franchise.

## Pokedex App Design Document

# Introduction
The Pokedex App is a fun game that allows users to explore and discover various Pokémon species. Users can interact with the app by attempting to guess the correct Pokémon based on a dark silhouette. If the user guesses correctly, they can catch the Pokémon and view its details. The app follows the MVVM architecture and utilizes Retrofit and Moshi to fetch Pokémon data from the PokeAPI and Room for local data storage.

# Pokemon Class
The Pokemon class is a data class representing individual Pokémon entities. It holds essential information about a Pokémon, including its ID, base experience, height, name, weight, images (frontDefault and frontShiny), base stats (HP, Attack, Defense, Special Attack, Special Defense, and Speed), and types.

# Gameplay Flow

1. Main Fragment
The user opens the app and lands on the MainFragment.
The MainFragment displays a list of caught Pokémon names and a "Search Pokemon" button.
When the user clicks the "Search Pokemon" button, they navigate to the SearchPokemonFragment.
When the user clicks on a Pokémon in the list, they can view the Pokémon details.
2. Search Pokemon Fragment
The SearchPokemonFragment displays a dark silhouette of a Pokémon and four options containing Pokémon names.
The user clicks on one of the options to make a guess.
If the guess is correct, the chosen option turns green, and the user can catch the Pokémon.
If the guess is incorrect, the user can try again.
3. Catching Pokemon
When the user catches a Pokémon, its data is saved locally using Room for offline access.
If it's the first Pokémon caught, a notification with a congratulatory message and the Pokémon's name is shown.
If the user clicks on the notification, they navigate to the Pokemon details fragment showing the caught Pokémon data.
4. Pokemon Details Fragment
The Pokemon details fragment shows the Pokémon name, a image, a switching button to change the image to the shiny Pokémon image, and other Pokémon data.
5. Motion Layout
Whenever the user switches between fragments, a Motion Layout is used to provide smooth transitions and a visually appealing experience.

# Architecture and Navigation
The app follows the MVVM architecture for clean separation of concerns and better maintainability.
Navigation between fragments is implemented using the Navigation component to navigate between activities and pass bundle data between them.

# Data Fetching and Storage
Retrofit and Moshi are used to fetch Pokémon data from the PokeAPI to show the silhouette and options in the "Search Pokemon" fragment.
Room is used for local data storage to save the Pokémon data after catching them.
||||||| 1bafbb4
**Disclaimer:** This app and its content are purely for educational purposes and are not affiliated with or endorsed by Nintendo Company or the Pokemon franchise.
=======
**Disclaimer:** This app and its content are purely for educational purposes and are not affiliated with or endorsed by Nintendo Company or the Pokemon franchise.
>>>>>>> e11324c6fded72d46f87749488749cc50c501b8f
