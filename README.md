# DBSChallenge
The challenge is to develop a system to help the team decide where to have lunch by running an election every morning.

- Types of users: admin and users
  - Admin is responsible for adding and updating the restaurant information.
  - Admin can not vote.
  - User can vote once a day.
- The voting system is available for users voting from 00:00 to 11:30.
- After 11:30, users are redirected to the election result page.
- Once a restaurant wins the election, it can not run the election again in the same week of its victory.

# Technologies
 - Java 17
 - Spring Boot 2.6.3
 - Maven Project
 - Spring Boot DevTools
 - Thymleaf
 - Spring Web
 
# Running Project
First, download the project and extract it. So, you can open the project folder in VSCode.
Once opened, run the application and acess http://localhost:8080/ and login as admin, so you can manage the restaurants, adding new ones, updating their information or removing them from the candidate's list.

After adding restaurants as admin, you should logout and login using a regular user credetial in order to vote.

You can set a different *DEFAULT_EXPIRED_TIME_VOTING* in **br.com.lunchbreak.lunchbreak.utilValidationUtil.java**.

# Credentionals
 - Role: username, password
  - Administrator: "admin", "password"
  - First User: "user1", "password"
  - Second User: "user2", "password"

## Access
 - Every user (authenticated or not) has access to "/" and '/index"
 - Only authenticated "ADMIN" users have access under "/admin"
 - Only authenticated "USER" users have access under "/election"

# Scheduled Tasks
 - The timezone application is set to GMT-3.
 - The application server runs these taks, however, they could be implemented as database functions/procedures.
 - Daily task fires every day at 11:30 AM.
  - Count votes and elect the winner.
 - Weekly task fires every Sunday at 11:50 AM.
  - Reset the winner restaurants of the week
 - Monthly task fires at 00:00 on every first day of the month.
  - Reset the history of the votes, avoiding memory issues.

# Optmizations
 - I assumed this is a non-critical application since it is intended to be used by a small team. This monolithic application doesn't require a database, but its architecture is ready to support a database, since it is important for scaling the application and also allows auditing the election system more efficiently. The database also allows the use of views and cron tasks.
 - Configuration attribute values should be set on a propriety file.
 - Handle better when we get a tie.
 - Handle exceptions properly and Customize error pages.
 - Include acceptance, integration, and unity tests.
 - Users database integration.
