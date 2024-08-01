package com.gotakeahike.takeahike.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data // Lombok @Data annotation generates the Getters and Setters
@NoArgsConstructor
@AllArgsConstructor
public class TrailAPIDTO {
    private String name;
    private String city;
    private String state;
    private String country;
    private String description;
    private String directions;
    private String lat;
    private String lon;

    @JsonProperty("parent_id")
    private String parent_Id;

    @JsonProperty("place_id")
    private String place_Id;

    private Map<String, Activity> activities = new HashMap<>();

    @JsonProperty("activities")
    public void setActivities(Map<String, Activity> activities) {
        this.activities = activities;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
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

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Attribs {
            private String length;
        }
    }
}