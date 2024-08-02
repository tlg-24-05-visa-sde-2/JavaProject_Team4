This is the DTO(Data Transfer Object, formerly known as value objects) package you are inside. We create DTO's to simplify and move commonly needed data across our application.

We do this for many of reasons. In the next few bullets I will go over a couple examples. If you would like to understand more about them and why they are useful or how to use them this "https://stackoverflow.com/questions/1051182/what-is-a-data-transfer-object-dto" stack overflow conversation has tons of reasons(good and bad) on why they are used.

For all Annotations used in the examples, reference the AnnotationsCheatSheet

- Scenario 1
  - In our models package, we created a class called "User". The user has a password, it could have other sensitive information like where the user lives, credit card numbers, etc... So, the "client", or as most of you know it would be the JavaScript/HTML you did with Nelly, want's some basic data about the user to display on the users profile page. So we would take "User.java" and make a "UserDTO.java".  
    - Reference this
    ```
    @Entity // Database entity
    @Table // Database table
    public class User {
      priavte Long id;
      private String username;
      private String password;
      private String firstName;
      private Boolean accountVerified;
      private int creditCardNumber;
      }
    ```
      Within this class, there is clearly some data we do not want to willingly give to the client over a http request. So we would do something like this to fill in the data without passing all the information 
    ```
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class UserDTO { 
        private Long id;            // We can give the ID
        private String username;    // The client probably wants to display this somewhere
        private Boolean accountVerified; // The client might need this to show components
     }
    ```
    Now, here is how would give back this data to the client/frontend/requester
    ```
    public UserDTO getUser() {
        User user = myDataBase.getUser();
    
        // Remember, we have an AllArgsConstructor
        // The userDTO has 3 attributes [id, username, accountVerified]
        UserDTO simpleUserData = new UserDTO(user.getId(), user.getUsername(), user.isAccountVerified());
        return simpleUserData;
    }
    ```
    
  - Now, when the client ask's for the users information, we can return them data about the user, that does not expose sensitive information. If the client needs sensitive information, those routes need to be very secure. We would use other means and security to validate the person making the request, encrypt data, etc.. Essentially, it's a simple down and dirty way to transfer commonly requested information from the client that does not send them our entire database object.

- Scenario 2
    - Let's say we are making a third party API request (think of Unsplash from Nelly's course) being called back here in the Java. 
    - How would we store and move the data? Answer: we make a DTO that mimics the data we are receiving. In this scenario, we don't even have a database object that mimics our API data. We are just making an API call on behalf of the client/frontend. 
    - So our solution, we fetch the data, pump it into the DTO, and send that DTO back to the client. 
    - Fun Fact: Java, Spring, and the web will automatically convert your object into JSON format for you. If you wanted to serialize and do it yourself, you absolutely can! You can also add custom formatting and attribute names if you'd like, but we won't dive into that right now.
    - Here is an example:
   ```
   public ImageDTO getImageFromUnsplashAPI() {
    String url = "https://www.unsplash.com/get/image/Grand-Canyon"
    
    // For how to build a full request, reference the "getDataFromApi" method in the TrailService java class, located in the "services" package.
    HttpRequest request = new HttpRequest.newBuilder().uri(url) 
    // Send the request.
    HttpResposne<String> resposne = HttpClient.newHttpClient().send(request);
  
    // Use the ObjectMapper to put this into your DTO
    ObjectMapper mapper = new ObjectMapper()
      
    // Here we are taking the API data and mapping it into our ImageDTO Java class. 
    // Obviously, your "ImageDTO" needs to reflect the data coming in from the request. Postman is a great tool for testing and viewing the data before creating the class
    mapper.readValue(response.body(), mapper.getTypeFactory().constructMapType(
      Map.class, String.class, ImageDTO.class 
    ));
   }
   ```