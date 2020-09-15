package com.github.marvindaviddiaz.beans;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named("indexBean")
public class IndexBean implements Serializable {

    private String title = "Hello!";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
