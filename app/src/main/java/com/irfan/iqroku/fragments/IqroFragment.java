package com.irfan.iqroku.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.irfan.iqroku.R;
import com.irfan.iqroku.adapter.IqroListAdapter;
import com.irfan.iqroku.model.MIqro;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by wim on 4/5/16.
 */
public class IqroFragment extends Fragment {

    private int PAGE_SIZE = 4;

    private boolean isLastPage = false;
    private boolean isLoading = false;

    private RecyclerView listiqro;
    private LinearLayoutManager linearLayoutManager;
    private IqroListAdapter iqroListAdapter;

    protected Context context;

    int thumb[] = { R.drawable.alif, R.drawable.ba, R.drawable.ta,
            R.drawable.tsa, R.drawable.ja, R.drawable.ha,
            R.drawable.kha, R.drawable.da, R.drawable.dza,
            R.drawable.ra, R.drawable.za, R.drawable.sa ,
            R.drawable.sya, R.drawable.sho,
            R.drawable.dho, R.drawable.to, R.drawable.zo,
            R.drawable.ngain, R.drawable.ghoin, R.drawable.fa,
            R.drawable.qo, R.drawable.ka, R.drawable.la,
            R.drawable.ma, R.drawable.nun, R.drawable.waw,
            R.drawable.haa,R.drawable.ya
            };

    String name[] = {"Alif", "Ba", "Ta", "Tsa",
            "Jim", "Kha", "Kho", "Dal",
            "Dzal", "Ra", "Za", "Sin",
            "Syin", "Shod", "Dhod", "Tho",
            "Dhlo", "'Ain", "Ghoin", "Fa",
            "Qof", "Kaf", "Lam", "Mim",
            "Nun", "Wawu", "Hamzah",
           "Ya"};

 String suara [] = {

         "alif", "ba", "ta", "tsa",
         "jim", "ha", "kho", "dal",
         "dzal", "ro", "za", "sin",
         "syin", "shod", "dlod", "tho",
         "dzo", "ngain", "ghoin", "fa",
         "qof", "kaf", "lamalif", "mim",
         "nun", "wawu", "hamzah",
         "ya"

        };

    public static IqroFragment newInstance(){
        return new IqroFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_iqro, container, false);

        listiqro = (RecyclerView) rootView.findViewById(R.id.listIqro);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        linearLayoutManager = new LinearLayoutManager(context);


        iqroListAdapter = new IqroListAdapter(getActivity());

        listiqro.setLayoutManager(linearLayoutManager);
        listiqro.setAdapter(iqroListAdapter);
        listiqro.addOnScrollListener(recyclerViewOnScrollListener);

        initData();
        initListener();
    }

    private void initData(){
        List<MIqro> iqroList = new ArrayList<>();
        MIqro iqro;

        for(int i=0; i < PAGE_SIZE; i++){
            iqro = new MIqro();

            iqro.setId(i+1);
            iqro.setName(name[i]);
            iqro.setThumb(thumb[i]);
            iqro.setSuara(suara[i]);

            iqroList.add(iqro);
        }

        iqroListAdapter.addAll(iqroList);

    }

    private void loadData(){
        isLoading = false;

        List<MIqro> iqroList = new ArrayList<>();
        MIqro iqro;

        int index = iqroListAdapter.getItemCount() - 1;
        int end = index + PAGE_SIZE;

        if (end <= thumb.length) {
            for (int i = index; i < end; i++) {
                iqro = new MIqro();

                iqro.setId(i + 1);
                iqro.setName(name[i]);
                iqro.setThumb(thumb[i]);
                iqro.setSuara(suara[i]);

                iqroList.add(iqro);
            }
            iqroListAdapter.addAll(iqroList);
            if(end >= thumb.length){
                iqroListAdapter.setLoading(false);
            }
        }

    }

    private void initListener() {
        iqroListAdapter.setOnItemClickListener(new IqroListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(context, "Clicked at index "+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = linearLayoutManager.getChildCount();
            int totalItemCount = linearLayoutManager.getItemCount();
            int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

            if (!isLoading && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE) {
                    isLoading = true;

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadData();
                        }
                    }, 500);
                }
            }
        }
    };
}
