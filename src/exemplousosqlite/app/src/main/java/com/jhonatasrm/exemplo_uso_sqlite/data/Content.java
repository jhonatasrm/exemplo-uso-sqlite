package com.jhonatasrm.exemplo_uso_sqlite.data;

import java.io.Serializable;

public class Content implements Serializable {
    private int id;
    private String title;
    private String description;

    public Content(int id, String nome, String description) {
        this.id = id;
        this.title = nome;
        this.description = description;
    }

    public Content(String nome, String description) {
        this.title = nome;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return title;
    }

    public void setNome(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
