package huynhph30022.fpoly.dethi1706.adapter;

import android.content.Context;
import android.graphics.Color;
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

import huynhph30022.fpoly.dethi1706.R;
import huynhph30022.fpoly.dethi1706.dao.RssDAO;
import huynhph30022.fpoly.dethi1706.model.Feel;

public class RssAdapter extends RecyclerView.Adapter<RssAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<Feel> list;
    RssDAO rssDAO;

    public RssAdapter(Context context) {
        this.context = context;
        rssDAO = new RssDAO(context);
    }

    public void setData(ArrayList<Feel> list) {
        this.list = list;
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

        if (position % 2 == 0) {
            holder.tvTitleRss.setTextColor(Color.DKGRAY);
            holder.tvSubtitleRss.setTextColor(Color.DKGRAY);
            holder.tvPubDateRss.setTextColor(Color.DKGRAY);
        } else {
            holder.tvTitleRss.setTextColor(Color.BLUE);
            holder.tvSubtitleRss.setTextColor(Color.BLUE);
            holder.tvPubDateRss.setTextColor(Color.BLUE);
        }

        holder.tvTitleRss.setText(objFeel.getTitle());
        holder.tvPubDateRss.setText(objFeel.getPubDate());

        String description = objFeel.getDescription();
        Document document = Jsoup.parse(description);
        holder.tvSubtitleRss.setText(document.text());

        Pattern pattern = Pattern.compile("src=\"(.*?)\"");
        Matcher matcher = pattern.matcher(description);
        String urlImage = "";
        if (matcher.find()) {
            urlImage = matcher.group(1);
        }
        Glide.with(context).load(urlImage).into(holder.imgRss);
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgRss;
        TextView tvTitleRss, tvSubtitleRss, tvPubDateRss;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRss = itemView.findViewById(R.id.imgRss);
            tvTitleRss = itemView.findViewById(R.id.tvTitleRss);
            tvSubtitleRss = itemView.findViewById(R.id.tvSubtitleRss);
            tvPubDateRss = itemView.findViewById(R.id.tvPubDateRss);
        }
    }
}
