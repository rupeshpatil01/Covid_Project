package com.rupesh.coronameter;

import java.util.ArrayList;



public class Data extends ArrayList<Data> {
    String name;
    String total;
    String deaths;
    String recover;

    public Data() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getRecover() {
        return recover;
    }

    public void setRecover(String recover) {
        this.recover = recover;
    }
}
