package com.zrv.newspage.exception;

public class DuplicateUserException extends Exception{

    public DuplicateUserException(String errorMessage) {
        super(errorMessage);
    }
}
