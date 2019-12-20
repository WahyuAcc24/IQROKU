package com.irfan.iqroku.model;


import com.irfan.iqroku.R;

import java.util.Random;

public class Hijaiyah {

    private int listhuruf[] = {

            R.drawable.b_hijaiyah_alif,
            R.drawable.b_hijaiyah_ba,
            R.drawable.b_hijaiyah_ta,
            R.drawable.b_hijaiyah_tsa,
            R.drawable.b_hijaiyah_jim,
            R.drawable.b_hijaiyah_ha,
            R.drawable.b_hijaiyah_kha,
            R.drawable.b_hijaiyah_dal,
            R.drawable.b_hijaiyah_dza,
            R.drawable.b_hijaiyah_ra,
            R.drawable.b_hijaiyah_dzal,
            R.drawable.b_hijaiyah_sin,
            R.drawable.b_hijaiyah_syin,
            R.drawable.b_hijaiyah_sad,
            R.drawable.b_hijaiyah_dha,
            R.drawable.b_hijaiyah_tha,
            R.drawable.b_hijaiyah_zai,
            R.drawable.b_hijaiyah_ain,
            R.drawable.b_hijaiyah_ghain,
            R.drawable.b_hijaiyah_fa,
            R.drawable.b_hijaiyah_kaf,
            R.drawable.b_hijaiyah_lam,
            R.drawable.b_hijaiyah_mim,
            R.drawable.b_hijaiyah_nun,
            R.drawable.b_hijaiyah_wau,
            R.drawable.b_hijaiyah_haa,
            R.drawable.b_hijaiyah_ya

    };
//
    private int listhuruf2[] = {

        R.drawable.k_alif,
        R.drawable.k_ba,
        R.drawable.k_ta,
        R.drawable.k_tsa,
        R.drawable.k_jim,
        R.drawable.k_ha,
        R.drawable.k_kho,
        R.drawable.k_dal,
        R.drawable.k_dzal,
        R.drawable.k_ro,
        R.drawable.k_za,
        R.drawable.k_sin,
        R.drawable.k_shin,
        R.drawable.k_shod,
        R.drawable.k_dlod,
        R.drawable.k_tho,
        R.drawable.k_dzo,
        R.drawable.k_ain,
        R.drawable.k_ghin,
        R.drawable.k_fa,
        R.drawable.k_qof,
        R.drawable.k_lam,
        R.drawable.k__mim,
        R.drawable.k_nun,
        R.drawable.k_wawu,
        R.drawable.k_hha,
        R.drawable.k_ya

    };

    public int[] getlist(){
        return listhuruf;
    }
    public int[] getlist2(){
        return listhuruf2;
    }

    public int getrandomhuruf(){
        int rnd = new Random().nextInt(listhuruf.length);
        return rnd;
    }

    public int getimagesoal(int i){
        return listhuruf[i];
    }

    public int getimagejwbn(int i){
        return listhuruf2[i];
    }

    public int getjumlah(){
        return listhuruf.length;
    }
    public int getjumlah2(){
        return listhuruf2.length;
    }
}