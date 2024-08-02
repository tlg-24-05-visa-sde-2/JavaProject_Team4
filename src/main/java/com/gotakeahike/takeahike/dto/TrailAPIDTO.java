package com.gotakeahike.takeahike.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashMap;
import java.util.Map;


/**
 * TrailAPIDTO represents a Data Transfer Object (DTO) for interacting with trail data
 * from an external API. This DTO is used to encapsulate the details of a trail
 * obtained from an external source.
 * The DTO is utilized to structure and transfer trail data between the API service
 * and the application.
 */
@Data // Lombok @Data annotation generates the Getters and Setters
@NoArgsConstructor
@AllArgsConstructor
public class TrailAPIDTO {
    private String name;               // Name of the trail
    private String city;               // City where the trail is located
    private String state;              // State where the trail is located
    private String country;            // Country where the trail is located
    private String description;        // Description of the trail
    private String directions;         // Directions to the trail
    private String lat;                // Latitude of the trail
    private String lon;                // Longitude of the trail

    @JsonProperty("parent_id") // Maps the JSON property "parent_id" to the Java field "parent_Id"
    private String parent_Id;  // ID of the parent location

    @JsonProperty("place_id") // Maps the JSON property "place_id" to the Java field "place_Id"
    private String place_Id;  // ID of the place

    private Map<String, Activity> activities = new HashMap<>(); // Map of activities associated with the trail

    @JsonProperty("activities") // Specifies the JSON property name for the activities field
    public void setActivities(Map<String, Activity> activities) {
        this.activities = activities;
    }

    /**
     * Activity represents an activity associated with the trail, including details such as URL, length,
     * description, name, and more. This class is used to encapsulate information about various activities
     * related to the trail. All of which is being displayed when the user calls upon the API.
     */

    @Data // Lombok @Data annotation generates getters and setters, toString(), equals(), and hashCode() methods
    @NoArgsConstructor // Lombok annotation to generate a no-argument constructor
    @AllArgsConstructor // Lombok annotation to generate a constructor with all arguments
    public static class Activity {
        private String url;
        private String length;
        private String description;
        private String name;
        private String rank;
        private String rating;
        private String thumbnail;

        @JsonProperty("activity_type")
        private String activityType;

        @JsonProperty("activity_type_name")
        private String activityTypeName;

        private Attribs attribs;

        @JsonProperty("place_activity_id")
        private String placeActivityId;

        /**
         * Attribs represents specific attributes of an activity, such as length.
         * Which we will eventually use when saving the API data in our saveTrailtoUser function
         */
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Attribs {
            private String length; //Length of the trail which was represented in miles
        }
    }
}