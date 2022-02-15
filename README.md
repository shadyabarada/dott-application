# dott-application
Dott Android Code Challenge

Architecture:
1. Using MVVM architecture for lifecycle
2. Using KOIN for dependency injection 
3. Using retrofit for network calls 
4. Using room for caching purposes

Build Variants:
1. The app has 2 build variants: release and debug
2. Each build variant has its own restricted api key for google maps

General workflow:
1. When the app starts you are requested for location permission - if declined you can't check the nearest restaurants, you will have to grant it again from settings
2. When the location permission is accepted, an api call is done to get the nerest restuarants based on the viewport llimits of your location and they are cached 
3. When panning the map, if at least 10 resturants within your view port are cached, we don't do a service call, we get them from cache
4. On click on a restaurant item in the map, a dialog fragment appears with the restaurant details

Futher enhancements:
1. The api keys can be stored as build configuration instead of xml for security purposes
2. The UI can be enhanced to add loading bar when loading the restaurant details
3. Unit tests for the full MVVM and Koin lifecycle can be added
4. Also fun to add: Splash screen, Welcome screen requiring permission, ask the permission again after certain number of declines or after a certain time

