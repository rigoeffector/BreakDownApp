package com.example.rigoeffector.celestinandro.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rigoeffector.celestinandro.ApplyBreakdown;
import com.example.rigoeffector.celestinandro.Models.Subtypes;
import com.example.rigoeffector.celestinandro.R;

import java.util.List;

/**
 * Created by rigoeffector on 9/25/20.
 */

public class SubTypeAdapter extends RecyclerView.Adapter<SubTypeAdapter.ViewHolder> {
    private List<Subtypes> subtypesList ;
    private Context context;

    public SubTypeAdapter(List<Subtypes> subtypesList, Context context) {
        this.subtypesList = subtypesList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subtype_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Subtypes subtypes = subtypesList.get(position);
        holder.type_name.setText(subtypes.getType_name());
        holder.subtype_name.setText(subtypes.getName());
        holder.price.setText(subtypes.getPrice());
        holder.timing.setText(subtypes.getTiming());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ApplyBreakdown.class);
                intent.putExtra("subtypeId",subtypes.getId());
                intent.putExtra("brId", subtypes.getBr_id());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return subtypesList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        public TextView type_name, subtype_name, price, timing;
        public LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            type_name = (TextView) itemView.findViewById(R.id.type_name);
            subtype_name = (TextView) itemView.findViewById(R.id.subtype_name);
            price = (TextView) itemView.findViewById(R.id.price);
            timing = (TextView) itemView.findViewById(R.id.timing);
            linearLayout =(LinearLayout) itemView.findViewById(R.id.linearLayout);
        }
    }
}
