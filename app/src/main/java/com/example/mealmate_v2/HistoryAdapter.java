package com.example.mealmate_v2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    Context context;
    ArrayList<HistoryItem> list;

    public HistoryAdapter(Context context, ArrayList<HistoryItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        HistoryItem item = list.get(position);
        holder.name.setText(item.getName());
        holder.type.setText(item.getType());
        holder.description.setText(item.getDescription());

        holder.delete.setOnClickListener(v -> {
            list.remove(position);
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {

        TextView name, type, description;
        View delete;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            type = itemView.findViewById(R.id.type);
            description = itemView.findViewById(R.id.description);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
