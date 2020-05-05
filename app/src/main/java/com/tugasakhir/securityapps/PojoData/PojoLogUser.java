package com.tugasakhir.securityapps.PojoData;

import org.json.JSONObject;

public class PojoLogUser {

    private String id = "id";
    private String User = "User";
    private String Status = "Status";
    private String Waktu = "Waktu";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getWaktu() {
        return Waktu;
    }

    public void setWaktu(String waktu) {
        Waktu = waktu;
    }

    public PojoLogUser(JSONObject object){
        try {

            String id = object.getString("id");
            String User = object.getString("User");
            String Status = object.getString("Status");
            String Waktu = object.getString("Waktu");


            this.id = id;
            this.User = User;
            this.Status = Status;
            this.Waktu = Waktu;

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

