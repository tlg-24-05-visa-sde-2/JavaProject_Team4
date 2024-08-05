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

## Usage
- For non developers who want to run the application, download the latest release.
- Extract the file anywhere you want on your machine and double-click the "run.cmd" file if you are on Windows or the "run.sh" if you are on Mac.

## Packaging
- In a command prompt or powershell, run the `.\mvnw package` command. This is going to create a production build of the React application and the jarred version of the entire project which encapsulates the React project. We are using maven plugins and including node within the jar so the user does not need to have it installed. You can simply run the java -jar 'our-jar' and it will run the gui with the spring server.

- To test the production build, use the "run.cmd/run.sh" file after running the command above in the terminal.

## Development
- For developers, once you run the package command from above, the version of the gui you get will always be whatever was present when you packaged. If you are updating the gui, you should `cd` into the gui directory and run the React project on localhost:3001 to see your concurrent changes while you develop. Once satisfied with your changes, run the package command again to update the gui in what will be the jar.
- NOTE: You do not need node or npm to develop the gui. Once the package command is run, a local copy of Node and NPM will be installed for you. This should stay ignored by Git.

## Credits
- Christopher McIntosh
- Tyler Poepping