package com.example.milkiminz.raddilo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpcomingFeatures extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{
    private TextView txt;
    TextView txt2;
    TextView txt3;
    String f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_features);
        txt=(TextView) findViewById(R.id.uf1);
        getSupportLoaderManager().initLoader(0,null,(LoaderManager.LoaderCallbacks<String>)this).forceLoad();
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        txt.setText(data);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {


        return new FetchData(this);



    }
    private static class FetchData extends android.support.v4.content.AsyncTaskLoader<String> {
        String f;

        public FetchData(Context context) {
            super(context);
        }

        @Override
        public String loadInBackground() {
            String load_url = getContext().getResources().getString(R.string.fetchfeatures);
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String jsonStr = null;
            String line;
            try {
                URL url = new URL(load_url);
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) return null;

                reader = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = reader.readLine()) != null) buffer.append(line);

                if (buffer.length() == 0) return null;
                jsonStr = buffer.toString();

            } catch (IOException e) {

                return null;
            } finally {
                if (urlConnection != null) urlConnection.disconnect();
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {

                    }
                }
            }


            return jsonStr;
        }

        @Override
        public void deliverResult(String data) {
            super.deliverResult(data);


        }
    }




}
