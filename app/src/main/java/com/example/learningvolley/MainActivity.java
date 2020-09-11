package com.example.learningvolley;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //https://jsonplaceholder.typicode.com/todos/1
    //private RequestQueue requestQueue;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //we have created a queue that can request data from the web
        //there is a more efficient way to create this so it can be used anywhere in our project
        //requestQueue = Volley.newRequestQueue(MainActivity.this);
        //this is taken from the MySingleton class and uses it more efficiently
        queue = MySingleton.getInstance(this.getApplicationContext())
                .getRequestQueue();

        //we retrieve an object and display it in the log
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://jsonplaceholder.typicode.com/todos/1", (JSONObject) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("JSON: ", "onResponse: " + response);
                        try {
                            Log.d("JSON:", "onResponse: " + response.getString("title"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "onErrorResponse: " + error.getMessage());
            }
        });
        //this adds the object to the array and returns it
        //requestQueue.add(jsonObjectRequest);
        queue.add(jsonObjectRequest);

        //we retrieve an Array and display it in the log
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                "https://jsonplaceholder.typicode.com/todos", (JSONArray) null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("JSON: ", "onResponse: " + response);

                        //retrieve each title in the Array
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                //or you can directly call this boolean from the Array in this way instead
                                Log.d("JSONArray: ", "onResponse: " + jsonObject.getString("id")
                                        + " " + jsonObject.getString("title")
                                        + " " + jsonObject.getBoolean("completed"));
                                //you can insert this into a variable to be called at a later time
                                boolean b = jsonObject.getBoolean("completed");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "onErrorResponse: " + error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);
        //this adds the object to the array and returns it
        //requestQueue.add(jsonArrayRequest);
    }


}