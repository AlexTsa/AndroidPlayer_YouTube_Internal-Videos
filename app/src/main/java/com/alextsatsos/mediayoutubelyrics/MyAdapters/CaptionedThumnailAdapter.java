package com.alextsatsos.mediayoutubelyrics.MyAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.alextsatsos.mediayoutubelyrics.R;
import com.alextsatsos.mediayoutubelyrics.YouTubeV3Bean.ItemsBean;
import com.alextsatsos.mediayoutubelyrics.YouTubeV3Bean.YoutubeV3Details;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CaptionedThumnailAdapter extends RecyclerView.Adapter<CaptionedThumnailAdapter.ViewHolder> {
    List<ItemsBean> results = new ArrayList<>();
    private Listener listener;
    public interface Listener {
        void onClick(int position);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }
    /*
    public CaptionedThumnailAdapter(String title[],String urlthumnail[]){
        this.title = title;
        this.urlthumnail=urlthumnail;
    }*/
    @Override
    public int getItemCount(){
        return results.size();
    }
    public void setListener(Listener listener) {
        this.listener = listener;
    }
    @Override
    public CaptionedThumnailAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType){
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_captioned_youtubethumbnail, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        ItemsBean itemsBean = results.get(position);
        ImageView imageView = (ImageView) cardView.findViewById(R.id.info_image);
        Picasso.get().load(itemsBean.getSnippet().getThumbnails().getHigh().getUrl()).into(imageView);
        TextView textView = (TextView)cardView.findViewById(R.id.info_text);
        textView.setText(itemsBean.getSnippet().getTitle());
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });

    }
    public void setResults(List<ItemsBean> results) {
        this.results = results;
        notifyDataSetChanged();
    }

}
