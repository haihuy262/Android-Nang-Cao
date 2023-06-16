package huynhph30022.fpoly.ontapmob201.adapter;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import huynhph30022.fpoly.ontapmob201.MainActivity;
import huynhph30022.fpoly.ontapmob201.R;
import huynhph30022.fpoly.ontapmob201.model.Feel;
import huynhph30022.fpoly.ontapmob201.rss.WebViewActivity;

public class RssAdapter extends RecyclerView.Adapter<RssAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<Feel> list;
    private final String TAG = "RssAdapter";

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
        Log.d(TAG, "onCreateViewHolder");
        View view = LayoutInflater.from(context).inflate(R.layout.items_rss, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder");
        Feel objFeel = list.get(position);
        if (objFeel == null) {
            return;
        }
        holder.titleRss.setText(objFeel.getTitle());
        holder.pubDateRss.setText(objFeel.getPubDate());

        String description = objFeel.getDescription();
        Log.d(TAG, "Check description " + description);
        Document document = Jsoup.parse(description);
        Log.d(TAG, "Check document " + document);
        holder.subTitleRss.setText(document.text());

        Pattern pattern = Pattern.compile("src=\"(.*?)\"");
        Matcher matcher = pattern.matcher(description);
        String urlImage = "";
        if (matcher.find()) {
            urlImage = matcher.group(1);
        }
        Glide.with(context).load(urlImage).into(holder.imageRss);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Feel objFeel = list.get(holder.getAdapterPosition());
                if (objFeel == null) {
                    return;
                }
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("link", objFeel.getLink());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount");
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageRss;
        TextView titleRss, subTitleRss, pubDateRss;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageRss = itemView.findViewById(R.id.imageRss);
            titleRss = itemView.findViewById(R.id.titleRss);
            subTitleRss = itemView.findViewById(R.id.subTitleRss);
            pubDateRss = itemView.findViewById(R.id.pubDateRss);
        }
    }
}
