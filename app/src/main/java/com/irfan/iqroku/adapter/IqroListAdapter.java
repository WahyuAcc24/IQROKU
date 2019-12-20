package com.irfan.iqroku.adapter;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.irfan.iqroku.R;
import com.irfan.iqroku.model.MIqro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class IqroListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private boolean loading = true;

    private List<MIqro> iqroList;
    private OnItemClickListener onItemClickListener;
    private Context ctx;

    public IqroListAdapter(Context ctx) {
        iqroList = new ArrayList<>();
        this.ctx = ctx;
    }

    private void add(MIqro item) {
        iqroList.add(item);
        notifyItemInserted(iqroList.size());
    }

    public void addAll(List<MIqro> iqroList) {
        for (MIqro iqro : iqroList) {
            add(iqro);
        }
    }

    public void remove(MIqro item) {
        int position = iqroList.indexOf(item);
        if (position > -1) {
            iqroList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public MIqro getItem(int position){
        return iqroList.get(position);
    }

    @Override
    public int getItemViewType (int position) {
        if(isPositionFooter (position)) {
            return VIEW_TYPE_LOADING;
        }
        return VIEW_TYPE_ITEM;
    }

    private boolean isPositionFooter (int position) {
        return position == iqroList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_iqro, parent, false);
            return new IqroViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_loading, parent, false);
            return new LoadingViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof IqroViewHolder) {
            IqroViewHolder iqroViewHolder = (IqroViewHolder) holder;

            final MIqro iqro = iqroList.get(position);

            iqroViewHolder.iqroThumb.setImageResource(iqro.getThumb());
            iqroViewHolder.iqroName.setText(iqro.getName());
            iqroViewHolder.iqrolist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Uri myUri = Uri.parse("android.resource://com.irfan.iqroku/raw/"+iqro.getSuara()); // initialize Uri here
                    MediaPlayer mPlayer = new MediaPlayer();
                    mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    try {
                        mPlayer.setDataSource(ctx, myUri);
                        mPlayer.prepare();
                        mPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(ctx, iqro.getSuara(),Toast.LENGTH_SHORT).show();
                }
            });

        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
            loadingViewHolder.progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        }

    }

    public void setLoading(boolean loading){
        this.loading = loading;
    }

    @Override
    public int getItemCount() {
        return iqroList == null ? 0 : iqroList.size() + 1;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    static class IqroViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView iqroThumb;
        TextView iqroName;
        RelativeLayout iqrolist;

        OnItemClickListener onItemClickListener;

        public IqroViewHolder(View itemView) {
            super(itemView);

            iqroThumb = (ImageView) itemView.findViewById(R.id.imgIqroMenu);
            iqroName = (TextView) itemView.findViewById(R.id.namaIqro);
            iqrolist = itemView.findViewById(R.id.iqrolist);


//            itemView.setOnClickListener(this);
//            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());






        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);

            progressBar = (ProgressBar) itemView.findViewById(R.id.loading);
        }
    }


    public interface OnItemClickListener {

        void onItemClick(View view, int position);
    }
}
