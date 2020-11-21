package com.example.rigoeffector.celestinandro.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rigoeffector.celestinandro.Home;
import com.example.rigoeffector.celestinandro.Models.ListItem;
import com.example.rigoeffector.celestinandro.R;
import com.example.rigoeffector.celestinandro.Sessions.SaveSharedPreference;
import com.example.rigoeffector.celestinandro.getSubType;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by rigoeffector on 9/25/20.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private SharedPreferences sharedPreferences;
    private List<ListItem> listItems ;
    private Context context;

    public MyAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, int position) {

        final ListItem listItem = listItems.get(position);
        holder.type_name.setText(listItem.getType_name());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"You clicked"+listItem.getId(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(),getSubType.class);
                intent.putExtra("TypeName", listItem.getType_name());
                intent.putExtra("TypeId",listItem.getId());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        public TextView type_name;
        public LinearLayout linearLayout;
        public ViewHolder(View itemView) {

            super(itemView);

            type_name = (TextView) itemView.findViewById(R.id.break_down_name);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.LinearLayout_break_down);
        }
    }
}
