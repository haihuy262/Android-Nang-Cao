package huynhph30022.fpoly.assignmentmob201.adapter;

import android.content.Context;
import android.content.Intent;
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

import huynhph30022.fpoly.assignmentmob201.R;
import huynhph30022.fpoly.assignmentmob201.model.Newspaper;
import huynhph30022.fpoly.assignmentmob201.newspaper.WebViewActivity;

public class NewspaperAdapter extends RecyclerView.Adapter<NewspaperAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<Newspaper> list;

    public NewspaperAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<Newspaper> list) {
        this.list = list;
        notifyItemInserted(0);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.items_newspaper, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Newspaper objNewspaper = list.get(position);
        if (objNewspaper == null) {
            return;
        }
        holder.tvTitleNews.setText(objNewspaper.getTitle());
        holder.tvThoiGian.setText(objNewspaper.getPubDate());
        String description = objNewspaper.getDescription();
        Document document = Jsoup.parse(description);
        String text = document.text();
        holder.tvSubTitleNews.setText(text);
        Pattern pattern = Pattern.compile("src=\"(.*?)\"");
        Matcher matcher = pattern.matcher(description);
        String url_image = "";
        if (matcher.find()) {
            url_image = matcher.group(1);
        }
        Glide.with(context).load(url_image).into(holder.imgNews);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Newspaper objNewspaper = list.get(holder.getAdapterPosition());
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("link", objNewspaper.getLink());
                context.startActivity(intent);
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
        ImageView imgNews;
        TextView tvTitleNews, tvThoiGian, tvSubTitleNews;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgNews = itemView.findViewById(R.id.imgNews);
            tvTitleNews = itemView.findViewById(R.id.tvTitleNews);
            tvThoiGian = itemView.findViewById(R.id.tvThoiGian);
            tvSubTitleNews = itemView.findViewById(R.id.tvSubTitleNews);
        }
    }
}
