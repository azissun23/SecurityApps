package com.tugasakhir.securityapps.PojoData;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class PojoUserLogin implements Parcelable {

    String LoginUser;
    String LoginPass;
    String LoginId;

    protected PojoUserLogin(Parcel in) {
        LoginUser = in.readString();
        LoginPass = in.readString();
        LoginId = in.readString();
    }

    public static final Creator<PojoUserLogin> CREATOR = new Creator<PojoUserLogin>() {
        @Override
        public PojoUserLogin createFromParcel(Parcel in) {
            return new PojoUserLogin(in);
        }

        @Override
        public PojoUserLogin[] newArray(int size) {
            return new PojoUserLogin[size];
        }
    };

    public String getLoginUser() {
        return LoginUser;
    }

    public void setLoginUser(String loginUser) {
        LoginUser = loginUser;
    }

    public String getLoginPass() {
        return LoginPass;
    }

    public void setLoginPass(String loginPass) {
        LoginPass = loginPass;
    }

    public String getLoginId() {
        return LoginId;
    }

    public void setLoginId(String loginId) {
        LoginId = loginId;
    }

    public PojoUserLogin (JSONObject object){
        try {
            String id = object.getString("id");
            String user = object.getString("user");
            String password = object.getString("password");

            this.LoginId = id;
            this.LoginUser = user;
            this.LoginPass = password;

        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(LoginUser);
        dest.writeString(LoginPass);
        dest.writeString(LoginId);
    }
}
