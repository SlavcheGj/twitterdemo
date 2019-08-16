package com.endava.twitterdemo.Exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class UserDoesNotExistException extends Exception {

    @JsonProperty("errorMessage")
    private String errorMessage;

    public UserDoesNotExistException() {
        super();
    }

    public UserDoesNotExistException(String errorMessage) {
        super();
        this.errorMessage = errorMessage;
    }

    @JsonProperty("errorMessage")
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDoesNotExistException that = (UserDoesNotExistException) o;
        return errorMessage.equals(that.errorMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorMessage);
    }

    @Override
    public String toString() {
        return "UserDoesNotExistException{" +
                "errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
