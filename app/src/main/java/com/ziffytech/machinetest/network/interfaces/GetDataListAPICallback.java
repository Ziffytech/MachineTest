package com.ziffytech.machinetest.network.interfaces;

import com.android.volley.VolleyError;

public interface GetDataListAPICallback {
    void getDataListAPISuccessCallback(String response);
    void getDataListAPIFailureCallback(VolleyError error);
}
