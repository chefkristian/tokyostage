package city.stage.com.spotifyapp;

import android.content.Context;
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
 * Created by indomegabyte on 03/03/16.
 */
public class AlbumAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mInflater;

    JSONArray mJsonArray;

    JSONObject mJsonObject;

    private int lastPosition = -1;

    // 1
    public AlbumAdapter(Context context, LayoutInflater inflater) {
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

        AlbumSearchResult album;

        view = mInflater.inflate(R.layout.search_result_album, null);

        album = new AlbumSearchResult();
        album.name = (TextView)view.findViewById(R.id.nama);
        album.image = (ImageView)view.findViewById(R.id.gambar);

        JSONObject jsonObject = (JSONObject) getItem(i);

        album.name.setText(jsonObject.optString("name"));
        JSONArray images = jsonObject.optJSONArray("images");


        boolean adaGambar = false;

        for(int x = 0; x < images.length(); x++){
            try {
                if(!adaGambar) {
                    JSONObject object = images.getJSONObject(x);

                    String height = object.optString("height");
//                    if (height.equals("200")) {
                    String url = object.optString("url");
                    Picasso.with(mContext).load(url).into(album.image);
                    adaGambar = true;
//                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // now do something with the Object
        }

        if(!adaGambar){
            album.image.setVisibility(View.GONE);
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (i > lastPosition) ? R.anim.up_to_bottom : R.anim.down_from_top);
        view.startAnimation(animation);
        lastPosition = i;

        return view;
    }

    public void masukin (JSONObject json){
        mJsonObject= json;
        mJsonArray = json.optJSONArray("items");


        //harus ada!!
        notifyDataSetChanged();
    }
    private static class AlbumSearchResult {

        public TextView name;
        public ImageView image;

    }
}
