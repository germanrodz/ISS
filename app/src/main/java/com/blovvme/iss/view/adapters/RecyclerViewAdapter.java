package com.blovvme.iss.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blovvme.iss.R;
import com.google.android.gms.common.api.Response;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Blovvme on 1/9/18.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    List<com.blovvme.iss.model.Response> mResponseList;
    Context mContext;

    public RecyclerViewAdapter(List<com.blovvme.iss.model.Response> mResponseList) {
        this.mResponseList = mResponseList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View mView = LayoutInflater.from(mContext).inflate(R.layout.pass_item_list, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        com.blovvme.iss.model.Response mResponse = mResponseList.get(position);
        holder.txtDuration.setText("Duration: "+mResponse.getDuration() + " sec");
        holder.txtRiseTime.setText("Rise Time: "+getDateCurrentTimeZone(mResponse.getRisetime()));
    }

    @Override
    public int getItemCount() {
        return mResponseList.size();
    }
    public  String getDateCurrentTimeZone(long timestamp) {
        try{
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currentTimeZone = calendar.getTime();
            return sdf.format(currentTimeZone);
        }catch (Exception e) {
        }
        return "";
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtDuration)
        TextView txtDuration;
        @BindView(R.id.txtRiseTime)
        TextView txtRiseTime;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}