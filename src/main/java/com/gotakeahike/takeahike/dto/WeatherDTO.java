package com.gotakeahike.takeahike.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherDTO {
    @JsonProperty("weather")
    private Weather[] weather;

    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    public static class Weather {
        @JsonProperty("id")
        private int id;
        @JsonProperty("description")
        private String description;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
        // Getters and setters
    }
}
