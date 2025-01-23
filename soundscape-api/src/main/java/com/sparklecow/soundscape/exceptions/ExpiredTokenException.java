package com.sparklecow.soundscape.exceptions;

public class ExpiredTokenException extends Exception{
    public ExpiredTokenException(String message){
        super(message);
    }
}