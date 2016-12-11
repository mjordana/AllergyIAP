package com.allergyiap.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.allergyiap.R;
import com.allergyiap.entities.AllergyEntity;
import com.allergyiap.entities.AllergyLevelEntity;

import java.util.List;

public class AllergiesAdapter extends RecyclerView.Adapter<AllergiesAdapter.AllergyViewHolder> {
    Context context;
    OnItemClickListener clickListener;
    List<AllergyLevelEntity> allergies;

    public AllergiesAdapter(Context context, List<AllergyLevelEntity> list) {
        this.context = context;
        this.allergies = list;
    }

    public void setAllergies(List<AllergyLevelEntity> list) {
        this.allergies = list;
        notifyDataSetChanged();
    }

    @Override
    public AllergyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_allergy, viewGroup, false);
        return new AllergyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AllergyViewHolder viewHolder, int i) {
        AllergyLevelEntity allergy = allergies.get(i);

        viewHolder.allergyEntity = allergy;
        viewHolder.name.setText(allergy.allergy_name);
        viewHolder.status.setText(allergy.forecast_level);
        int resource = 0;
        switch(Integer.parseInt(allergy.current_level)){
            case 0 : resource = R.drawable.legend_level_allergy_null; break;
            case 1 : resource = R.drawable.legend_level_allergy_low; break;
            case 2 : resource = R.drawable.legend_level_allergy_medium; break;
            case 3 : resource = R.drawable.legend_level_allergy_high; break;
            case 4 : resource = R.drawable.legend_level_allergy_veryhigh; break;
        }
        viewHolder.image.setImageResource(resource);

    }

    @Override
    public int getItemCount() {
        return allergies == null ? 0 : allergies.size();
    }


    public class AllergyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        TextView status;
        ImageView image;
        AllergyLevelEntity allergyEntity;
        View finalView;

        public AllergyViewHolder(View view) {
            super(view);
            this.finalView = view;
            name = (TextView) view.findViewById(R.id.name);
            status = (TextView) view.findViewById(R.id.status);
            image = (ImageView) view.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v, getPosition(), allergyEntity);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, AllergyLevelEntity alertEntity);
    }

    public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
}
