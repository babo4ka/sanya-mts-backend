package com.sanya.mts.tariffs_manager.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TrafficTracker {

    @Id
    private Integer id;

    private int totalVisits;
    private int todayVisits;
}
