# item-finder

<h1 align="center">Hi 👋, I'm Macarena Latini 👩🏻‍💻 </h1>

<p align="center">This is a Item Finder app 🔍</p>

<h2 align="left">About Item Finder App</h2>
This app allows you to browse a wide variety of products (both new and used), services (such as those provided by teachers, transportation providers, etc.), real estate (available for rent or purchase), and vehicles in Argentina. The app was built using the public API found at https://developers.mercadolibre.com.ar/es_ar/api-docs-es. ❗️ <i> Note: This API only support spanish 😔 </i> ❗️

You can easily search for what you're looking for by exploring categories or by conducting a specific search. All results are clearly listed, and you can click on any item to learn more about it, including details like price, condition, and a description provided by the seller.


<h2 align="left">About this project</h2>
This project is 100% Kotlin :heart: and it follows the MVVM (Model-View-ViewModel) architectural pattern.<br>

👉🏼 Project structure:

/api: This folder contains files related to network services.
- model: In this folder, the necessary data classes are created to model the request data for services.
- service: In this folder, a new folder is created with the following 3 files:
  - NameApi: Interface with Retrofit endpoints.
  - NameService: Class for implementing requests.
  - INameService: Interface with the function definitions required for making requests.

/model: This folder contains two files for data logic:
  - NameModel: Implementation of data logic rules.
  - INameModel: Data logic interface.

/utils: As the name suggests, this folder contains classes that are useful in other objects or classes, such as number formatting.

/views: This folder contains activities and adapters grouped by component.

/viewmodel: Contains viewmodels, with the viewmodel interface and the corresponding viewmodel grouped together.

<h2 align="left">About the code</h2>
🤩 This project uses several Design Patterns, like: Builder, Factory, Adapter, Singleton and Observer.

👉🏼 Additionally, you'll find that the project makes use of several Kotlin features, including:
- High order functions and lambdas
- Sealed classes
- Extension functions
- Coroutines and suspend functions
- lateinit and lazy initializations

📱 In terms of user interface, this project makes use of various techniques to improve responsive design and performance, including:
- MotionLayout for building splash animation
- Guidelines to support different screen sizes
- XML optimization to reduce overdraw (try using developer options!)
- ViewStub for better layout inflating
- Different XML layouts for portrait and landscape modes.

❗️ Error handling:
- Empty responses
- Network error
- Generic errors

🤝🏻 Improvements:
Some potential future improvements for the app include:
- Implementing support for retrying errors. Currently, some errors result in a static screen without a retry option.
- Expanding the available item details for users to view.
- Adding more tests to improve the app's stability and reliability.
- Creating more detailed documentation.

💻 Some conventions that are followed in this project include:
- Rebasing instead of merging to keep the codebase updated with the latest changes.
- Squashing commits to keep each feature or bugfix in a single, descriptive commit, rather than having multiple small commits that may clutter the history.

<h2 align="left">See the app running:</h2>

<p>Gif:<br></p>


https://user-images.githubusercontent.com/26662708/218946593-ac301ad8-1d15-48da-a167-b5e512cb6f83.mp4



<p>Home:<br></p>
<p><img src=https://user-images.githubusercontent.com/26662708/218944693-7b0b4c62-dadd-42a3-a608-0024e309728c.jpg width="400">
<br></p>

<p>Item detail (portrait):<br></p>
<p><img src=https://user-images.githubusercontent.com/26662708/218944741-674730a9-f959-4951-96b8-31db1923f158.jpg width="400">
<br></p>

<p>Item detail (landscape):<br></p>
<p><img src=https://user-images.githubusercontent.com/26662708/218944830-8eb1a0b5-2e7f-4dda-91a0-5d3803cf5600.jpg width="400">
<br></p>



<h2 align="left">Thank you! 😁</h2>

