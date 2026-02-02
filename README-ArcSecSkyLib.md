ArcSecond-SkyLibrary
Project for Net Scrap Development Corporation, LLC

Description
ArcSecond SkyLibrary is an app that allows the user to pick and choose to view information of the 88 modern constellations recognized by the International Astronomical Union. The constellations' information is all stored within a CSV file that comes with the ArcSecond_SkyLibrary.zip file.

On the home screen, users have the choice of logging in, signing up, or continuing as a guest, which their login or signup information will be saved on a locally made file. Upon successful login, signup, or continuing as a guest, the user will be brought to a screen welcoming them and presenting three buttons: North, South, and View Favorites. The View Favorites button is only available for logged in users. The user can choose whether they want to look at constellations in the Northern or Southern celestial hemisphere.

After choosing a hemisphere, a RecyclerView of constellations in the specific hemisphere is loaded with two constellations in each row. Each constellation has its name and an image on its clickable button. Users can click on any of the constellations to bring up information about every star belonginging to the constellation and all of the information of each star. The information currently available is the star's name, right ascension, declination, apparent magnitude, the direction of its celestial hemisphere, and which constellation the star belongs to.

Users that have an account have the option to favorite a constellation. Once they do, when they return to the screen where they can choose North, South, or View Favorites, a logged in user can click View Favorites to bring up a list of every constellation they have favorited. The constellations are loaded onto the screen and users can easily view the information of the constellations they have favorited.

The Users are managed by the User and UserManager class. All the constellation information is managed by the Sky, Constellation, and Star classes.

Contributors
Renae Manalastas

Mark Finley

Angela Oommen

Adine Bahambana

Run Requirements
Android Studio
Android emulator with API 31 or later
Internet
constellations.csv, file included in ArcSecond_SkyLibrary/ArcSecond_SkyLibrary/app/src/main/assets
How to Run
Download and unzip ArcSecond_SkyLibrary.zip
Open the unzipped folder in Android Studio
Open an Android device emualtor inside Android Studio with API 31+
After Gradle sync, run the program by pressing the green play button at the top of the screen (Shift+F10 on Windows devices)
Known Issues
Runtime Exception when guest attempts to access information to a constellation ✅ Handled guest users
Guests should not be able to favorite constellations at all, so the favorite button should not appear to them. ✅ Toasts a message instead of allowing guests to favorite.
