package com.tugasakhir.securityapps.Preferensi;

import android.app.Notification;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tugasakhir.securityapps.BuildConfig;
import com.tugasakhir.securityapps.Notification.Notifications;
import com.tugasakhir.securityapps.PojoData.PojoLogUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GetLogUpdate extends JobService {

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d("LOG", "onStartJob: ");
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d("LOG", "onStopJob: ");
        return true;
    }

    public void getLogUpdate (JobParameters job){
        Date date = Calendar.getInstance().getTime();
        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = formater.format(date);

        String URL = BuildConfig.BASE_URL + "getData.php?api_key=" + BuildConfig.API_KEY + todayDate;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("result");

                    for (int i = 0; i < array.length(); i++){
                        JSONObject object1 = array.getJSONObject(i);
                        PojoLogUser logUser = new PojoLogUser(object1);

                        if (logUser.getWaktu().equals(todayDate)){
                            Notifications notifications1 = new Notifications();
                            notifications1.sendNotif(getApplicationContext(), logUser.getUser(),"now",logUser.getStatus(), i);


                        }


                        jobFinished(job, false);

                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                jobFinished(job, true);
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
}
