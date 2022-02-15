package com.alextsatsos.mediayoutubelyrics.MyAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.alextsatsos.mediayoutubelyrics.Entities.LoopForLocalVideoEntity;
import com.alextsatsos.mediayoutubelyrics.R;

import java.util.ArrayList;
import java.util.List;

public class LoopsLocalVideoAdapter extends RecyclerView.Adapter<LoopsLocalVideoAdapter.ViewHolder> {
    private List<LoopForLocalVideoEntity> loopForLocalVideoEntityList = new ArrayList<>();
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
    public LoopsLocalVideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_loops_localvideo, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull LoopsLocalVideoAdapter.ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        LoopForLocalVideoEntity loopForLocalVideoEntity = loopForLocalVideoEntityList.get(position);
        TextView loopsForLocalVideo_textCardView = cardView.findViewById(R.id.loopsForLocalVideo_textCardView);
        loopsForLocalVideo_textCardView.setText(timeConversion(loopForLocalVideoEntity.getLoopLocalVideoStart()) + "-" + timeConversion(loopForLocalVideoEntity.getLoopLocalVideoEnd()));

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
        return loopForLocalVideoEntityList.size();

    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void setLoopForLocalVideoEntityList(List<LoopForLocalVideoEntity> loopForLocalVideoEntityList) {
        this.loopForLocalVideoEntityList = loopForLocalVideoEntityList;
        notifyDataSetChanged();
    }

    public LoopForLocalVideoEntity getLoopLocalVideoEntity(int position) {
        return loopForLocalVideoEntityList.get(position);
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
