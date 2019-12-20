package com.irfan.iqroku.model;

public class MLogin {

    private boolean status;
    private String message;
    private DataMhs data_mhs;


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataMhs getData_mhs() {
        return data_mhs;
    }


    public class DataMhs {
        private String id;
        private String nama;
        private String password;

        public String getId() {
            return id;
        }

        public String getNama() {
            return nama;
        }

        public String getPassword() {
            return password;
        }
    }
}
