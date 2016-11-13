package com.allergyiap.entities;

public class CatalogEntity extends Entity {

    public int id;
    public String title;
    public String description;
    public String url_image;

    public CatalogEntity(){
        this(0, "", "", "");
    }

    public CatalogEntity(int id, String title, String description){

        this(id, title, description, "");
    }

    public CatalogEntity(int id, String title, String description, String url_image){

        this.id = id;
        this.title = title;
        this.description = description;
        this.url_image = url_image;
    }
}
