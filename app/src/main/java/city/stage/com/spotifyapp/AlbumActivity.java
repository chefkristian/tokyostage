package city.stage.com.spotifyapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


/**
 * Created by indomegabyte on 03/03/16.
 */
public class AlbumActivity extends Activity implements AdapterView.OnItemClickListener {
    ListView list2;
    String z;
    AlbumAdapter mJSONAdapter;
    private SwipeRefreshLayout swipeContainer;
    private Tracker mTracker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        list2 = (ListView)findViewById(R.id.list2);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        z = this.getIntent().getExtras().getString("json");
        Log.d("z", z);


        mJSONAdapter = new AlbumAdapter(this, getLayoutInflater());
        list2.setAdapter(mJSONAdapter);

        list2.setOnItemClickListener(this);

        try {
            JSONObject jsonObj = new JSONObject(z);

            String id = jsonObj.optString("id");

            loadAlbums(id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                Toast.makeText(AlbumActivity.this,"Refresh", Toast.LENGTH_LONG).show();
                try {
                    JSONObject jsonObj = new JSONObject(z);

                    String id = jsonObj.optString("id");

                    loadAlbums(id);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        sendScreenImageName();

    }

    private void loadAlbums (String idArtist){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://api.spotify.com/v1/artists/" + idArtist + "/albums?album_type=SINGLE&offset=0&limit=50",
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        // Toast.makeText(getApplicationContext(), jsonObject.optString("picture"), Toast.LENGTH_SHORT).show();
                        Log.i("loadAlbum", jsonObject.toString());

                        mJSONAdapter.masukin(jsonObject);
                        swipeContainer.setRefreshing(false);

                        // mJSONAdapter.masukin(jsonObject);
///intent
                        //  jsonObject.toString();

                    }

                    @Override
                    public void onFailure(int statusCode, Throwable throwable, JSONObject error) {
                        // 15. Dismiss the ProgressDialog
//                        mDialog.dismiss();

                        //16. Keluarkan toast
                        Toast.makeText(getApplicationContext(), "Error: " + statusCode + " " + throwable.getMessage(), Toast.LENGTH_LONG).show();

                        //17. Print Log
                        Log.e("aplikasi-mobile.com", statusCode + " " + throwable.getMessage());
                    }

                });


    }




    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        JSONObject jsonObject = (JSONObject) mJSONAdapter.getItem(i);
//        String pics = jsonObject.optString("picture","");

        // 22. buat Intent untuk berpindah ke SecondActivity
        Intent intent3 = new Intent(this, TrackActivity.class);

        // 23. Isi putExtra
        intent3.putExtra("json",  jsonObject.toString());

        // start the next Activity using your prepared Intent
        startActivity(intent3);
    }
    @Override
    protected void onResume() {
        super.onResume();
        sendScreenImageName();

    }
    private void sendScreenImageName() {

        // [START screen_view_hit]
        Log.i("TAG", "Album Activity");
        mTracker.setScreenName("Album Activity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // [END screen_view_hit]
    }
}
