package com.github.marvindaviddiaz.beans;

import com.github.marvindaviddiaz.service.ServicioService;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named("indexBean")
public class IndexBean implements Serializable {

    @Inject
    private ServicioService indexService;

    private String title = "Hello!";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
