package com.tugasakhir.securityapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.SyncHttpClient;
import com.tugasakhir.securityapps.PojoData.PojoLogUser;
import com.tugasakhir.securityapps.Preferensi.GetLogUpdate;
import com.tugasakhir.securityapps.Preferensi.LogUserAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycleViewUserLog)
    RecyclerView recyclerView;
    LogUserAdapter adapter;
    SwipeRefreshLayout refreshLayout;
    int jobId = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        adapter = new LogUserAdapter(this);

        String Url_Log = BuildConfig.BASE_URL + "getData.php?api_key=" + BuildConfig.API_KEY;
        DemoAsync demoAsync = new DemoAsync();
        demoAsync.execute(Url_Log);

        refreshLayout = findViewById(R.id.swipe_refresh);
        refreshLayout.setColorSchemeResources(R.color.hitam,R.color.skyblue);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
                String Url_Log = BuildConfig.BASE_URL + "getData.php?api_key=" + BuildConfig.API_KEY;
                DemoAsync demoAsync1 = new DemoAsync();
                demoAsync1.execute(Url_Log);
            }
        });
        startJob();

    }

    private void startJob() {
        Log.d("LOG", "startJob :");
        if(cekSkejul(this)){
            Toast.makeText(this, "Sudah Terjadwal",Toast.LENGTH_LONG).show();
        }else {
            ComponentName componentName = new ComponentName(this, GetLogUpdate.class);
            JobInfo.Builder builder = new JobInfo.Builder(jobId,componentName);
            builder.setRequiresCharging(false);
            builder.setRequiresCharging(false);
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                builder.setPeriodic(15*60*1000);
            } else{
                builder.setPeriodic(3*60*1000);
            }
            JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
            jobScheduler.schedule(builder.build());
        }
    }

    private boolean cekSkejul(Context context){
        Log.d("TAG", "cekSkejul :");
        boolean isSkejul = false;

        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        if (jobScheduler != null){
            for (JobInfo jobInfo : jobScheduler.getAllPendingJobs()){
                if (jobInfo.getId() == jobId){
                    isSkejul = true;
                }
            }
        }
        return isSkejul;
    }

    private class DemoAsync extends AsyncTask<String, Void, ArrayList<PojoLogUser>> {
        @Override
        protected ArrayList<PojoLogUser> doInBackground(String... strings) {
            String uri = strings[0];
            final ArrayList<PojoLogUser> pojoLogUsers = new ArrayList<>();
            SyncHttpClient client = new SyncHttpClient();

            client.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
            client.get(uri, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try{
                        String hasil = new String(responseBody);
                        JSONObject jsonObject = new JSONObject(hasil);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0; 0 < jsonArray.length(); i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            PojoLogUser pojoLogUser = new PojoLogUser(jsonObject1);

                            pojoLogUsers.add(pojoLogUser);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.d("Tag","onFailure: " +statusCode);
                }
            });

            return pojoLogUsers;
        }
        @Override
        protected  void  onPostExecute (ArrayList<PojoLogUser> user){
            super.onPostExecute(user);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            adapter.setListUserLog(user);
            recyclerView.setAdapter(adapter);
        }
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Ingin Keluar Aplikasi?").setCancelable(false).setPositiveButton(
                "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MainActivity.this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
