package com.sanya.mts.tariffs_manager.entities;

import javax.persistence.Id;
import java.sql.Date;

public class DailyTracker {

    @Id
    private Date day;

    private int visits;
}
