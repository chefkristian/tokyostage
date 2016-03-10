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

/**
 * Created by indomegabyte on 07/03/16.
 */
public class SongActivity extends Activity implements AdapterView.OnItemClickListener {
    ListView list4;
    String y;
    SongAdapter mJSONAdapter;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        list4 = (ListView) findViewById(R.id.list4);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        y = this.getIntent().getExtras().getString("judul_lagu");
        Log.d("y", y);

        loadLagu(y);

        mJSONAdapter = new SongAdapter(this, getLayoutInflater());
        list4.setAdapter(mJSONAdapter);

        list4.setOnItemClickListener(this);


        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
              //  Toast.makeText(SongActivity.this,"Refresh", Toast.LENGTH_LONG).show();
               loadLagu(y);


            }
        });

    }

    private void loadLagu(String judulLagu) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://api.spotify.com/v1/search?q=" + judulLagu + "&type=track",
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        // Toast.makeText(getApplicationContext(), jsonObject.optString("picture"), Toast.LENGTH_SHORT).show();
                        Log.i("aplikasi-mobile.com", jsonObject.toString());
                         mJSONAdapter.masukin(jsonObject);
                        swipeContainer.setRefreshing(false);
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

        Uri myUri = Uri.parse(jsonObject.optString("preview_url"));
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(myUri, "audio/*");
        startActivity(intent);
    }
}