package com.irfan.iqroku.Main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
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

public class RegisActivity extends AppCompatActivity {

    Button daftar;
    Intent intent;
    EditText nama_txt, namaortu_txt, umur_txt, jk_txt, password_txt;


    ProgressDialog pDialog;
    private String url = "http://10.10.20.18/iqroku/daftar.php";
    private static final String TAG = RegisActivity.class.getSimpleName();
    String tag_json_obj = "json_obj_req";
    ConnectivityManager conMgr;

    // No 2
// void onCreate diperuntukan bungkus dari program yang telah dibuat, dan untuk menampilkan sesuai perintah

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regis_main);

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
                Toast.makeText(getApplicationContext(), " Silahkan Cek Lagi Koneksi Anda ",
                        Toast.LENGTH_LONG).show();
            }
        }

        // No 5
// inisialisasi penamaan subjek (No 1) untuk memasangkan ke objek  .

        nama_txt = (EditText) findViewById(R.id.edtNama);
        umur_txt = (EditText) findViewById(R.id.edtUmur);
        jk_txt = (EditText) findViewById(R.id.edtJk);
        namaortu_txt = (EditText) findViewById(R.id.edtNamaOrtu);
        password_txt = (EditText) findViewById(R.id.edtPassReg);
        daftar = (Button) findViewById(R.id.btnReg);

        // No 6
// Void untuk tombol login , disini kita memerintahkan sebuah tombol mau ngapain aja..
// disini saya memerintahkan tombol login yang didalam edit text terdapat npm dan pass

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = nama_txt.getText().toString();
                String namaortu = namaortu_txt.getText().toString();
                String jk = jk_txt.getText().toString();
                String umur = umur_txt.getText().toString();
                String password = password_txt.getText().toString();

// No 9
// Jika npm dan password itu admin. maka diarahkan ke adminActivity
                if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {
                    Regis(nama, namaortu, jk, umur, password);
                } else {
                    Toast.makeText(getApplicationContext(), "cek internet anda", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    // No 10
// Melakukan void baru untuk pelemparan data dengan Stringrequest, di void ini menghubungkan antara android API dan Database

    private void Regis (final String nama, final String namaortu, final String jk, final String umur, final String password) {

// gambar loading muter2 akan ilang jika berhasil
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Registrasi sedang diproses...");
        showDialog();

// Method penghubung dari  Android API dan Database

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Mendaftar Merespon: " + response.toString());


                // No 11
                // menyimpan data Pemilih didalam android sementara
                try {
                    Rak.entry("nama", nama);
                    Rak.entry("namaortu", namaortu);
                    Rak.entry("jenis_kelamin", jk);
                    Rak.entry("umur", umur);
                    Rak.entry("password", password);


                    MLogin res = new Gson().fromJson(String.valueOf(response), MLogin.class);

                    if (res.isStatus()) {

                        hideDialog();

// isi kolom di edittext akan hilang jika telah berhasil melakukna registrasi
                        nama_txt.setText("");
                        namaortu_txt.setText("");
                        jk_txt.setText("");
                        umur_txt.setText("");
                        password_txt.setText("");

                        startActivity(new Intent(RegisActivity.this, LoginActivity.class));
                        finish();

                        // Jika salah atau belum terdaftar maka akan muncul text dibawah ini

                    }else{
                        hideDialog();
                        Toast.makeText(getApplicationContext(), "Nim sudah terdaftar", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Log.e("haha", e.getMessage());
                }
            }

            // sama kaya dilogin
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,"Login Error :" + error.getMessage());
                Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nama", nama);
                params.put("ortu", namaortu);
                params.put("jk", jk);
                params.put("umur", umur);
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

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        intent = new Intent(RegisActivity.this,LoginActivity .class);
        finish();
        startActivity(intent);
    }



}
