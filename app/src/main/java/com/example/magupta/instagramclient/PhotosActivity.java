package com.example.magupta.instagramclient;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PhotosActivity extends AppCompatActivity {
    public ArrayList<InstagramPhoto> photos;
    private static final String CLIENT_ID = "5c43cb0e2cda4147b5b03375e963fee9";
    private static final String ENDPOINT = "https://api.instagram.com/v1/media/popular";
    private ArrayAdapter<InstagramPhoto> instagramPhotoAdapter;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        photos = new ArrayList<InstagramPhoto>();
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchPopularPhotos();
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // Create adaptor to list
        instagramPhotoAdapter = new InstagramPhotoAdapter(PhotosActivity.this, photos);

        // Get List View
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);

        // Set Adaptor to List View
        lvPhotos.setAdapter(instagramPhotoAdapter);

        // Fetch Instagram Photos
        fetchPopularPhotos();
    }


    public void fetchPopularPhotos(){
        // 1. Url to call
        String url = ENDPOINT + "?client_id=" + CLIENT_ID;

        // 2. Create Network Client
        AsyncHttpClient client = new AsyncHttpClient();

        // 3. Trigger Get Request
        client.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
                Log.i("DEBUG", response.toString());
                JSONArray photosJSON = null;
                instagramPhotoAdapter.clear();
                try {
                    photosJSON = response.getJSONArray("data");

                    if (photosJSON != null) {
                        for (int i = 0; i < photosJSON.length(); i++) {
                            JSONObject photoJSON = photosJSON.getJSONObject(i);
                            //TODO convert json directly to complete object of photos without manually work
                            // decode the attirbutes of json into  a data model
                            InstagramPhoto instagramPhoto = new InstagramPhoto();
                            instagramPhoto.setUsername(photoJSON.getJSONObject("user").getString("username"));
                            if (photoJSON.optJSONObject("caption") != null) {
                                instagramPhoto.setCaption(photoJSON.getJSONObject("caption").getString("text"));
                            }
                            instagramPhoto.setImageUrl(photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url"));
                            instagramPhoto.setImageHeight(photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("height"));
                            instagramPhoto.setImageWidth(photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("width"));
                            instagramPhoto.setLikes(photoJSON.getJSONObject("likes").getInt("count"));
                            instagramPhoto.setProfilePicture(photoJSON.getJSONObject("user").getString("profile_picture"));
                            photos.add(instagramPhoto);

                        }
                    }
                    instagramPhotoAdapter.notifyDataSetChanged();
                    swipeContainer.setRefreshing(false);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // Handle Failure
                Log.i("DEBUG", "Instagram API Call failed");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
