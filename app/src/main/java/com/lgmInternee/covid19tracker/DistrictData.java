package com.lgmInternee.covid19tracker;

public class DistrictData {
    private final String district;
    private final int confirmed;
    private final int active;
    private final int deceased;
    private final int recovered;

    public DistrictData(String district, int confirmed, int active, int deceased, int recovered) {
        this.district = district;
        this.confirmed = confirmed;
        this.active = active;
        this.deceased = deceased;
        this.recovered = recovered;
    }

    public String getDistrict() {
        return district;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public int getActive() {
        return active;
    }

    public int getDeceased() {
        return deceased;
    }

    public int getRecovered() {
        return recovered;
    }
}

