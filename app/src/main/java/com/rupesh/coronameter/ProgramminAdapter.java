package com.rupesh.coronameter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by BHAWESHWARI on 07-06-2020.
 */

public class ProgramminAdapter extends RecyclerView.Adapter<ProgramminAdapter.ProgrammingViewHolder> {

    ArrayList<Data> arrayNames;

    public ProgramminAdapter() {
        arrayNames=new ArrayList<>();


    }

    public void setData( ArrayList<Data> arrayNames){
        this.arrayNames=arrayNames;

    }


    @NonNull
    @Override
    public ProgrammingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem,viewGroup,false);
        return new ProgrammingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgrammingViewHolder programmingViewHolder, int i) {
        Data data = arrayNames.get(i);


        programmingViewHolder.districtName.setText(data.name);
        programmingViewHolder.totalCaseD.setText(data.total);
        programmingViewHolder.deathsD.setText(data.deaths);
        programmingViewHolder.recoverD.setText(data.recover);



    }

    @Override
    public int getItemCount() {
        return arrayNames.size();
    }

    public class ProgrammingViewHolder extends RecyclerView.ViewHolder{
        TextView districtName;
        TextView textView13;
        TextView textView14;
        TextView textView15;
        TextView totalCaseD;
        TextView deathsD;
        TextView recoverD;
        public ProgrammingViewHolder(@NonNull View itemView) {
            super(itemView);
            districtName= itemView.findViewById(R.id.distictName);
            textView13 = itemView.findViewById(R.id.textView13);
            textView14 = itemView.findViewById(R.id.textView14);
            textView15 = itemView.findViewById(R.id.textView15);
            totalCaseD = itemView.findViewById(R.id.totalCaseD);
            deathsD = itemView.findViewById(R.id.deathsD);
            recoverD = itemView.findViewById(R.id.recoverD);
        }
    }
}
