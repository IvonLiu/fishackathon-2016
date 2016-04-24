package com.example.android.camera2basic;

import android.os.AsyncTask;
import android.util.Log;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by Owner on 4/23/2016.
 */
public class FilestackTask extends AsyncTask<File, Void, String> {

    public interface OnFilestackTaskCompleteListener {
        void onFilestackTaskComplete(String url);
    }

    private OnFilestackTaskCompleteListener listener;
    private String url = "https://www.filestackapi.com/api/store/S3?key=" + Config.FILESTACK_API_KEY;

    public FilestackTask(OnFilestackTaskCompleteListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(File... params) {
        HttpResponse<String> response = null;
        String s = "";
        try {
            response = Unirest.post(url)
                    .field("fileUpload", params[0])
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        if (response != null) {
            s = response.getBody();
        }
        return s;
    }

    @Override
    protected void onPostExecute(String response) {
        String url = "";
        try {
            JSONObject json = new JSONObject(response);
            url = json.getString("url");
            Log.i("TAG", url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (listener != null) {
            listener.onFilestackTaskComplete(url);
        }
    }
}
