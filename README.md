# Travello
## A personal trip planning and tracking application

Travello is a Java desktop application that helps users organize their trips by managing three main features: a travel itinerary, an expense tracker, and a packing checklist. Users can create a trip, add scheduled activities for each day, record travel-related expenses, and manage a checklist of items they need to bring. The system also includes smart features such as itinerary conflict detection, budget alerts, and expense summaries by category.

In Phase 1, the program will run as a console-based application. In Phase 2, it will support saving and restoring the full state of the application. In Phase 3, the application will be expanded into a graphical user interface. The data model will be separated from the user interface so that the same data can be used by both the console and GUI versions.

This application is designed for travelers who want a simple and organized way to plan their trips and keep track of important details in one place. I chose this project because I enjoy traveling and often need a better way to organize my travel plans. This project is interesting to me because it solves a real-life problem and allows me to build something that could be useful outside of this course.

**Main Features:**
- Create and manage trips  
- Build and manage a daily travel itinerary  
- Detect scheduling conflicts between itinerary items  
- Track travel expenses with categories  
- Show expense summaries grouped by category  
- Provide budget alerts when spending is low or exceeded  
- Maintain a travel checklist with progress tracking  

---

# User Stories

- As a user, I want to be able to add an itinerary item to my trip (date, time, duration, and location).  
- As a user, I want to be able to view the list of itinerary items for my trip.  
- As a user, I want to be warned if I try to add an itinerary item that conflicts with an existing one.  
- As a user, I want to be able to add an expense to my trip with a category.  
- As a user, I want to be able to see a summary of my total spending grouped by category.  
- As a user, I want to be alerted when I am close to my budget limit or have exceeded my budget.  
- As a user, I want to be able to add an item to my travel checklist.  
- As a user, I want to be able to view the list of items in my travel checklist.  
- As a user, I want to be able to mark a checklist item as packed.  
- As a user, I want to have the option to save the entire state of my trip to file.
- As a user, I want to have the option to load the entire state of my previously saved trip and resume exactly as it was when I last saved it.


# Instructions for End User

- You can view the panel that displays the itinerary items that have already been added to the trip by looking at the "Itinerary" section in the main application window.

- You can generate the first required action related to the user story "adding multiple itinerary items to a trip" by clicking the "+ Add Itinerary" button, entering the date, start time, end time, and location in the pop-up window, and then clicking "OK".

- You can generate the second required action related to the user story "adding multiple itinerary items to a trip" by clicking the "Remove Item" button at the bottom of the window, entering the item number you want to remove in the pop-up window, and then clicking "OK".

- You can locate my visual component by opening the application and looking at the travel-themed images displayed in the main application window, including the background image and the welcome GIF that appears when the application starts.

- You can save the state of my application by clicking the "Save" button at the bottom of the window.

- You can reload the state of my application by clicking the "Load" button at the bottom of the window.


## Phase 4: Task 2

Below is a representative sample of the events that occur when the program runs:

Sun Mar 29 10:46:30 PDT 2026  
Added itinerary item: Paris on 2000-01-01 from 12:00 to 13:00

Sun Mar 29 10:46:53 PDT 2026  
Added itinerary item: Richmond on 2000-12-12 from 13:00 to 14:00

Sun Mar 29 10:46:58 PDT 2026  
Removed itinerary item: Paris on 2000-01-01 from 12:00 to 13:00


## Phase 4: Task 3

This UML diagram shows the overall structure of my Travello application, this includes the model, persistence, and UI parts. The Trip class is the main part of the program and it contains things like the itinerary, checklist, and expense log. The diagram also shows how the GUI and console app both work with Trip and the JSON reader and writer classes.

If I had more time, one thing I would improve is the duplication between TravelloApp and TravelloGUI. Right now, both of them create and manage their own Trip, JsonReader, and JsonWriter, and some of the logic is repeated. I think it would be better to move that shared logic into a separate class so both versions can reuse it.

I would also improve TravelloGUI by splitting it into smaller parts. At the moment, it handles too many things like layout, user input, updating the screen, and startup logic. If I break it into smaller classes, this would make the code cleaner and easier to understand.