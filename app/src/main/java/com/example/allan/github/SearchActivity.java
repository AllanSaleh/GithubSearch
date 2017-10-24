package com.example.allan.github;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    EditText searchQuery;
    ImageButton searchButton;
    TextView resultsNumber;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchQuery = (EditText) findViewById(R.id.searchQuery);
        searchButton = (ImageButton) findViewById(R.id.searchButton);
        resultsNumber = (TextView) findViewById(R.id.resultsNumber);
        list = (ListView) findViewById(R.id.list);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchAsyncTask task = new SearchAsyncTask();
                task.execute(searchQuery.getText().toString());
            }
        });
    }

    class SearchAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            Log.v("RUNNING", "IN BACKGROUND");

            StringBuilder JsonData = new StringBuilder();
            HttpURLConnection httpURLConnection = null;
            InputStream inputStream = null;

            try {

                URL url = new URL("https://api.github.com/search/repositories?q=" + strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setReadTimeout(15000);
                httpURLConnection.connect();

                inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    JsonData.append(line);
                    line = reader.readLine();
                }
                Log.v("AsyncTask", "Connected" + httpURLConnection.getResponseCode());
            } catch (Exception e) {
                e.printStackTrace();
                Log.v("AsyncTask", e.getMessage());
            } finally {
                if (httpURLConnection != null)
                    httpURLConnection.disconnect();
                if (inputStream != null)
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            }

            Log.v("TIME", String.valueOf(System.currentTimeMillis()));

            return JsonData.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null && !s.isEmpty())
                updateUI(s);
            else
                Toast.makeText(SearchActivity.this, "Failed to connect.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI(String s) {
        try {

            JSONObject root = new JSONObject(s);

            int total_count = root.getInt("total_count");

            JSONArray items = root.getJSONArray("items");

            final ArrayList<SearchResult> searchArrayList = new ArrayList<SearchResult>();

            for (int i = 0; i < items.length(); i++) {

                JSONObject currentItem = items.getJSONObject(i);
                String name = currentItem.getString("name");
                //int itemsId = currentItem.getInt("id");
                JSONObject currentOwner = currentItem.getJSONObject("owner");
                String login = currentOwner.getString("login");
                //int ownersId = currentOwner.getInt("id");
                String avatarURL = currentOwner.getString("avatar_url");

                SearchResult searchResult = new SearchResult(name, login, avatarURL);

                searchArrayList.add(searchResult);
            }
            resultsNumber.setText(total_count+" results");
            SearchAdapter adapter = new SearchAdapter(this, R.layout.item_search_layout, searchArrayList);

            list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
