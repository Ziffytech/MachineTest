package com.ziffytech.machinetest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.ziffytech.machinetest.adapter.DataAdapter;
import com.ziffytech.machinetest.model.DataListModel;
import com.ziffytech.machinetest.network.VolleyNetworkUtil;
import com.ziffytech.machinetest.network.interfaces.GetDataListAPICallback;
import com.ziffytech.machinetest.network.interfaces.OnClickListenerRecyclerView;
import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements GetDataListAPICallback, OnClickListenerRecyclerView {

    private static final String TAG = MainActivity.class.getSimpleName();
    private DataAdapter mAdapter;
    private ArrayList<DataListModel> mDatamodelList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private DataListModel mDatamodellist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        initRecyclerView();
        setListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataListAPICall();
    }

    private void bindViews() {
        mRecyclerView = findViewById(R.id.recycler_view_pl);
    }


    private void initRecyclerView() {
        mAdapter = new DataAdapter(MainActivity.this, mDatamodelList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnClickListener(MainActivity.this);
    }

    private void setListeners() {

    }

    private void getDataListAPICall() {
        String url = "";
        String authKey = "";
        HashMap<String, String> params = new HashMap<>();
        params.put("type", "");
        params.put("type", "");
        Log.e(TAG, "PARAMS: " + params.toString());
        String requestTag = "getdataList";
        VolleyNetworkUtil.getdataListAPICall(this, url, authKey, params, requestTag, this);
    }


    @Override
    public void getDataListAPISuccessCallback(String response) {
        Log.e("RESPONSE", response);
        mDatamodelList.clear();


       /* try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray data = jsonObject.getJSONArray("data");
            Gson gson = new Gson();
            Type listType = new TypeToken<List<DataListModel>>() {
            }.getType();
            mDatamodelList.addAll(gson.fromJson(data.toString(), listType));
            mAdapter.refreshList(mDatamodelList);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

    }

    @Override
    public void getDataListAPIFailureCallback(VolleyError error) {
        Log.e("##", "##" + error.toString());
    }

    @Override
    public void onClickRecyclerView(View view, int position) {

    }
}
