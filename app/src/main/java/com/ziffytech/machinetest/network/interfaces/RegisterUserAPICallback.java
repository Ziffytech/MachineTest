package com.ziffytech.machinetest.network.interfaces;

import com.android.volley.VolleyError;

public interface RegisterUserAPICallback {
    void registerUserAPISuccessCallback(String response);

    void registerUserAPIFailureCallback(VolleyError error);
}
