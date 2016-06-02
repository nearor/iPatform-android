package com.nearor.common.route;


import com.nearor.framwork.ValueObject;

/**
 * Created by Nearor on 6/1/16.
 */
public class Module extends ValueObject {

    private String name;
    private String path;

    private String descripation;

    public Module(String name, String path, String descripation) {
        this.name = name;
        this.path = path;
        this.descripation = descripation;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getDescripation() {
        return descripation;
    }


}
