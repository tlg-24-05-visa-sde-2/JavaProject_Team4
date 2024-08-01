package com.gotakeahike.takeahike.Exceptions;

public class TrailNotFoundException extends RuntimeException {
    public TrailNotFoundException(String message) {
        super(message);
    }
}