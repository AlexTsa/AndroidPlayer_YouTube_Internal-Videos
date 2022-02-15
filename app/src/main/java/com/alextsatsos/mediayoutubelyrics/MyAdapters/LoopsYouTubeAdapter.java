package com.alextsatsos.mediayoutubelyrics.MyAdapters;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.alextsatsos.mediayoutubelyrics.Entities.LoopEntity;
import com.alextsatsos.mediayoutubelyrics.R;

import java.util.ArrayList;
import java.util.List;

public class LoopsYouTubeAdapter extends RecyclerView.Adapter<LoopsYouTubeAdapter.ViewHolder> {
    private List<LoopEntity> loopEntityList = new ArrayList<>();
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

    @NonNull
    @Override
    public LoopsYouTubeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_loops_youtube,parent,false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        final LoopEntity loopEntity = loopEntityList.get(position);
        final TextView loopsForYoutube_textCardView = cardView.findViewById(R.id.loopsForYoutube_textCardView);
        Handler handlerloopAdapter = new Handler();

        loopsForYoutube_textCardView.post(new Runnable() {
            @Override
            public void run() {
                loopsForYoutube_textCardView.setText(timeConversion(loopEntity.getLoopStart()) + "-" +timeConversion(loopEntity.getLoopEnd()));

            }
        });
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return loopEntityList.size();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
      //na allaksw onoma na balw loop
    public void setYouTubeEntityList(List<LoopEntity> loopEntityList){
        this.loopEntityList =  loopEntityList;
        notifyDataSetChanged();
    }
    public LoopEntity getLoopEntityAt(int position){
        return loopEntityList.get(position);
    }
    private String timeConversion(long value) {
        String songTime;
        int dur = (int) value;
        int hrs = (dur / 3600000);
        int mns = (dur / 60000) % 60000;
        int scs = dur % 60000 / 1000;

        if (hrs > 0) {
            songTime = String.format("%02d:%02d:%02d", hrs, mns, scs);
        } else {
            songTime = String.format("%02d:%02d", mns, scs);
        }
        return songTime;
    }


}
