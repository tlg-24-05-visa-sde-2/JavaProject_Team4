# Go Take A Hike

## What is this?
- This is a spring boot application that allows users to interface with a simple gui built in react, in which users can:
  - login
  - create an account
  - search hiking trails
  - save favorite hiking trails
  - view saved hiking trails
  
## Installation
- Unzip the file in your desired location and run the "run.cmd/run.sh" file located in the directory

## Packaging
- In a command prompt or powershell, run the `.\mvnw package` command. This is going to create a production build of the React application and the jarred version of the entire project which encapsulates the React project. We are using maven plugins and including node within the jar so the user does not need to have it installed. You can simply run the java -jar 'our-jar' and it will run the gui with the spring server.

- To test the production build, use the "run.cmd/run.sh" file after running the command above in the terminal.

## Credits
- Christopher McIntosh
- Tyler Poepping