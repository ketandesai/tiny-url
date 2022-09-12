package com.tinyurl.app.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ValidationError {

    private List<String> errors = new ArrayList<>();
    private String errorMessage;

    public boolean hasErrors(){
        return !errors.isEmpty();
    }
}
