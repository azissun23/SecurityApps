package com.tugasakhir.securityapps.Preferensi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tugasakhir.securityapps.PojoData.PojoLogUser;
import com.tugasakhir.securityapps.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogUserAdapter extends RecyclerView.Adapter<LogUserAdapter.ViewHolder> {

    ArrayList<PojoLogUser> listUserLog;
    Context context;

    public LogUserAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<PojoLogUser> getListUserLog() {
        return listUserLog;
    }

    public void setListUserLog(ArrayList<PojoLogUser> listUserLog) {
        this.listUserLog = listUserLog;
    }

    @NonNull
    @Override
    public LogUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.loguser_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogUserAdapter.ViewHolder viewHolder, int i) {
        viewHolder.User.setText(getListUserLog().get(i).getUser());
        viewHolder.Waktu.setText(getListUserLog().get(i).getWaktu());
        viewHolder.Informasi.setText(getListUserLog().get(i).getStatus());
    }

    @Override
    public int getItemCount() {
        return getListUserLog().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtUser)
        TextView User;
        @BindView(R.id.txtwaktu)
        TextView Waktu;
        @BindView(R.id.txtinformation)
        TextView Informasi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
