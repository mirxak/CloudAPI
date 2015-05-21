package com.main.cloudapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by mirxak on 22.05.15.
 */
public class KASKO {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PkaskoBrand{
        public String name;
        public List<PkaskoCar> models;

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PkaskoCar{
        public String name;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ApiKey{
        public String api_key;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class KaskoResult{
        public String name;
        public Float price;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PkaskoCalcRequest{
        public String make;
        public String model;
        public String year;
        public String power;
        public Long price;
        public List<PkaskoDriver> drivers;
        public PkaskoExtended extended;
    }

    public static class PkaskoDriver{
        public String sex;
        public String age;
        public String experience;
        public boolean marriage;
    }

    public static class PkaskoExtended{
        public String carNew;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PkaskoCalcResponse{
        public String id;

    }
}
