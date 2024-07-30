package com.gotakeahike.takeahike.Exceptions;

public class JobNotFoundException extends RuntimeException{
    public JobNotFoundException(String message) {
        super(message);
    }
}
