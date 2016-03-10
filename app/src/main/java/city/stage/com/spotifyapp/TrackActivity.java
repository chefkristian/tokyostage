package city.stage.com.spotifyapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by indomegabyte on 04/03/16.
 */
public class TrackActivity extends Activity implements AdapterView.OnItemClickListener {
    ListView list3;
    String q;
TrackAdapter mJSONAdapter;
    JSONObject activeJSONObj;
    private SwipeRefreshLayout swipeContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        list3 = (ListView) findViewById(R.id.list3);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        q = this.getIntent().getExtras().getString("json");
        Log.d("q", q);

        mJSONAdapter = new TrackAdapter(this, getLayoutInflater());
        list3.setAdapter(mJSONAdapter);
        list3.setOnItemClickListener(this);



        try {
            JSONObject jsonObj = new JSONObject(q);

            String id = jsonObj.optString("id");

            loadTrack(id);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                Toast.makeText(TrackActivity.this, "Refresh", Toast.LENGTH_LONG).show();
                try {
                    JSONObject jsonObj = new JSONObject(q);

                    String id = jsonObj.optString("id");

                    loadTrack(id);

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

    }



    private void loadTrack(String idAlbum) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://api.spotify.com/v1/albums/" + idAlbum + "/tracks",
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        // Toast.makeText(getApplicationContext(), jsonObject.optString("picture"), Toast.LENGTH_SHORT).show();
                        Log.i("loadTrack", jsonObject.toString());


                        mJSONAdapter.masukin(jsonObject);
                        swipeContainer.setRefreshing(false);
///intent
                        //  jsonObject.toString();
                        activeJSONObj = jsonObject;
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

            Uri myUri = Uri.parse(jsonObject.optString("preview_url"));
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
            intent.setDataAndType(myUri, "audio/*");
            startActivity(intent);


    }
}
