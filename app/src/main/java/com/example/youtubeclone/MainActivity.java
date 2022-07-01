package com.example.youtubeclone;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeBaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText searchEntryIV ;
    private ImageButton search_buttonIV ;
    private RecyclerView youTubeRv ;
    private YouTubeAdapter youTubeAdapter ;
  //  private ArrayList<YouTubeRvModel> YouTubeRVModelArrayList ;
    private String url , URL , key ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //FOR FULL SCREEN
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        searchEntryIV = findViewById(R.id.searchEntry) ;
        search_buttonIV = findViewById(R.id.search_button) ;
        youTubeRv = findViewById(R.id.rvfirst) ;
       // YouTubeRVModelArrayList = new ArrayList<>() ;
       // youTubeAdapter = new YouTubeAdapter(this , YouTubeRVModelArrayList);
        RequestQueue container ;
        container = Volley.newRequestQueue(this);
        url ="https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=15&q=";
        key ="&key=AIzaSyAGKMazc5bagpZbUzCaoBNYDJyWql0guY0";
        search_buttonIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                URL = url ;
                URL += searchEntryIV.getText().toString();
                URL += key ;
                Log.d("myapp", "onClick: "+URL);
                StringRequest job=new StringRequest(Request.Method.GET,URL,new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("items");
                            youTubeAdapter = new YouTubeAdapter(jsonArray);
                            youTubeRv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                            youTubeRv.setAdapter(youTubeAdapter);
                            Log.d("myapp", "onResponse:SUCCESS");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.d("myapp", "onErrorResponse:"+error);
                    }
                }
                );
                int socketTimeout = 10000;
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                job.setRetryPolicy(policy);
                container.add(job);
            }
        });

    }
}