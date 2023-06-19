package huynhph30022.fpoly.ontap1906.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import huynhph30022.fpoly.ontap1906.R;
import huynhph30022.fpoly.ontap1906.model.Feel;

public class RssAdapter extends RecyclerView.Adapter<RssAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<Feel> list;

    public RssAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<Feel> list) {
        this.list = list;
        notifyItemInserted(0);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_rss, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Feel objFeel = list.get(position);
        if (objFeel == null) {
            return;
        }
        holder.tvLinkRss.setText(objFeel.getLoc());
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLinkRss;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLinkRss = itemView.findViewById(R.id.tvLinkRss);
        }
    }
}
