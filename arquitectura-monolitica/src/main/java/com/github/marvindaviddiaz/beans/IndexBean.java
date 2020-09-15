package com.github.marvindaviddiaz.beans;

import com.github.marvindaviddiaz.bo.Servicio;
import com.github.marvindaviddiaz.service.IndexService;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@Named("indexBean")
public class IndexBean implements Serializable {

    @Inject
    private IndexService indexService;

    private String title = "Hello!";

    public String getTitle() {
        obtenerServicios();
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private void obtenerServicios() {
        List<Servicio> all = indexService.getAll();
        all.forEach(System.out::println);
    }
}
