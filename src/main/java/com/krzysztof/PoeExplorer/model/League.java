package com.krzysztof.PoeExplorer.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class League {
    private String id;
    private String name;
    private String realm;
    private String description;
    private Category category;

    public League() {

    }

    public League(String name, Category category) {

        this.name = name;
        this.category = category;

    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "League{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", realm='" + realm + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
