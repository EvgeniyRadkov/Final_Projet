package com.gmail.vanyasudnishnikov.application.repository.model;

public enum StatusEnum {
    NEW("New"),
    IN_PROGRESS("In progress"),
    DONE("Done"),
    REJECTED("Rejected");
    private String line;

    StatusEnum(String line) {
        this.line = line;
    }

    public String getLine() {
        return line;
    }
}
