# App Design

## 1. Class Diagram

```plantuml
@startuml
hide circle
hide empty methods
skin rose

' classes
class MainActivity{
- selectedCategory : String
- categoryLabels : String[]
- placeInResults : int
- abc : boolean
~ onCreate(savedInstanceState : Bundle)
+ onSearch(word : String)
+ onFavorite() : Word
+ getPlaceInResults() : int
+ getResultsSize() : int
+ onSelectNext() : Word
+ onSelectPrevious() : Word
+ onSelectStart() : Word
+ sort()
+ getAbc() : boolean
+ onGoHome()
+ onViewFavorites()
+ onViewCategories()
+ onBrowse()
+ onSelectCategory(category : String)
+ getCategoryLabels() : String[]
+ onSaveInstanceState(outState : Bundle) 
}

interface IMainView {
+ setNavButtonStatus(homeOn : boolean, browseOn : boolean, favoriteOn : boolean)
+ getRootView() : view
+ displayFragment(fragment : Fragment, addToStack : boolean, name : String) 
}

class MainView {
- fmanager : FragmentManager 
- binding : MainBinding
+ setNavButtonStatus(homeOn : boolean, browseOn : boolean, favoritesOn : boolean)
}

interFace IHomeScreenView {}

class HomeScreenFragment {
- binding : FragmentHomeScreenBinding
+ onCreateView(inflater : LayoutInflater , container : ViewGroup, 
savedInstanceState: Bundle) : View
+ onViewCreated(view : View, savedInstanceState: Bundle) 
}

interface ICategoryBrowse {}

class CategoryBrowseFragment {
- binding : FragmentCategoryBrowseBinding
+ onCreateView(inflater : LayoutInflater , container : ViewGroup, 
savedInstanceState: Bundle) : View
+ onViewCreated(view : View, savedInstanceState: Bundle) 
}

interface ISingleWord {}

class SingleWordFragment {
- binding : FragmentSingleWordBinding
+ onCreateView(inflater : LayoutInflater , container : ViewGroup, 
savedInstanceState: Bundle) : View
+ onViewCreated(view : View, savedInstanceState: Bundle) 
- setFavoriteBtnMsg()
- setSortBtnMsg()
- displayWord()
}

class SignDictionary{
+ getDict() : List<Word>
+ search(text : String) : List<Word>
+ select(text : String) : Word
+ remove(word : Word)
+ add(word : Word)

}
class Word {
- english : String
- asl : int
- isFavorite: boolean
- category : String
+ getEnglish() : String
+ getASL() : int
+ getFavoriteStatus() : boolean
+ getCategory() : String
+ switchFavoriteStatus(status : boolean)
+ compareTo(o : Object) : int

}

class DictionaryBuilder {
+ static filterByCategory(label : String, words : List<Word>) : SignDictionary
+ getFull() : SignDictionary
+ getFavorites() : SignDictionary
+ getCategories() : Map<String, SignDictionary>
+ save(updated : SignDictionary) 
}

interface IPersistenceFacade {
+ saveDictionary(SignDictionary : full)
+ retrieveDictionary() : SignDictionary
}

class LocalStorageFacade {
- fullFile : File
- directory : File
}

class State {
SEARCH_RESULTS,
BROWSE_RESULTS,
FAVORITE_RESULTS,
HOME,
BROWSE_CATEGORY,
}

class AslFragFactory {
+ instantiate (classLoader : ClassLoader, className : String) : Fragment
}

' associations
MainActivity -- "(1) full \n(1) favorites \n(1..*) categories : Dictionary<String, SignDictionary>" SignDictionary 
DictionaryBuilder -- "(1) full \n(1) favorites \n(1..*) categories : Dictionary<String, SignDictionary>" SignDictionary 
SignDictionary -- " (*) words : Dictionary<String,Word> " Word
MainActivity -- "(1) state" State
MainActivity -- " (1) selected \n(*) results : List<Word> " Word
MainActivity "(1) listener" -- "(1) mainView" MainView
MainActivity -- "(1) dictionaryBuilder" DictionaryBuilder
SingleWordFragment -- "(1) listener" MainActivity
HomeScreenFragment -- "(1) listener" MainActivity
CategoryBrowseFragment -- "(1) listener" MainActivity
DictionaryBuilder - "(1) persFacade" LocalStorageFacade
SingleWordFragment -- " (1) displayedWord" Word
AslFragFactory -- "(1) controller " MainActivity

IMainView --> MainView
ISingleWord --> SingleWordFragment
IHomeScreenView --> HomeScreenFragment
ICategoryBrowse --> CategoryBrowseFragment
IPersistenceFacade --> LocalStorageFacade


@enduml
```
## 2. Feature Flow Chart
```plantuml

@startuml

skin rose 

usecase "Search Sign" as search
usecase "Browse Category" as browse
usecase "View Favorites" as favoriteView
usecase "Add/Delete Favorite Sign" as favorite
usecase "Select Sign" as select


search --> select
browse --> select
favoriteView --> select
select -> favorite

favorite --> favorite 



@endum
```
__Workflow Description__
 - The user opens the app
 - The navbar gives the user the option to search for a sign, browse a category, or view favorites.
 - If the user wants to search for a sign, the app displays the signs that are the result from their search. Then they can either add the search sign as their favorite or choose a new option from the navbar. So, on for other features as well.

## 3. Sequence Diagrams

__Navigate App (and View Favorites)__
```plantuml
@startuml
skin rose
hide footbox

actor User as user

participant "mainView : MainView" as ui
participant " categoryBrowseFrag : CategoryBrowseFragment" as bfrag
participant "fravoritesFrag : SingleWordFragment" as wfrag
participant " homeScreenFrag: HomeScreenFragment" as hfrag

participant " : MainActivity" as nav
participant "favorites : SignDictionary" as fav

user -> ui: selects browse button 
ui -> nav : onViewCategories()
nav -> bfrag ** : create()
nav -> ui : displayFragment(categoryBrowseFrag)
ui -> user : displays browse screen

user -> ui: selects favorites button 
ui -> nav : onViewFavorites()
nav -> fav : results = getDict()
nav -> wfrag ** : create()
wfrag -> nav : displayedWord = onSelectStart()
nav -> ui : displayFragment(favoritesFrag)
ui -> user : displays first word in the list of favorites

user -> ui: selects home button 
ui -> nav : onGoHome()
nav -> hfrag ** : create()
nav -> ui : displayFragment(homeScreenFrag)
ui -> user : displays search screen

@enduml
```
__Search Sign__
```plantuml
@startuml
skin rose
hide footbox

actor User as user

participant "mainView : MainView" as ui
participant " : HomeScreenFragment" as hfrag
participant "singleWordFrag : SingleWordFragment" as wfrag
participant "nav : Controller" as nav
participant "full : SignDictionary" as dict
participant "displayedWord : Word" as word

user -> hfrag: types a word into the search bar
hfrag -> nav: onSearch(text)
nav -> dict: results = search(text)

nav -> wfrag ** : create()
wfrag -> nav : displayedWord = onSelectStart()
nav -> ui : displayFragment(singleWordFrag)

alt results is not empty
wfrag -> word : getEnglish()
wfrag -> word : getASL()
wfrag -> word : getFavoriteStatus()
wfrag -> nav : getAbc()
wfrag -> nav : getResultsSize()
wfrag -> nav : getPlaceInResults()
end


ui -> user: displays the first word in the results
@enduml
```

__Select Sign__
```plantuml
@startuml
skin rose
hide footbox

actor User as user

participant " : SingleWordFragment" as ui
participant " : Controller" as nav
participant "displayedWord : Word" as word

user -> ui : clicks next or previous result button

alt next button clicked
ui -> nav : displayedWord = onSelectNext()
else previous button clicked
ui -> nav : displayedWord = onSelectPrevious()
end

ui -> word : getEnglish()
ui -> word : getASL()
ui -> word : getFavoriteStatus()

ui -> nav : getAbc()
ui -> nav : getResultsSize()
ui -> nav : getPlaceInResults()


ui -> user : displays the next or previous word in the results
@enduml
```

__Add or Delete Favorite__
```plantuml
@startuml
skin rose
hide footbox

actor User as user

participant " : SingleWordFragment" as ui
participant " : MainActivity" as nav
participant "favorite : SignDictionary" as favorite
participant " displayedWord : Word" as word

user -> ui: selects add or delete favorite option
ui -> nav: displayedWord = onFavorite()
nav -> word : status = getFavoriteStatus()

alt status is true
nav -> favorite: remove(selected)
nav -> word : switchFavoriteStatus(false)
else status is false
nav -> favorite: add(selected)
nav -> word : switchFavoriteStatus(true)
end

ui -> word : getEnglish()
ui -> word : getASL()
ui -> word : getFavoriteStatus()
ui -> user: displays the updated visual

@enduml
```

__Browse Category__
```plantuml
@startuml
skin rose
hide footbox

actor User as user

participant "mainView : MainView" as ui
participant " : CategoryBrowseFragment" as bfrag
participant "singleWordFrag : SingleWordFragment" as wfrag
participant " : MainActivity" as nav
participant " : SignDictionary" as dict
participant "displayedWord : Word" as word

user -> bfrag: chooses a category
bfrag -> nav: onSelectCategory(category)
user -> bfrag: clicks select button
bfrag -> nav: onBrowse()
nav -> dict: results = getDict()

nav -> wfrag ** : create()
wfrag -> nav : displayedWord = onSelectStart()
nav -> ui : displayFragment(singleWordFrag)

wfrag -> word : getEnglish()
wfrag -> word : getASL()
wfrag -> word : getFavoriteStatus()
wfrag -> nav : getAbc()
wfrag -> nav : getResultsSize()
wfrag -> nav : getPlaceInResults()



ui -> user: displays the first word in the category
@enduml
```