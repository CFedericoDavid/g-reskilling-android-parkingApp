# g-reskilling-android
Repo for Android reskilling process

Create a NEW git repository in order to share the exercise of this onboarding.
- For each relevant functionality, you must create a new branch(feature branch) from the updated master branch. And add unitary tests that support the different functionalities of the calculator.
- Once you have finished your task, you should make a commit and a Pull Request (PR) for that task. The PR will pass through a revision process in charge of some mentors before being merged.
- First, follow the instructions to start your repository and add the .gitignore file to avoid tracking unwanted files. You can download it from here.
Using the counter as an example implement a parking system. 
- For each of the functionalities of the parking, the system adds a PR.

1. Project initialization
2. Configure parking size. The user must set the number of parking available.
3. Make a parking reservation. The user should choose a start and end date (DatePicker) and time (TimePicker), parking lot, and provide a security code (EditText). The reservation shouldn’t overlap with another existing one.
4. Release parking. The user should enter a parking number and a security code and if the data match the place is released.
5. Run an automatic release system. Scan all the current reservations and erase those that the end date-time is in the past.

- The MVP structure has to be implemented with the contract for each class.
- All the errors must be visible to the user on the screen using an AlertDialog.
- Implement the following edge cases:
  - On the reservation, the start and end date-time should be future.
  - Before the reservation flow, run the automatic release system.
  - On the configuration, the parking size must be greater than zero.
- Add all the UNIT tests for each functionality. Using MockK. 
