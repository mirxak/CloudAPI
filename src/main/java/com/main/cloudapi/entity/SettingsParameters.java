package com.main.cloudapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by mirxak on 10.04.15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SettingsParameters {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CoefLength{
        public Integer min_length;
        public Integer max_length;
        public Float coef;
    }

}
