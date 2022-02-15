package com.alextsatsos.mediayoutubelyrics.MyAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.alextsatsos.mediayoutubelyrics.LocalVideoModel.VideoModel;
import com.alextsatsos.mediayoutubelyrics.R;


import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("unchecked")
public class LocalVideoAdapter extends RecyclerView.Adapter<LocalVideoAdapter.ViewHolder> implements Filterable {
private List<VideoModel> videoModelList = new ArrayList<>();

private List<VideoModel> videoModelFullList;
private Listener listener;



    public interface Listener{
    void onClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    @NonNull
    @Override
    public LocalVideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_localvideos,parent,false);
        return new ViewHolder(cv);

    }

    @Override
    public void onBindViewHolder(@NonNull LocalVideoAdapter.ViewHolder holder, final int position) {
        CardView cardView =holder.cardView;
        VideoModel videoModel = videoModelList.get(position);
        TextView title_forLocalVideotext=cardView.findViewById(R.id.title_forLocalVideotextCardView);
        TextView duration_forLocalVideotext = cardView.findViewById(R.id.duration_forLocalVideotextCardView);
        title_forLocalVideotext.setText("Title: "+videoModel.getVideoTitle());
        duration_forLocalVideotext.setText("Duration: " +videoModel.getVideoDuration());
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener !=null){
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoModelList.size();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
    public void setResults(List<VideoModel> list){
      this.videoModelList =list;
      this.videoModelFullList =new ArrayList<>(list);
     notifyDataSetChanged();
    }
    public VideoModel getVideoModelAt(int position){
        return  videoModelList.get(position);
    }
    @Override
    public Filter getFilter() {
        return filterwithList;
    }
    private Filter filterwithList = new Filter() {
        String vtitle;

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<VideoModel> filterList = new ArrayList<>();
            if(constraint ==null || constraint.length()==0){
               filterList.addAll(videoModelFullList);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(VideoModel model:videoModelFullList){
                    vtitle = model.getVideoTitle();
                   if(vtitle.toLowerCase().contains(filterPattern)){
                   filterList.add(model);
                   }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            videoModelList.clear();
            videoModelList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}
