package com.zrv.newspage.exception;

import com.zrv.newspage.domain.User;
import org.w3c.dom.ls.LSOutput;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

public class WrongUserDataException extends RuntimeException {
    List<String> errorsList;

    public WrongUserDataException(List<String> list) {
        this.errorsList = list;
    }

    public List<String> getErrorsList() {
        return errorsList;
    }
}
