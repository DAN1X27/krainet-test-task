package com.example.auth_service.util;

import lombok.SneakyThrows;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Iterator;

public final class RequestErrorsHandler {

    private RequestErrorsHandler() {
    }

    @SneakyThrows
    public static void handleErrors(BindingResult bindingResult, Class<? extends Exception> ex) {
        if (bindingResult.hasErrors()) {
            Iterator<FieldError> iterator = bindingResult.getFieldErrors().iterator();
            StringBuilder sb = new StringBuilder();
            while (iterator.hasNext()) {
                FieldError fieldError = iterator.next();
                sb.append(fieldError.getDefaultMessage());
                if (iterator.hasNext()) {
                    sb.append("; ");
                }
            }
            throw ex.getDeclaredConstructor(String.class).newInstance(sb.toString());
        }
    }

}
