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

    }
}

