package city.stage.com.spotifyapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

/**
 * Created by indomegabyte on 03/03/16.
 */
public class ResultActivity extends Activity implements AdapterView.OnItemClickListener {
   // TextView tv_artis;
    ListView list1;
    String x;

    //JSONObject jo;
ArtisAdapter mJSONAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        list1= (ListView)findViewById(R.id.list1);
      //  tv_artis= (TextView)findViewById(R.id.tv_artis);

        x = this.getIntent().getExtras().getString("nama_artis");
        Log.d("x", x);


     //   tv_artis.setText(x);


        mJSONAdapter = new ArtisAdapter(this, getLayoutInflater());
        list1.setAdapter(mJSONAdapter);


        loadData(x);

    list1.setOnItemClickListener(this);

    }

    private void loadData(String namaArtis){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://api.spotify.com/v1/search?q=" + namaArtis + "&type=artist",
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        // Toast.makeText(getApplicationContext(), jsonObject.optString("picture"), Toast.LENGTH_SHORT).show();
                        Log.i("aplikasi-mobile.com", jsonObject.toString());
                        mJSONAdapter.masukin(jsonObject);
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

//
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // 21. ambil data gambar
        JSONObject jsonObject = (JSONObject) mJSONAdapter.getItem(i);
//        String pics = jsonObject.optString("picture","");

        // 22. buat Intent untuk berpindah ke SecondActivity
        Intent intent2 = new Intent(this, AlbumActivity.class);

        // 23. Isi putExtra
        intent2.putExtra("json",  jsonObject.toString());
        Log.d("x", x);
        // start the next Activity using your prepared Intent
        startActivity(intent2);

    }
    }
