package com.houider.skypayhoteltest.models;

import java.sql.Date;

public class Period {

    private Date startDate;
    private Date endDate;

    public Period(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
