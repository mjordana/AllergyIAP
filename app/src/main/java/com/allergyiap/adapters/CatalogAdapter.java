package com.allergyiap.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.allergyiap.R;
import com.allergyiap.entities.AllergyEntity;
import com.allergyiap.entities.CatalogEntity;
import com.allergyiap.entities.DownloadImageTask;
import com.allergyiap.entities.ProductCatalogEntity;

import java.util.List;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder> {
    Context context;
    OnItemClickListener clickListener;
    List<ProductCatalogEntity> catalogs;

    public CatalogAdapter(Context context, List<ProductCatalogEntity> list) {
        this.context = context;
        this.catalogs = list;
    }

    public void setCatalogs(List<ProductCatalogEntity> list) {
        this.catalogs = list;
        notifyDataSetChanged();
    }

    @Override
    public CatalogViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_catalog, viewGroup, false);
        return new CatalogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CatalogViewHolder viewHolder, int i) {
        ProductCatalogEntity catalaog = catalogs.get(i);

        viewHolder.catalogEntity = catalaog;
        viewHolder.name.setText(catalaog.product_name);
        viewHolder.description.setText(catalaog.product_description);

        //new DownloadImageTask(viewHolder.image).execute(catalaog.);
        viewHolder.image.setImageResource(R.drawable.allergy_product);

    }

    @Override
    public int getItemCount() {
        return catalogs == null ? 0 : catalogs.size();
    }


    public class CatalogViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        TextView description;
        ImageView image;
        ProductCatalogEntity catalogEntity;
        View finalView;

        public CatalogViewHolder(View view) {
            super(view);
            this.finalView = view;
            name = (TextView) view.findViewById(R.id.name);
            description = (TextView) view.findViewById(R.id.description);
            image = (ImageView) view.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v, getPosition(), catalogEntity);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, ProductCatalogEntity catalogEntity);
    }

    public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
}
