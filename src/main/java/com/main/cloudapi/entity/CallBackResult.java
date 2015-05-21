package com.main.cloudapi.entity;

/**
 * Created by mirxak on 20.05.15.
 */
public class CallBackResult {

    //<editor-fold desc="Статус ответа от сервера">
    private int status;
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    //</editor-fold>

    //<editor-fold desc="Тело ответа от сервера">
    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    //</editor-fold>

}
