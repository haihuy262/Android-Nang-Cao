package huynhph30022.fpoly.ontap952023.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import huynhph30022.fpoly.ontap952023.R;
import huynhph30022.fpoly.ontap952023.activity.SuaLienHe;
import huynhph30022.fpoly.ontap952023.database.DanhBaDAO;
import huynhph30022.fpoly.ontap952023.model.DanhBa;

public class DanhBaAdapter extends RecyclerView.Adapter<DanhBaAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<DanhBa> list;
    private final DanhBaDAO danhBaDAO;

    public DanhBaAdapter(Context context) {
        this.context = context;
        danhBaDAO = new DanhBaDAO(context);
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

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.menu_xoa) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Thông báo");
                            builder.setMessage("Bạn có muốn xoá liên hệ này không?");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    boolean check = danhBaDAO.deleteDatabase(list.get(holder.getAdapterPosition()).getIdDanhBa());
                                    if (check) {
                                        Toast.makeText(context, "Đã xoá liên hệ", Toast.LENGTH_SHORT).show();
                                        loadData();
                                    } else {
                                        Toast.makeText(context, "Xoá thất bại!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            builder.setNegativeButton("No", null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else if (item.getItemId() == R.id.menu_sua) {
                            Intent intent = new Intent(context, SuaLienHe.class);
                            intent.putExtra("idDanhBa", list.get(holder.getAdapterPosition()).getIdDanhBa());
                            intent.putExtra("hoTen", list.get(holder.getAdapterPosition()).getHoTen());
                            intent.putExtra("soDienThoai", list.get(holder.getAdapterPosition()).getDienThoai());
                            intent.putExtra("email", list.get(holder.getAdapterPosition()).getEmail());
                            intent.putExtra("ghiChu", list.get(holder.getAdapterPosition()).getGhiChu());
                            context.startActivity(intent);
                        }
                        return true;
                    }
                });
                popupMenu.show();
                return false;
            }
        });

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

    private void loadData() {
        list.clear();
        list = danhBaDAO.getAllDatabase();
        notifyDataSetChanged();
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
