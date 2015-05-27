package com.main.cloudapi.utils;

import com.main.cloudapi.entity.User;
import org.apache.commons.lang3.StringUtils;

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

    public static User getUser(){
        if (getData().getUser() == null){
            return new User();
        }
        return getData().getUser();
    }

    public static void setUser(User user){
        getData().setUser(user);
    }

    public static class Data{
        private Integer milage_partition;
        private User user;
        private String activationUrl;

        public Integer getMilage_partition() {
            String milpart = MainConfig.get("milage.partition");
            if (StringUtils.isBlank(milpart))return 15000;
            return Integer.valueOf(MainConfig.get("milage.partition"));
        }

        public void setMilage_partition(Integer milage_partition) {
            this.milage_partition = milage_partition;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public String getActivationUrl() {
            return MainConfig.get("activation.base.url");
        }

        public void setActivationUrl(String activationUrl) {
            this.activationUrl = activationUrl;
        }
    }
}

