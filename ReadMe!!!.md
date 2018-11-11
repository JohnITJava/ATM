Author: Ivanov Evgenii
Email: ivanjohnoff@gmail.com

**Final Home Work.**

**Description.**

ATM API allowing an user to login(username/password), put/take the money, select to look at the balance.
It should be possible to have different users with different balances.
It would be good to save 3 last operations for each user(exclude balance displaying).
One user must not get access to another's money and must not get the list of other users.

**Special features for Admin user:**
- get all the users
- can read 3 last operations for any user
- can read balance for each user
- cannot put/take the money.

**Features that are not implemented.**
In the process of writing tests, I realized in process of development, there are become strong dependencies in the controller classes and their entities. 
A bit of logic and checks remained in the controller classes, which, according to the idea, should be transferred to entities. 
Was used a crutch in SQLHandler class, which contains special methods of raising the connection to the test base. 
In theory, you need a file with property, from which the path to the bases will be taken into the only one method of connection.
User registration is also left for the future.

**Instructions for START**

 - App uses sqlLite db. Create atm.db in root with project, then run "start_db.sql" in resources for basic filling it.
 - Run the App from Main class.
 - For testing it's needed to create atm_test.db in root with project and run "for_test_db.sql" in resources.
 - If you have any troubles with start, please contact with me.
 - For generating testing reports use mvn test. Then open target/jacoco-ut/index.html.

**Use username:** 'admin1' and pass: 'admin1' to enter in app with Admin role.

**Use username:** 'user1' and pass: 'user1' to enter in app with User role.


**Goals.**
1. Pick up one of the options listed in the FinalHomeTask document; +
2. HomeTask project should be uploaded to VCS; +
3. HomeTask project should be covered by unit tests tests(use TestNG or JUnit, you to decide); +
4. HomeTask project should follow the standard Maven structure; +
5. The code should be well-documented; +
6. Proper project documentation should be provided; +
7. (*) Unit test coverage report should be generated upon unit tests run; +
8. (**) Provide GUI for the application (if you can); +
9. (**) Provide REST API for the application. -

Due date - 2018-11-10. Commit and create a PR of whatever you have by that day.