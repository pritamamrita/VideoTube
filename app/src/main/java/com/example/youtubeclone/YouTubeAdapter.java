package com.example.youtubeclone;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList ;

public class YouTubeAdapter extends RecyclerView.Adapter<YouTubeAdapter.ViewHolder> {
    private JSONArray jsonArray ;

    public YouTubeAdapter(JSONArray jsonArray) {
        this.jsonArray = jsonArray ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvfirst_content ,parent , false) ;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.videoID=jsonArray.getJSONObject(position).getJSONObject("id").getString("videoId").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            holder.getUploadTimeTV().setText("   Date: "+jsonArray.getJSONObject(position).getJSONObject("snippet").getString("publishTime").toString().substring(0,10));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            holder.getChannelNameTV().setText("   Channel: "+jsonArray.getJSONObject(position).getJSONObject("snippet").getString("channelTitle").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            holder.getNameTV().setText(jsonArray.getJSONObject(position).getJSONObject("snippet").getString("title").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url= null ;
        try {
            url = jsonArray.getJSONObject(position).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("high").getString("url").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.getThumbnailTV().setImageBitmap(get(url));

    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumbnailTV ;
        private TextView nameTV;
        private TextView channelNameTV;
        public ImageView getThumbnailTV() { return thumbnailTV; }
        public TextView getNameTV() {return nameTV;}
        public TextView getChannelNameTV() { return channelNameTV; }
        public TextView getUploadTimeTV() { return uploadTimeTV;}

        private TextView uploadTimeTV ;
        private String videoID ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnailTV = itemView.findViewById(R.id.video_thumbnail) ; //im
            nameTV = itemView.findViewById(R.id.vName) ; //t
            channelNameTV = itemView.findViewById(R.id.vChannelName) ; //c
            uploadTimeTV = itemView.findViewById(R.id.vUploadTime) ; //p
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext() , MainActivity2.class) ;
                    intent.putExtra("vid" , videoID) ;
                    intent.putExtra("name" , nameTV.getText().toString()) ;
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
    public static Bitmap get(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }
}
