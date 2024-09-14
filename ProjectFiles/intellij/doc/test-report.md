# Test Report Description

__Prototype name__: [App Name] Phase I

__Date of Testing__: 10 October 2023.

__Tester__: Becca and Elta.

# Overview:
The testing done on the App prototype is summarized in this test report. 
The prototype is an early version of the [App Name] that concentrates on aspects like 
sign language translation, dictionary functionality, and favorites management.

# Testing Goals:
Our main goals of the prototype testing were to:
- Analyze the app usability and user experience.
- Check the operation and accuracy of the key features, such as "Search Bar", "View Favorites" and "Category Browsing". 
- Identify any usability problems, errors, or interface and interaction improvements areas.
- Get feedback from the tester regarding the overall experience of the prototype.

# Testing methods
Scenario-based testing and manual exploratory testing were both used in the testing process. 
The tester interacted with the prototype, going through pre-planned test scenarios and investigating the features of the app.

# Key Findings
While testing we were able to observe the following:
1. The user was able to input words for sing language translation successfully via the "Search Bar" functionality. The animations provided were easy to understand and clear.
2. The user was able to add/ delete favorite signs "Favorite List" feature.
3. The user was able to know the available words through the "Browse Categories" functionality, which made it easier for the user to explore the new signs.
4. Overall, there were no critical usability problems or major errors during testing, which provided a positive feedback for the user.

# Unit Testing Feedback
Below is the desired output taken from the search sign testing:

Welcome to our ASL translation app!

Type search, browse category, view favorites or quit: search 

Choose a word: milk

milk The ASL representation is at team-1e/intellij/img/milk.gif This sign is not favorited.

Type add favorite, delete favorite, go home or quit: add favorite

milk The ASL representation is at team-1e/intellij/img/milk.gif This sign is favorited.

Type go home, delete favorite, or quit: delete favorite

milk The ASL representation is at team-1e/intellij/img/milk.gif This sign is not favorited.

Type go home, add favorite, or quit: go home

Welcome to our ASL translation app!

Type search, browse category, view favorites or quit: browse category

adjective

food

Type one of the above categories: adjective

big The ASL representation is at team-1e/intellij/img/...gif This sign is not favorited.

small The ASL representation is at team-1e/intellij/img/...gif This sign is not favorited.

Type select word, go home or quit: select word

Choose a word: big

big The ASL representation is at team-1e/intellij/img/...gif This sign is not favorited.

Type add favorite, delete favorite, go home or quit: add favorite

big The ASL representation is at team-1e/intellij/img/...gif This sign is favorited.

Type go home, delete favorite, or quit: go home

Welcome to our ASL translation app!

Type search, browse category, view favorites or quit: view favorites

big The ASL representation is at team-1e/intellij/img/...gif This sign is favorited.

Type select word, go home or quit: quit

Process finished with exit code 0

# Future Improvements:
For future development and improvement the following recommendations are going to be made:
1. Include additional categories and signs to ensure a nice coverage of sign language vocabulary.
2. Identify personalization options to make the "view favorite list" feature process smoother.
3. Continue testing the app with the above changes and gather diverse feedback.
4. Consider adding filtering option for "search sign".
