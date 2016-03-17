package city.stage.com.spotifyapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by indomegabyte on 07/03/16.
 */
public class SongAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    Context mContext;
    JSONArray mJsonArray;
    JSONObject mJsonObject;

    private int lastPosition = -1;

    // 1
    public SongAdapter(Context context, LayoutInflater inflater) {
        mContext = context;
        mInflater = inflater;
        mJsonArray = new JSONArray();
    }

    @Override
    public int getCount() { return mJsonArray.length(); }

    @Override
    public Object getItem(int i) { return mJsonArray.optJSONObject(i); }

    @Override
    public long getItemId(int i) {return i;}


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        SongSearchResult song;

        view = mInflater.inflate(R.layout.search_song_result, null);

        song = new SongSearchResult();
        song.name = (TextView)view.findViewById(R.id.nama_track);
        song.popularitas=(TextView)view.findViewById(R.id.popularity);
       song.artis = (TextView)view.findViewById(R.id.nama_artis);
        song.gambar = (ImageView)view.findViewById(R.id.gbr_lagu);

        JSONObject jsonObject = (JSONObject) getItem(i);
        song.name.setText(jsonObject.optString("name"));
        song.popularitas.setText(jsonObject.optString("popularity"));

        String nama_Artis = "";


        //json structure
        JSONArray arr_artis = jsonObject.optJSONArray("artists");
        for(int x = 0; x < arr_artis.length(); x++){
            try {
                JSONObject artist_object = arr_artis.getJSONObject(x);
                nama_Artis = nama_Artis + "" +artist_object.optString("name");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        song.artis.setText(nama_Artis);


        //ambil JSON Object didalam JSON Object
        String preview_url = jsonObject.optString("preview_url");

        JSONObject external_urls = jsonObject.optJSONObject("external_urls");
        String spotify = external_urls.optString("spotify");

        Log.d("spotify", spotify);



        JSONObject album = jsonObject.optJSONObject("album");
        String album_type = album.optString("album_type");

        Log.d("album_type",album_type);

        //JSONObject album = jsonObject.optJSONObject("album");
        JSONObject album_url = album.optJSONObject("external_urls");
        String album_spotify = external_urls.optString("spotify");

        JSONArray images_album = album.optJSONArray("images");


        String url = "";

        for(int x = 0; x < 3; x=x+1){
            JSONObject images_object = null;
            try {
                images_object = images_album.getJSONObject(x);
                url = images_object.optString("url");

                if(!url.equals("")){
                    break;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        Log.d("Images URL",url);

        //contoh kedua ..ambil langsung dari Array selalu urtutan pertama (ke 0)
        try {

            JSONObject imageObj = images_album.getJSONObject(0);
            String imagesURL = imageObj.optString("url");
            Picasso.with(mContext).load(imagesURL).into(song.gambar);

            Log.d("Images URL 1",imagesURL);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        Animation animation = AnimationUtils.loadAnimation(mContext, (i > lastPosition) ? R.anim.up_to_bottom : R.anim.down_from_top);
        view.startAnimation(animation);
        lastPosition = i;
        return view;
    }

    public void masukin(JSONObject json) {
        mJsonObject = json;
        mJsonArray = json.optJSONObject("tracks").optJSONArray("items");

        //harus ada!!
        notifyDataSetChanged();
    }

    private static class SongSearchResult {
        public TextView popularitas;
        public TextView name;
        public TextView artis;
        public ImageView gambar;

        //   public TextView duration;


    }
}
