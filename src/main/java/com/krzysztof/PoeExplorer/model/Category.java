package com.krzysztof.PoeExplorer.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {
    private String id;
    private boolean current;

    public Category() {

    }

    public Category(String id, boolean current) {

        this.id = id;
        this.current = current;

    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public boolean isCurrent() {
        return current;
    }
    public void setCurrent(boolean current) {
        this.current = current;
    }
}
