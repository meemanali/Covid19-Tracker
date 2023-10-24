package com.lgmInternee.covid19tracker;

import android.Manifest;
import android.annotation.SuppressLint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lgmInternee.covid19tracker.databinding.ActivityMainBinding;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean connection = networkInfo != null && networkInfo.isConnectedOrConnecting();
        if (!connection) return;

        binding.shimmerEffect.setVisibility(View.VISIBLE);
        binding.txtNoInternet.setVisibility(View.GONE);

        binding.txtLgm.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce));

        final String url = "https://data.covid19india.org/state_district_wise.json";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        ArrayList<DistrictData> arrayList = new ArrayList<>();

        JsonObjectRequest objectRequest = new JsonObjectRequest(url, jsonObject -> {
            try {

                Iterator<String> Iterator = jsonObject.keys();

                while (Iterator.hasNext()) {

                    String state = Iterator.next();
                    JSONObject stateData = jsonObject.getJSONObject(state);
                    JSONObject districtData = stateData.getJSONObject("districtData");
                    Iterator<String> districtIterator = districtData.keys();

                    while (districtIterator.hasNext()) {

                        String district = districtIterator.next();
                        JSONObject districtDetails = districtData.getJSONObject(district);
                        int confirmed = districtDetails.getInt("confirmed");
                        int active = districtDetails.getInt("active");
                        int deceased = districtDetails.getInt("deceased");
                        int recovered = districtDetails.getInt("recovered");

                        DistrictData data = new DistrictData(district, confirmed, active, deceased, recovered);
                        arrayList.add(data);
                    }
                }

                // by default shimmer is auto start. if u stop then u can also start or stop when u want
                MyAdapter myAdapter = new MyAdapter(this, arrayList);
                binding.listView.setAdapter(myAdapter);

                // hides shimmer effect when data is loaded
                binding.listView.post(binding.shimmerEffect::hideShimmer);

            } catch (Exception ex) {
                binding.txtLgm.setText(ex.getLocalizedMessage());
            }

        }, volleyError -> {
            Toast.makeText(MainActivity.this, volleyError.getLocalizedMessage(), Toast.LENGTH_LONG).show(); // you can also use Timber / Log here
        });

        requestQueue.add(objectRequest); // we request for getting json ie json object or json array data with RequestQueue


        binding.refreshLayout.setOnRefreshListener(() -> {
            recreate();
            binding.refreshLayout.setRefreshing(false);
        });

    }
}
