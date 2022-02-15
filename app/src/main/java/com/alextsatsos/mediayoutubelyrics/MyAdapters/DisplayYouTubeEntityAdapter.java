package com.alextsatsos.mediayoutubelyrics.MyAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.alextsatsos.mediayoutubelyrics.Entities.YouTubeEntity;
import com.alextsatsos.mediayoutubelyrics.R;

import java.util.ArrayList;
import java.util.List;

public class DisplayYouTubeEntityAdapter extends RecyclerView.Adapter<DisplayYouTubeEntityAdapter.ViewHolder>{
    private List<YouTubeEntity> youTubeEntityList = new ArrayList<>();
    private Listener listener;
    private OnLongListener onLongListener;



    public interface Listener {
        void onClick(int position);
    }
    public interface OnLongListener{
        void onLongClick(int position);
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
    public DisplayYouTubeEntityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_favourite_for_youtube,parent,false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        YouTubeEntity youTubeEntity = youTubeEntityList.get(position);
        TextView titleForYoutube_textCardView = cardView.findViewById(R.id.titleForYoutube_textCardView);
        TextView idForYoutube_textCardView = cardView.findViewById(R.id.idForYoutube_textCardView);
        TextView categoryForYoutube_textCardView = cardView.findViewById(R.id.categoryForYoutube_textCardView);
        TextView genreForYoutube_textCardView = cardView.findViewById(R.id.genreForYoutube_textCardView);
        titleForYoutube_textCardView.setText("Title: "+youTubeEntity.getTitle());
        idForYoutube_textCardView.setText("Id: "+youTubeEntity.getId());
        categoryForYoutube_textCardView.setText("Category: "+youTubeEntity.getCategory());
        genreForYoutube_textCardView.setText("Genre: "+youTubeEntity.getGenre());
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(onLongListener !=null){
                    onLongListener.onLongClick(position);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return youTubeEntityList.size();
    }
    public void setListener(Listener listener){
        this.listener = listener;
    }
    public void setOnLongListener(OnLongListener onLongListener){
       this.onLongListener = onLongListener;
    }
    public  YouTubeEntity getYouTubeEntityAt(int position){
        return youTubeEntityList.get(position);
    }
    public void setYouTubeEntityList(List<YouTubeEntity> youTubeEntityList){
        this.youTubeEntityList =  youTubeEntityList;
        notifyDataSetChanged();
    }

}
