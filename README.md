<<<<<<< HEAD


# 💬 Chat App 📱

## ✨ Project Overview

This project is a **real-time chat application** developed for Android. It allows users to communicate with each other through text messages. The app utilizes **Firebase** for authentication, real-time database, and cloud messaging.

## 🚀 Key Features

*   **User Authentication:** Users can sign up with their email and password or use Google Sign-In [1-3].
*   **Real-Time Chat:** Supports one-on-one text-based conversations with real-time updates [4-7].
*   **User List:** Displays a list of registered users to initiate chats [8-10].
*   **Message Sending and Receiving:** Users can send and receive messages in real-time [5, 7, 11, 12].
*   **Search Functionality:** Users can search for other users by name or email [13-15].
*   **Push Notifications:** The app uses Firebase Cloud Messaging (FCM) to send and receive push notifications [16-20].
*   **User Profile:** User profiles include a display name, email, and profile image [21, 22].
*   **Loading Animations:** Provides visual feedback during loading operations [21, 23, 24].

<video src="assets/demo.mp4" controls width="600"></video>


## 🛠️ Technologies Used

*   **Android SDK:** For building the native Android application.
*   **Kotlin:** The primary programming language used for development.
*   **Firebase:**
    *   **Firebase Authentication:** Manages user accounts and sign-in [2, 25-27].
    *   **Firebase Realtime Database:** Stores and synchronizes chat messages [6, 8, 12, 25, 26].
    *    **Firebase Firestore:** Used for storing user information [9, 27, 28].
    *   **Firebase Cloud Messaging (FCM):** Handles push notifications [16, 18, 19].
*   **Android Architecture Components:**
    *   **Fragments:** For modular UI design [1, 29-33].
    *   **Navigation Component:** Manages navigation between app screens [1, 29-33].
    *   **RecyclerView:** For displaying lists of users and messages [29, 30, 33].
*    **Gson:** For parsing data to and from JSON [9, 34-37].
*   **Lottie:** For displaying animations [23, 24, 32, 38].

## ⚙️ Setup Instructions

1.  **Clone the repository** to your local machine.
2.  **Set up Firebase:**
    *   Create a new project in the [Firebase Console](https://console.firebase.google.com/).
    *   Add an Android app to your Firebase project.
    *   Download the `google-services.json` file and place it in your `app` directory.
    *   Enable Authentication and Realtime Database in the Firebase console.
3.  **Configure Android Studio:**
    *   Import the project in Android Studio.
    *   Ensure all dependencies are correctly set up by syncing the Gradle project.
4.  **Run the app** on an emulator or physical device.

## 📁 File Structure
* `com.astech.chatapp`:
    *   `adapters`:
        *  `ChatAdapter.kt` : Provides the adapter for displaying chat messages in the RecyclerView [29, 30, 39]
        *  `UserAdapter.kt` : Provides the adapter for displaying a list of users [33, 40, 41].
    *  `databinding`: Automatically generated classes that enable interaction with view layouts [1, 29-33].
    *  `models`:
        * `ChatMessage.kt`: Defines the structure of a chat message [25, 26, 35].
        * `Message.kt`: Defines the structure of a message [25, 26].
        * `User.kt`: Defines the structure of a user profile [10, 25-27].
    *   `view`:
        *    `ChatListFragment.kt`: Displays a list of users and allows users to start a chat with other users [9, 15, 33].
        *   `ChatFragment.kt`: Displays the chat interface and handles sending and receiving messages [29, 34, 36, 39].
        *    `LoginFragment.kt`: Handles user login functionality [1, 3, 23].
        *    `SignupFragment.kt`: Handles user registration functionality [21, 31, 42, 43].
        *    `SplashFragment.kt`:  Displays a splash screen when the app is launched and navigates to the login or chat list screen based on login status [32, 44, 45].
    *   `MainActivity.kt`: The main activity of the app. Requests FCM token [16, 17].
    *   `MyFirebaseMessagingService.kt`: Handles Firebase Cloud Messaging, specifically push notifications [18-20].

## 🖼️ UI Components

*   `FragmentChatBinding`: Layout for the chat screen, including the message list and input area [6, 29, 30, 34-36].
*   `FragmentLoginBinding`: Layout for the login screen with email and password fields [1, 3, 23, 24, 46, 47].
*   `FragmentSignupBinding`: Layout for the signup screen, including fields for username, email, password, and profile image selection [27, 31, 42].
*   `FragmentChatListBinding`: Layout for the chat list screen, including a list of users and a search view [9, 13, 15, 33].
*   `FragmentSplashBinding`: Layout for the splash screen that is shown when the app starts [32, 38].
*   `user_item.xml`: Layout for the user item in the user list [48].

## 🔑 Firebase Integration

*   **Authentication:** Uses `FirebaseAuth` for user authentication [2, 25-27].
*   **Realtime Database:**  Uses `FirebaseDatabase` to store and retrieve chat messages [6, 8, 10, 12, 34, 35, 49].
    *   Messages are stored under the `chats` node, with separate child nodes for each unique sender and receiver combination.
*   **Firestore:** Used to store user data [9, 27, 28]
*   **Cloud Messaging:**  Uses `FirebaseMessaging` for handling push notifications [16, 18].

## 💡 Key Concepts

*   **Fragments:** The app uses fragments to create modular UI components, which helps in managing the UI and navigation [1, 29-33].
*   **Adapters:** `ChatAdapter` and `UserAdapter` are responsible for binding data to the `RecyclerView` and updating the UI when the data changes [29, 30, 40, 50].
*   **Real-Time Updates:** `addValueEventListener` is used to listen for changes in the database, which allows real-time updates in the UI [4-7].
*    **User IDs:** User IDs are used to create unique chat rooms to separate conversation data. [39].
*   **Data Modeling:** Classes like `ChatMessage` and `User` are used to structure the data used in the application [25-27, 35].
*   **Asynchronous Operations:**  Uses Kotlin's `addOnCompleteListener` pattern to handle asynchronous operations, such as signing in, creating users, and updating the user profile [21-23].

## ⚠️ Known Issues and Future Enhancements

*   **Image Loading:**  Image loading with Glide or Picasso needs to be implemented. [41]
*   **Message Delivery Status:** Add message delivery and read receipts.
*   **Error Handling:** Improve error handling for various scenarios.
*   **UI/UX Improvements:** Further enhance UI/UX for better user engagement.
*   **Group Chat:** Implement group chat functionality.
*   **Media Sharing:** Add functionality for sharing images, videos, and audio.
*   **Data Validation:** Implement more data validation before submitting data to firebase.

## 📜 License

This project is open source and available under the MIT License.
This README.md file provides a good overview of the project. It explains what the app does, what technologies are used, how to set it up, and what the structure looks like. You can copy and paste this content into a file named README.md in the root of your project directory.