package huynhph30022.fpoly.phone_sms;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Contact> list;


    public ContactAdapter(ArrayList<Contact> list, Context context) {
        this.context = context;
        this.list = list;
    }

    public void setData(ArrayList<Contact> lst) {
        this.list = lst;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Contact contact = list.get(position);
        holder.tv1.setText(list.get(position).getName());
        holder.tv2.setText(list.get(position).getPhone());
        holder.tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + contact.getPhone()));
                v.getContext().startActivity(intent);
            }
        });
        holder.imgSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.dialog_message, null);
                dialog.setView(view);


                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
                TextView tv_dialog_name = view.findViewById(R.id.tv_dialog_name);
                TextView tv_dialog_phone = view.findViewById(R.id.tv_dialog_phone);
                tv_dialog_name.setText(list.get(position).getName());
                tv_dialog_phone.setText(list.get(position).getPhone());

                EditText edContent = alertDialog.findViewById(R.id.ed_dialog_content);

                TextView btnSend = alertDialog.findViewById(R.id.btn_dialog_send);
                btnSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String content = edContent.getText().toString();
                        String phone = contact.getPhone();
//                        Intent intent = new Intent(Intent.ACTION_SENDTO);
//                        intent.setData(Uri.parse("smsto:" + contact.getPhone()));
//                        intent.putExtra("sms_body", content);
                        //                        v.getContext().startActivity(intent);
                        if (!content.isEmpty()) {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(phone, null, content, null, null);
                            Toast.makeText(v.getContext(), "Đã gửi tin nhắn", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(v.getContext(), "Vui lòng nhập nội dung tin nhắn", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv1, tv2;
        private ImageView imgSMS;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv_item_name);
            tv2 = itemView.findViewById(R.id.tv_item_phone);
            imgSMS = itemView.findViewById(R.id.imgSMS);
        }
    }
}
