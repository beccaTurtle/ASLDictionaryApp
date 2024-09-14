# Use Case: Navigate App

__Description__:
Users of the ASL App can explore and access various features and functionalities within the app thanks to the "Navigate App" use case, which offers a fluid and user-friendly experience.

## 1. Primary actor and goals
__User__:  The primary actor who wishes to use the  ASL app, access particular features, and take different actions in accordance with their requirements.

## 2. Other stakeholders and their goals
__Developers__: Ensure that users have an easy-to-use navigation experience.
__Educators__: Teachers and students may receive guidance on how to use the app successfully for learning sign language.

## 3. Preconditions
The app is installed and operational on the user's device.

## 4. Postconditions
The user accesses the intended features or content after successfully navigating through the program.


## 5. Workflow

```plantuml
@startuml

skin rose

title Navigate App (casual level)

'define the lanes
|#application|User|
|#implementation|App|

|User|
start
:Opens the app on their device;

|App|
:Views the app's main screen with navigation options;
|App|
:Displays the app's home screen with feature icons;

|User|
if (Wants to look up a specific word?)
:Types in search bar;
else if (Wants to browse a category?)
:Selects browse icon;

|App|
:Displays the categories in a dropdown menu;

|User|
:Selects a category from the dropdown menu;

else if (Wants to view favorited signs?)
:Selects favorite list icon;
endif

|App|
:Displays the signs from the selected category;

|User|
:Navigates the signs with next and previous button;
if (Wants to favorite or unfavorite the sign)
:Selects favorite icon;

|App|
:Adds/removes the sign from the favorite list;

endif

|User|
:Navigates back to the main screen;

stop
@enduml
```

__Workflow Description__:

The User opens the app on their device.
The Navigate App function takes the user to the app's home page, where navigation options and feature icons are displayed.
To access a particular section of the software, the User chooses an icon or feature from the home screen.
The Navigate App function directs the user to the chosen section or feature.
After that, the User can engage with the feature they have chosen by searching, examining their favourites, or browsing their sign language.
The User can repeat the process to return to the home screen if necessary or explore other options or search again for a new sign.

