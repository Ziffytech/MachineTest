package com.ziffytech.machinetest.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ziffytech.machinetest.network.interfaces.GetDataListAPICallback;

import java.util.HashMap;
import java.util.Map;

public class VolleyNetworkUtil {

    private static Map<String, String> createHeaders(String authKey) throws AuthFailureError {
        Map paramsHeader = new HashMap<String, String>();
        //paramsHeader.put("Authkey","kLcgQEl3NSyPtmWM1");
        //paramsHeader.put("Authkey","q2R5TKzIspurG1Z2W");
        paramsHeader.put("Authkey", authKey);
        Log.e("paramsHeader", paramsHeader.toString());
        return paramsHeader;
    }

    /**
     * This method is used for Get Data List call.
     *
     * @param context    Context for getting volley instance.
     * @param url        Url for the GetDataListAPICallback List
     * @param authKey    Auth key for API call
     * @param params     Parameters to be passed to the API. It is a Map object of String key and value.
     * @param requestTag The tag associated with the request. It is a String variable.
     * @param callback   Callback to the class which implements the GetDataListAPICallback interface.
     */
    public static void getdataListAPICall(Context context, String url, final String authKey,
                                          final Map<String, String> params, String requestTag,
                                          final GetDataListAPICallback callback) {
        try {
            final Map<String, String> headers = createHeaders(authKey);

            StringRequest getAppointmentListRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            callback.getDataListAPISuccessCallback(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            callback.getDataListAPIFailureCallback(error);
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() {
                    return headers;
                }
            };

            int socketTimeout = 10000;//10 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            getAppointmentListRequest.setRetryPolicy(policy);
            VolleyHelper.getInstance(context).addToRequestQueue(getAppointmentListRequest, requestTag);
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
            callback.getDataListAPIFailureCallback(authFailureError);
        }
    }


}
