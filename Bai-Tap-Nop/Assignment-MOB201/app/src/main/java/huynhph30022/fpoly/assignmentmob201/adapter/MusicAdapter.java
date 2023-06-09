package huynhph30022.fpoly.assignmentmob201.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import huynhph30022.fpoly.assignmentmob201.R;
import huynhph30022.fpoly.assignmentmob201.fragment.MusicFragment;
import huynhph30022.fpoly.assignmentmob201.model.Song;
import huynhph30022.fpoly.assignmentmob201.service.MusicService;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {

    private final Context context;
    private final TextView tvTitleBaiHat;
    private final RelativeLayout relativeLayoutControlMusic;
    private ArrayList<Song> list;
    private final ImageView imgPlayOrPause;

    public MusicAdapter(Context context, TextView tvTitleBaiHat, RelativeLayout relativeLayoutControlMusic, ImageView imgPlayOrPause) {
        this.context = context;
        this.tvTitleBaiHat = tvTitleBaiHat;
        this.relativeLayoutControlMusic = relativeLayoutControlMusic;
        this.imgPlayOrPause = imgPlayOrPause;
    }

    public void setData(ArrayList<Song> list) {
        this.list = list;
        notifyItemInserted(0);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song objSong = list.get(position);
        if (objSong == null) {
            return;
        }
        holder.tvTenBaiHat.setText(objSong.getTenBaiHat());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Song objSong = list.get(holder.getAdapterPosition());
                if (objSong == null) {
                    return;
                }
                String tenBaiHat = objSong.getTenBaiHat();
                tvTitleBaiHat.setText(tenBaiHat);
                tvTitleBaiHat.setVisibility(View.VISIBLE);
                relativeLayoutControlMusic.setVisibility(View.VISIBLE);
                imgPlayOrPause.setImageResource(R.drawable.baseline_pause_circle_outline_24);

                Intent intent = new Intent(context, MusicService.class);
                intent.setAction(MusicService.ACTION_PLAY_SONG);
                intent.putExtra("link", objSong.getLink());
                context.startService(intent);

                Intent stopIntent = new Intent(context, MusicService.class);
                stopIntent.setAction(MusicService.ACTION_STOP_SONG);
                context.startService(stopIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenBaiHat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenBaiHat = itemView.findViewById(R.id.tvTenBaiHat);
        }
    }
}
