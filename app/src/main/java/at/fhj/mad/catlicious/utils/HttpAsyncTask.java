package at.fhj.mad.catlicious.utils;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import at.fhj.mad.catlicious.service.AsyncCallbackInternet;


/**
 * @author Thomas Spitzer
 * @since 06.04.2017
 */

public class HttpAsyncTask extends AsyncTask<String, Void, String> {


    AsyncCallbackInternet callback;

    public void setCallback(AsyncCallbackInternet callback) {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... params) {
        StringBuilder out = new StringBuilder();
        URL url = null;
        HttpURLConnection httpURLConnection = null;
        try {
            Log.i("INTERNET", "Params: [" + params + "]");
            url = new URL(params[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            try (InputStream in = new BufferedInputStream(httpURLConnection.getInputStream())) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    out.append(line);
                }
                reader.close();
            }
            Log.i("INTERNET", out.toString());
        } catch (Exception e) {
            Log.e("INTERNET", "Error occurred at fetching from url:"  + url + "]", e);
        }
        return out.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        callback.handleResponse(s);
    }
}
