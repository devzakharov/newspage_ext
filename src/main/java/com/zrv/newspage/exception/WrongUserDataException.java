package com.zrv.newspage.exception;

import com.zrv.newspage.domain.User;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class WrongUserDataException extends RuntimeException {
    public String WrongUserDataException() {
        return "Ошибка";
    }
}
