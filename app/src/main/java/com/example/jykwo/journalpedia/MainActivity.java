package com.example.jykwo.journalpedia;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String JSON_DATA = "JSON_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String apiKey = "PykSKLeJz7ZldYQbNWoTDvwCfnarFXOg";
        String searchTerm = "cells";
        String dataUrl = "https://core.ac.uk/api-v2/articles/search/"+ searchTerm +
                "?page=1&pageSize=20&metadata=true&fulltext=false&citations=false&similar=false&duplicate=false&urls=true&faithfulMetadata=false&apiKey="
                + apiKey;

        if(isNetworkAvailable()) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(dataUrl)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                        } else {
                            alertUserAboutError();

                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }


                }
            });
        }
        else{
            Toast.makeText(this, R.string.network_unavailable_message, Toast.LENGTH_LONG).show();
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvaliable = false;
        if(networkInfo != null && networkInfo.isConnected()) {
            isAvaliable = true;
        }

        return isAvaliable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");

    }

    private JournalData[] getdata(String jsonData) throws JSONException{
        JSONObject journal = new JSONObject(jsonData);
        JSONArray data = journal.getJSONArray("data");

        JournalData[] journalDatas = new JournalData[data.length()];

        for (int i = 0; i<data.length(); i++){
            JSONObject jsonJournal = data.getJSONObject(i);
            JournalData journaldata = new JournalData();

            journaldata.setMtitle(jsonJournal.getString("title"));
            journaldata.setMyear(jsonJournal.getString("year"));
            journaldata.setMpublisher(jsonJournal.getString("publisher"));
            journaldata.setMdescription(jsonJournal.getString("description"));
            journaldata.setMdownloadUrl(jsonJournal.getString("downloadUrl"));

            journalDatas[i] = journaldata;
    }
            return journalDatas;
    }
}
