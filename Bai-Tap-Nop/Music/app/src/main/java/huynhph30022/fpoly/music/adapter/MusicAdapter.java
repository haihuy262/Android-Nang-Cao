package huynhph30022.fpoly.music.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import huynhph30022.fpoly.music.R;
import huynhph30022.fpoly.music.model.Music;
import huynhph30022.fpoly.music.service.MusicService;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<Music> list;
    private TextView tvTieuDe;

    public MusicAdapter(Context context, TextView tvTieuDe) {
        this.context = context;
        this.tvTieuDe = tvTieuDe;
    }

    public void setData(ArrayList<Music> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.items_music, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Music music = list.get(position);
        if (music == null) {
            return;
        }
        holder.tvTenBaiHat.setText(music.getTenNhac());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = list.get(holder.getAdapterPosition()).getTenNhac();
                tvTieuDe.setText(title);

                Intent intent = new Intent(context, MusicService.class);
                intent.putExtra("musicUri", list.get(holder.getAdapterPosition()).getLinkNhac());
                context.startService(intent);
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
            tvTenBaiHat = itemView.findViewById(R.id.tenBaiHat);
        }
    }
}
