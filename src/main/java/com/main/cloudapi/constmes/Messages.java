package com.main.cloudapi.constmes;

/**
 * Created by mirxak on 23.01.15.
 */

import com.main.cloudapi.utils.JsonUtils;

import java.util.Random;

/**
 * Шаблоны response
 */
public enum Messages {
    SUCCESS_200
            (true,
                    "Successful",
                    jsMess.MessType.System,
                    200),

    FILE_ADD_412
            (false,
                    "Can`t upload file",
                    jsMess.MessType.FileStoreException,
                    412),

    FILE_GET_404
            (false,
                    "File not found",
                    jsMess.MessType.FileStoreException,
                    404),

    NOT_SUPPORTED_500
            (false,
                    "Not supported yet!",
                    jsMess.MessType.System,
                    500),

    NOT_FOUND_404
            (false,
                    "Not found",
                    jsMess.MessType.System,
                    404),

    VALIDATE_EXCEPTION
            (false,
                    "Not valid",
                    jsMess.MessType.System,
                    403);


    private final jsMess mess;

    Messages(boolean state, String mess, jsMess.MessType type, int code) {
        this.mess = new jsMess(state, mess, type, code);
    }

    Messages(boolean state, String mess, jsMess.MessType type,int status, int code) {
        this.mess = new jsMess(state, mess, type, code);
    }

    public String toString() {
        return mess.toString();
    }
    public jsMess getMess(){
        return mess;
    }


    public static class jsMess {
        public enum MessStatus {
            error,
            success
        }

        public enum MessType {
            AuthMessage,
            AuthException,
            FileStoreMessage,
            FileStoreException,
            System
        }


        // public MessStatus status;
        public boolean success = true;
        public String message;
        public MessType type;

        public int code= new Random().nextInt(1000);
        public int status;

        public jsMess() {
        }

        public jsMess(boolean state, String message, MessType type, int status) {
            success = state;
            this.message = message;
            this.status = status;
            this.type = type;
        }
        public jsMess(boolean state, String message, MessType type,int status, int code) {
            success = state;
            this.message = message;
            this.status = status;
            this.type = type;
            this.code=code;
        }

        public String toString() {
            try {
                return JsonUtils.getJson(this);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        public boolean equals(Object l) {
            if(l instanceof jsMess ){
                jsMess o = (jsMess) l;
                return this.success == o.success && this.status == o.status && this.type == o.type;
            }
            return false;
        }
    }
}