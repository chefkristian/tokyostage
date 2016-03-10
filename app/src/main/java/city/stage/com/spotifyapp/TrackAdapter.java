package city.stage.com.spotifyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by indomegabyte on 04/03/16.
 */
public class TrackAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    Context mContext;
    JSONArray mJsonArray;
    JSONObject mJsonObject;

    private int lastPosition = -1;

    // 1
    public TrackAdapter(Context context, LayoutInflater inflater) {
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

        TrackSearchResult track;

        view = mInflater.inflate(R.layout.search_track_result, null);
        track = new TrackSearchResult();
        track.name = (TextView)view.findViewById(R.id.nama_track);
        track.number=(TextView)view.findViewById(R.id.track_no);
       // track.duration = (TextView)view.findViewById(R.id.durasi);

        JSONObject jsonObject = (JSONObject) getItem(i);
        track.number.setText(jsonObject.optString("track_number"));
        track.name.setText(jsonObject.optString("name"));
       // track.duration.setText(jsonObject.optString("duration_ms"));



        return view;
    }





    public void masukin (JSONObject json){
        mJsonObject= json;
        mJsonArray = json.optJSONArray("items");

        //harus ada!!
        notifyDataSetChanged();
    }
    private static class TrackSearchResult {
        public TextView number;
        public TextView name;
     //   public TextView duration;


    }
}
