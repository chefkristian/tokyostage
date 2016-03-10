package city.stage.com.spotifyapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;


public class MainActivity extends Activity implements View.OnClickListener {

    EditText etSearch;
    Button button1;
    Switch switchButton;
    // String ArtistName = "Artist";
    // String TrackName = "TrackTitle";
    TextView track,artis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSearch = (EditText) findViewById(R.id.etSearch);
        button1 = (Button) findViewById(R.id.button1);
track = (TextView)findViewById(R.id.track);
        artis = (TextView)findViewById(R.id.artis);
        //  button2 = (Button)findViewById(R.id.button2);
        switchButton = (Switch) findViewById(R.id.switchButton);

        button1.setOnClickListener(this);
        // button2.setOnClickListener(this);

//        switchButton.setChecked(true);
//        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
//                if (bChecked) {
//                    textView.setText(ArtistName);
//
//                } else {
//                    textView.setText(TrackName);
//                }
//            }
//        });
//
//        if (switchButton.isChecked()) {
//            textView.setText(ArtistName);
//        } else {
//            textView.setText(TrackName);
//        }
//        switchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (switchButton.isChecked()) {
//                    artis.setTextColor(Color.parseColor("#F44336"));
//                    track.setTextColor(Color.parseColor("#8E24AA"));
//                   // Toast.makeText(MainActivity.this, "Artist Name", Toast.LENGTH_LONG).show();
//
//                } else {
//                    track.setTextColor(Color.parseColor("#F44336"));
//                   artis.setTextColor(Color.parseColor("#8E24AA"));
//                  //  Toast.makeText(MainActivity.this, "Track Title", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
        track.setTextColor(Color.parseColor("#C62828"));
        artis.setTextColor(Color.parseColor("#9E9E9E"));
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    artis.setTextColor(Color.parseColor("#C62828"));
                    track.setTextColor(Color.parseColor("#9E9E9E"));
                } else {
                    track.setTextColor(Color.parseColor("#C62828"));
                    artis.setTextColor(Color.parseColor("#9E9E9E"));

                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onClick(View view) {
        if (switchButton.isChecked()) {
            if (view.getId() == R.id.button1) {

                String a = etSearch.getText().toString();
                if (a.equals("")){
                    Toast.makeText(this,"Artist masih kosong",Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(this, ResultActivity.class);
                intent.putExtra("nama_artis", a);

                Log.d("a", a);
                startActivity(intent);


            }
        } else {
            if (view.getId() == R.id.button1) {

                String b = etSearch.getText().toString();
                if (b.equals("")){
                    Toast.makeText(this,"Tracks masih kosong",Toast.LENGTH_SHORT).show();

                }

                Intent intentlagu = new Intent(this, SongActivity.class);
                intentlagu.putExtra("judul_lagu", b);

                Log.d("b", b);
                startActivity(intentlagu);
            }
//        if (view.getId()==R.id.button2) {
//
//            String b = etSearch.getText().toString();
//
//            Intent intent2 = new Intent(this,SongActivity.class);
//            intent2.putExtra("judul_lagu",b);
//            startActivity(intent2);
//        }
        }

    }
}
