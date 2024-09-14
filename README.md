# AndroidInterViewChallenge

- What frameworks or supporting libraries did you use to make the task simpler and/or easier to
  accomplish?
- I have used Clean Architecture with MVVM pattern, Used different libraries mention below 
  * Jetpack Compose
  A modern Android UI toolkit that simplifies building dynamic, reactive user interfaces.
 * Koin (Dependency Injection)
 * Retrofit (Networking)
 * ExifInterface (Image Orientation Handling)
 * Kotlin Coroutines (Asynchronous Programming)
- How did you ensure that the display of the avatar image (from a remote URL) gave the best user
  experience?
- I apply and SubcomposeAsyncImage properties to make sure the display of the avatar image will give good experience and handle state of image like, loading, success, error. such, as i apply content scale and enable diskCachePolicy to prevent the load the image from network again and again.
- How did you set up the app so that it was automatically logged in for the user on subsequent
  uses?
- I have used SharedPreference to store the accessToken of the user after successfully login and on the bases of the accessToken i identify if the user is already logged in or not.
- How did you cope with building the sample app without having access to the real back-end API?
- I have create the FakeUserProvider class which is responsible to provide me fake dummy data.
- What testing did you do, and why?
- I write Unit Tests cases for the Login Screen to show the behavior verification and isloation and also code quality.