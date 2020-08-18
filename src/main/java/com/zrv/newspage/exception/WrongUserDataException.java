package com.zrv.newspage.exception;

import java.util.List;

public class WrongUserDataException extends Exception {
    List<String> errorsList;

    public WrongUserDataException(List<String> list) {
        this.errorsList = list;
    }

    public List<String> getErrorsList() {
        return errorsList;
    }
}
