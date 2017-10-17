package com.example.jykwo.journalpedia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by jykwo on 10/15/2017.
 */

public class CustomAdapter extends BaseAdapter {

    private Context mContext;
    private JournalData[] mJournalDatas;

    public CustomAdapter(Context context, JournalData[] journalDatas) {
        mContext = context;
        mJournalDatas = journalDatas;
    }

    @Override
    public int getCount() {
        return mJournalDatas.length;
    }

    @Override
    public Object getItem(int position) {
        return mJournalDatas[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView ==null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_main, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.year = (TextView) convertView.findViewById(R.id.year);
            holder.publisher = (TextView) convertView.findViewById(R.id.publisher);
            holder.description = (TextView) convertView.findViewById(R.id.description);
            holder.downloadUrl = (TextView) convertView.findViewById(R.id.downloadUrl);

            convertView.setTag(holder);

        }
        else{
            holder = (ViewHolder)  convertView.getTag();
        }

        JournalData journalData = mJournalDatas[position];

        holder.title.setText(journalData.getMtitle());
        holder.year.setText(journalData.getMyear());
        holder.publisher.setText(journalData.getMpublisher());
        holder.description.setText(journalData.getMdescription());
        holder.downloadUrl.setText(journalData.getMdownloadUrl());


        return null;
    }

    private static class ViewHolder {
        TextView title;
        TextView year;
        TextView publisher;
        TextView description;
        TextView downloadUrl;

    }
}
