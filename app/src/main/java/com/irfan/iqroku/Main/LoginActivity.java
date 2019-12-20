package com.irfan.iqroku.Main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.irfan.iqroku.R;
import com.irfan.iqroku.model.MLogin;

import java.util.HashMap;
import java.util.Map;

import io.isfaaghyth.rak.Rak;

public class LoginActivity extends AppCompatActivity {


    EditText nama_txt, pass_txt;
    Button masuk;
    TextView daftar;
    Intent intent;



    ConnectivityManager conMgr;

    ProgressDialog pDialog;

    private String url = "http://10.10.20.18/iqroku/login.php";
    private static final String TAG = LoginActivity.class.getSimpleName();

    public final static String TAG_NAMA = "nama";
    public final static String TAG_PASS = "password";

    String tag_json_obj = "json_obj_req";

    SharedPreferences sharedPreferences;
    Boolean session = false;
    String id,pass;
    public  static final String my_shared_preferences ="my_shared_preferences";
    public static final String session_status = "session_status";

    // No 2
// void onCreate diperuntukan bungkus dari program yang telah dibuat, dan untuk menampilkan sesuai perintah
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        // No 3
// Library android RAK bertujuan untuk menyimpan data json kedalam android (jika diperlukan)
        Rak.initialize(this);


        // No 4
// melakukan cek koneksi Internet apakah telah Aktif atau tidak
        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {

            } else {
                Toast.makeText(getApplicationContext(), "TIDAK ADA KONEKSI INTERNET",
                        Toast.LENGTH_LONG).show();
            }

        }

        // No 5
// inisialisasi penamaan subjek (No 1) untuk memasangkan ke objek  .
        masuk = (Button) findViewById(R.id.btnLogin);

        nama_txt = (EditText) findViewById(R.id.edtuser);
        nama_txt.getText(); // method mengambil Text kalau menempatkan text ke komponen android setTex();
        pass_txt = (EditText) findViewById(R.id.edtpass);
        pass_txt.getText();

        // No 6
// menyimpan id dan pass ke dalam android
        sharedPreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedPreferences.getBoolean(session_status, false);
        id = sharedPreferences.getString(TAG_NAMA, null);
        pass = sharedPreferences.getString(TAG_PASS, null);

        // No 7
// Menyimpan id login
        if (Rak.isExist("login")) {
            if (Rak.grab("login")) {
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            }
        }

        if (session) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra(TAG_NAMA, id);
            intent.putExtra(TAG_PASS, pass);
            startActivity(intent);
            finish();
        }

        // No 8
// Void untuk tombol login , disini kita memerintahkan sebuah tombol mau ngapain aja..
// disini saya memerintahkan tombol login yang didalam edit text terdapat npm dan pass
        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = nama_txt.getText().toString();
                String pass = pass_txt.getText().toString();
                // No 9
// Jika npm dan password itu admin. maka diarahkan ke adminActivity
//                if (npm.equals("admin") && pass.equals("admin")) {
//                    Intent log = new Intent(LoginActivity.this, AdminActivity.class);
//                    startActivity(log);
//                } else {
                    if (nama.trim().length() > 0 && pass.trim().length() > 0) {
                        if (conMgr.getActiveNetworkInfo() != null
                                && conMgr.getActiveNetworkInfo().isAvailable()
                                && conMgr.getActiveNetworkInfo().isConnected()) {
                            ceklogin(nama, pass);
                        } else {
                            Toast.makeText(getApplicationContext(), "tidak ada koneksi", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "kolom tidak boleh kosong", Toast.LENGTH_LONG).show();

                    }
                }

        });

// Tombol untuk Regis, ketika di klik akan ke halaman regis
        daftar = (TextView) findViewById(R.id.txtDaftar);
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(LoginActivity.this, RegisActivity.class);
                startActivity(intent);
            }
        });

    }

    // No 10
// Melakukan void baru untuk pelemparan data dengan Stringrequest, di void ini menghubungkan antara android API dan Database
    private void ceklogin(final String nama, final String password){

// gambar loading muter2 akan ilang jika berhasil
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Mohon Tunggu .....");
        showDialog();

// Method penghubung dari  Android API dan Database
        StringRequest strReq = new StringRequest(Request.Method.POST, url + "?nama=" + nama + "&password=" +
                password, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Response: " + response.toString());

                MLogin res = new Gson().fromJson(response, MLogin.class);

                // jika npm dan pasword benar maka akan diarahkan ke halaman menu utama
                if (res.isStatus()) {

                    // No 11
                    // menyimpan data Pemilih didalam android sementara

//                    Rak.entry("id", res.getData_mhs().getId());
//                    Rak.entry("nama", res.getData_mhs().getNama());
                    Rak.entry("login", true);

                    hideDialog();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();

                    // Jika salah atau belum terdaftar maka akan muncul text dibawah ini
                } else {
                    Toast.makeText(getApplicationContext(), "gagal bro", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            // pengecek salah dengan Volley library
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,"Login Error :" + error.getMessage());
                Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        })
        {


            // inisialisasi peletakan objek yang ada di dalam android ke isi Table yang ada di database
            @Override
            protected Map<String, String> getParams() {
                //posting parameter to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("nama", nama); // yang ada kutipnya itu sesuaiin dengan didalam table database. sebelahnya sesuain seperti di no 10 yang ada finalnya
                params.put("password", password);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
    private  void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();

    }

}
