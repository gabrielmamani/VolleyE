package info.androidhive.volleyexamples;

import info.androidhive.volleyexamples.app.AppController;
import info.androidhive.volleyexamples.utils.Const;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

public class JsonRequestActivity extends Activity implements OnClickListener {

    private String TAG = JsonRequestActivity.class.getSimpleName();
    private Button btnJsonObj, btnJsonArray;
    private TextView msgResponse;
    private ProgressDialog pDialog;
    public String jsonResponse;

    // These tags will be used to cancel the requests
    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        btnJsonObj = (Button) findViewById(R.id.btnJsonObj);
        btnJsonArray = (Button) findViewById(R.id.btnJsonArray);
        msgResponse = (TextView) findViewById(R.id.msgResponse);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        btnJsonObj.setOnClickListener(this);
        btnJsonArray.setOnClickListener(this);
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    /**
     * Making json object request
     */
    private void makeJsonObjReq() {
        showProgressDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
                Const.URL_JSON_OBJECT, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        msgResponse.setText(response.toString());
                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("placa", "todas");
                //params.put("email", "abc@androidhive.info");
                //params.put("pass", "password123");

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    }

    /**
     * Making json array request
     */
    private void makeJsonArryReq() {
        showProgressDialog();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                JsonArrayRequest req = new JsonArrayRequest(Const.URL_JSON_ARRAY,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                Log.d(TAG, response.toString());
                                //msgResponse.setMovementMethod(new ScrollingMovementMethod());
                                //msgResponse.setText(response.toString());
                                //hideProgressDialog();

                                try {
                                    // Parsing json array response
                                    // loop through each json object
                                    jsonResponse = "";
                                    for (int i = 0; i < response.length(); i++) {

                                        JSONObject trama = (JSONObject) response.get(i);

                                        String asimut = trama.getString("Asimut");
                                        String EstadoGPS = trama.getString("EstadoGPS");
                                        String EstadoMotor = trama.getString("EstadoMotor");
                                        String EstadoPuerta = trama.getString("EstadoPuerta");
                                        String FechaGPS = trama.getString("FechaGPS");
                                        String ID = trama.getString("ID");
                                        String IDButton = trama.getString("IDButton");
                                        String IMEI = trama.getString("IMEI");
                                        String Kilometraje = trama.getString("Kilometraje");
                                        String Latitud = trama.getString("Latitud");
                                        String Longitud = trama.getString("Longitud");
                                        String NIT = trama.getString("NIT");
                                        String Nombre = trama.getString("Nombre");
                                        String Nro = trama.getString("Nro");
                                        String NroPlaca = trama.getString("NroPlaca");
                                        String Temperatura = trama.getString("Temperatura");
                                        String Velocidad = trama.getString("Velocidad");
                                        String VoltajeBateria = trama.getString("VoltajeBateria");
                                        String direcciones = trama.getString("direcciones");
                                        String tipov = trama.getString("tipov");

                                        jsonResponse += "asimut: " + asimut + "\n";
                                        jsonResponse += "EstadoGPS: " + EstadoGPS + "\n";
                                        jsonResponse += "EstadoMotor: " + EstadoMotor + "\n";
                                        jsonResponse += "EstadoPuerta: " + EstadoPuerta + "\n";
                                        jsonResponse += "FechaGPS: " + FechaGPS + "\n";
                                        jsonResponse += "ID: " + ID + "\n";
                                        jsonResponse += "IDButton: " + IDButton + "\n";
                                        jsonResponse += "IMEI: " + IMEI + "\n";
                                        jsonResponse += "Kilometraje: " + Kilometraje + "\n";
                                        jsonResponse += "Latitud: " + Latitud + "\n";
                                        jsonResponse += "Longitud: " + Longitud + "\n";
                                        jsonResponse += "NIT: " + NIT + "\n";
                                        jsonResponse += "Nombre: " + Nombre + "\n";
                                        jsonResponse += "Nro: " + Nro + "\n";
                                        jsonResponse += "NroPlaca: " + NroPlaca + "\n";
                                        jsonResponse += "Temperatura: " + Temperatura + "\n";
                                        jsonResponse += "Velocidad: " + Velocidad + "\n";
                                        jsonResponse += "VoltajeBateria: " + VoltajeBateria + "\n";
                                        jsonResponse += "direcciones: " + direcciones + "\n";
                                        jsonResponse += "tipov: " + tipov + "\n\n\n";
                                    }

                                    msgResponse.setMovementMethod(new ScrollingMovementMethod());
                                    msgResponse.setText(jsonResponse);

                                    hideProgressDialog();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(),
                                            "Error: " + e.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        hideProgressDialog();
                    }
                });

                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(req,
                        tag_json_arry);

                // Cancelling request
                // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_arry);

            }
        }, 0, 5000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnJsonObj:
                makeJsonObjReq();
                break;
            case R.id.btnJsonArray:
                makeJsonArryReq();
                break;
        }

    }

}
