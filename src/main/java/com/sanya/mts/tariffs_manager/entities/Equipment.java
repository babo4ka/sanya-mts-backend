package com.sanya.mts.tariffs_manager.entities;

import javax.persistence.*;

@Entity
@Table(name ="equipments")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
