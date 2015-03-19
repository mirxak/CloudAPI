package com.main.cloudapi.utils;

/**
 * Created by mirxak on 23.01.15.
 */
public class ContextHolder {

    private static ContextHolder staticContextHolder;
    private static Data data;

    public ContextHolder(){
        if (staticContextHolder == null){
            staticContextHolder = this;
        }
    }

    public static Data getData() {
        if (data == null) {
            data = new Data();
        }
        return data;
    }

    public static class Data{
        private Integer milage_partition;

        public Integer getMilage_partition() {
            return Integer.valueOf(MainConfig.get("milage.partition"));
        }

        public void setMilage_partition(Integer milage_partition) {
            this.milage_partition = milage_partition;
        }
    }
}

