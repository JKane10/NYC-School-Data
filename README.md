## README

## About
This repository contains a simple, single activity two fragment native MVVM Android application
written in both Kotlin and Java.
The first fragment contains a list of NYC Schools
The second fragment gives more detailed information on the school you select from the list.
This application is powered by the NYC Open Data API. (https://opendata.cityofnewyork.us/)

## Steps to run
1. Clone repository
2. Open in Android Studio
3. (Optional) If you'd like images, open app/build.gradle
    - Set the 'PLACES_API_ENABLED' build config to true
    - Open keystore.properties and provide a Google Places API Key
4. Run the project.

![alt text](NYC_School_Viewer_App.png)

### Libraries / Dependencies used
* Retrofit - Network calls (https://square.github.io/retrofit/)
* RxJava - Reactive functionality (http://reactivex.io/) or (https://github.com/ReactiveX/RxJava)
* Chuck - HTTP inspection (debugging) (https://github.com/jgilfelt/chuck)
* Glide - Image loading (https://github.com/bumptech/glide)
* Dagger - Dependency injection (https://github.com/google/dagger)
* Stetho - More debugging (https://github.com/facebook/stetho)
* ktlint - Linting / code quality (https://ktlint.github.io/)
* Image Icons / Assets came from https://material.io/resources/icons/

### APIs
* Google Places -https://cloud.google.com/maps-platform/places
* NYC Open Data API - https://opendata.cityofnewyork.us/

### Future improvements list
- Write instrumentation testing.
- Implement simple caching for offline usage.

### GIF
![alt_test](NYC_Schools_List.gif)
