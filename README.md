# Simple Todo Application Demo:
This is an andorid application for maintaing a to-do list. The application can be used for adding items and its priority to the list, displaying items in a list and editing or deleting an item from the list. I have now updated the functionality to add items to an SQLiteDatabase using SQLiteOpenHelper class. I have used a custom adapter for improving the style of the list items. 

Completed User Stories:
* Required: User can view the list of todos.
* Required: User can add new items and its priority to the list. The added item and priority displays in the list.
* Required: User can click on a particular item and edit the item or its priority. The updated item displays in the list.
* Required: User can long click on a particular item in a list and delete the item from the list.
* Persist the todo items into SQLite using SQLiteOpenHelper.
* Added the option of adding priority for each item (using DatePicker) and added to the list.
* Style of the todo items list improved by using custom adapter.
* Used DialogFragment instead of activity for adding/editing items.
* Added the option of adding completion due dates for each item (using DatePicker) and added to the list.
* Optional: Added an image for adding new items (using ImageButton) instead of a button
* Optional: A scheduled notification to the user with the list of items based on the due date
  - Notification will be generated on running the code from Android Studio 
  - Then the notification is scheduled to trigger every 24 hours (using AlarmManager) even though the app is not in use
  - Notification will not be trigerred when there are no action items for that day

Walkthrough of all the user stories:
 
<IMG src="Simple Todo Walkthrough.gif"/>
