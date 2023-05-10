package huynhph30022.fpoly.ontap952023.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import huynhph30022.fpoly.ontap952023.R;
import huynhph30022.fpoly.ontap952023.model.DanhBa;

public class DanhBaAdapter extends RecyclerView.Adapter<DanhBaAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<DanhBa> list;

    public DanhBaAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<DanhBa> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.items_danh_ba, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DanhBa danhBa = list.get(position);
        if (danhBa == null) {
            return;
        }
        holder.hoTen.setText(danhBa.getHoTen());
        holder.soDienThoai.setText(danhBa.getDienThoai());

        // Hien thi Dialog
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.dialog_chi_tiet_danh_ba, null);
                builder.setView(view);

                AlertDialog dialog = builder.create();
                dialog.setCancelable(false);
                dialog.show();

                // Hien thi chi tiet danh ba
                TextView ctHoTen = view.findViewById(R.id.ctHoTen);
                TextView ctSoDT = view.findViewById(R.id.ctSoDienThoai);
                TextView ctEmail = view.findViewById(R.id.ctEmail);
                TextView ctGhiChu = view.findViewById(R.id.ctGhiChu);
                ctHoTen.setText(list.get(holder.getAdapterPosition()).getHoTen());
                ctSoDT.setText(list.get(holder.getAdapterPosition()).getDienThoai());
                ctEmail.setText(list.get(holder.getAdapterPosition()).getEmail());
                ctGhiChu.setText(list.get(holder.getAdapterPosition()).getGhiChu());

                // Close chi tiet danh ba
                ImageView imgClose = view.findViewById(R.id.imgClose);
                imgClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
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
        protected TextView hoTen, soDienThoai;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hoTen = itemView.findViewById(R.id.hoTen);
            soDienThoai = itemView.findViewById(R.id.soDienThoai);
        }
    }
}
