package com.wadimkazak.weather;

import com.orm.SugarRecord;

public class NamesOfCites extends SugarRecord {

    String name;

    public NamesOfCites() {
    }

    public NamesOfCites(String name) {
        this.name = name;
    }
}
