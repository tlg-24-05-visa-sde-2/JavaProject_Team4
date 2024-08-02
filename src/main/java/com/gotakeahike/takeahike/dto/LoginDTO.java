package com.gotakeahike.takeahike.dto;

/*
 * This is a simple DTO to facilitate the process of logging a user into the app.
 *
 * We use this because we don't need the entire User model class or to even touch that class.
 *
 * This class will be a record and immutable once the data is received
 */
public record LoginDTO(String username, String password) { }