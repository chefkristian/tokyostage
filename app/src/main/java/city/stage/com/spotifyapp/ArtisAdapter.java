package city.stage.com.spotifyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by indomegabyte on 03/03/16.
 */
public class ArtisAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mInflater;

    JSONArray mJsonArray;

    JSONObject mJsonObject;

    private int lastPosition = -1;

    // 1
    public ArtisAdapter(Context context, LayoutInflater inflater) {
        mContext = context;
        mInflater = inflater;
        mJsonArray = new JSONArray();
    }
    @Override
    public int getCount() {
        return mJsonArray.length();
    }

    @Override
    public Object getItem(int i) {return mJsonArray.optJSONObject(i);

    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        ArtistSearchResult artist;

        view = mInflater.inflate(R.layout.search_result_artist, null);

        artist = new ArtistSearchResult();
        artist.name = (TextView)view.findViewById(R.id.nama);
        artist.popularity =(TextView)view.findViewById(R.id.popularitas);
//artist.id_artist=(TextView)view.findViewById(R.id.artis_id);
        artist.image = (ImageView)view.findViewById(R.id.gambar);



        JSONObject jsonObject = (JSONObject) getItem(i);

        artist.name.setText(jsonObject.optString("name"));
        artist.popularity.setText(jsonObject.optString("popularity"));
       // artist.id_artist.setText(jsonObject.optString("id"));

        JSONArray images = jsonObject.optJSONArray("images");

        boolean adaGambar = false;

        for(int x = 0; x < images.length(); x++){
            try {
                if(!adaGambar) {
                    JSONObject object = images.getJSONObject(x);

                    String height = object.optString("height");
//                    if (height.equals("200")) {
                        String url = object.optString("url");
                        Picasso.with(mContext).load(url).placeholder(R.drawable.hourglass).into(artist.image);
                        adaGambar = true;
//                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // now do something with the Object
        }

        if(!adaGambar){
            artist.image.setVisibility(View.GONE);
        }



        return view;
    }

    public void masukin (JSONObject json){
        mJsonObject= json;
        mJsonArray = json.optJSONObject("artists").optJSONArray("items");

        //harus ada!!
        notifyDataSetChanged();
    }

    private static class ArtistSearchResult {

        public TextView name;
        public TextView popularity;
        public ImageView image;
        public TextView id_artist;

    }
}
