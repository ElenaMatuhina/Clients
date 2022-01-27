package com.example.clientlist.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientlist.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolderData> {
    private List<Client> clientList;
    private AdapterOnItemClicked adapterOnItemClicked;
    private int[] colorArray = {R.drawable.circle_yellow, R.drawable.circle_red, R.drawable.circle_green};
    private Context context;
    private SharedPreferences defPref;

    public DataAdapter(List<Client> clientList, AdapterOnItemClicked adapterOnItemClicked, Context context) {
        this.clientList = clientList;
        this.adapterOnItemClicked = adapterOnItemClicked;
        this.context = context;
        defPref = PreferenceManager.getDefaultSharedPreferences(context);
    }


    @NonNull
    @NotNull
    @Override
    public DataAdapter.ViewHolderData onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_client_list, parent, false);
        return new ViewHolderData(view, adapterOnItemClicked);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DataAdapter.ViewHolderData holder, int position) {
        holder.setData(clientList.get(position));
    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }

    public class ViewHolderData extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvFirstName;
        ImageView imType;
        ImageView statysVip;
        TextView tvLastName;
        TextView tvTelephone;
        private AdapterOnItemClicked adapterOnItemClicked;

        public ViewHolderData(@NonNull @NotNull View itemView, AdapterOnItemClicked adapterOnItemClicked) {
            super(itemView);
            tvFirstName = itemView.findViewById(R.id.tvFirstName);
            imType = itemView.findViewById(R.id.imType);
            statysVip = itemView.findViewById(R.id.statusVip);
            tvLastName = itemView.findViewById(R.id.tvLastName);
            tvTelephone = itemView.findViewById(R.id.tvTelItem);
            this.adapterOnItemClicked = adapterOnItemClicked;
            itemView.setOnClickListener(this);
        }

        public void setData(Client client) {
            tvFirstName.setTextColor(Color.parseColor(defPref.getString(context
                    .getResources()
                    .getString(R.string.settings_color_text_key), "#FF000000")));
            tvFirstName.setText(client.getFirstName());
            tvLastName.setText(client.getLastName());
            tvTelephone.setText(client.getTelepfone());
            imType.setImageResource(colorArray[client.getType()]);
            if(client.getVip() == 1) {
                statysVip.setVisibility(View.VISIBLE);
            } else {
                statysVip.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View view) {
            adapterOnItemClicked.onAdapterItemClicked(getAdapterPosition());
        }
    }

    public interface AdapterOnItemClicked {
        void onAdapterItemClicked(int position);
    }

    public void updateAdapter(List<Client> clientList) {
        this.clientList.clear();
        this.clientList.addAll(clientList);
        notifyDataSetChanged();
    }
}
