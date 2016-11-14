package com.allergyiap.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.allergyiap.R;
import com.allergyiap.adapters.CatalogAdapter;
import com.allergyiap.entities.CatalogEntity;

import java.util.ArrayList;
import java.util.List;

public class ProductCatalogActivity extends BaseActivity {

    static final String TAG = "ProductCatalog";

    private CatalogAdapter adapter;
    private RecyclerView recyclerView;
    List<CatalogEntity> catalogs = new ArrayList<>();
    private AsyncTask<Void, Void, Void> task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_catalog);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.scrollableview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        createArray();
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        adapter = null;

        if (task != null) {
            task.cancel(true);
            task = null;
        }
    }

    private void createArray() {
        catalogs = new ArrayList<>();
        catalogs.add(new CatalogEntity(1,"Nasal Steroids","These are drugs you spray into your nose","http://www.krishnaherbals.com/images/allergy-care-zoom.jpg"));
        catalogs.add(new CatalogEntity(1,"Antihistamines","These drugs work against the chemical histamine","http://www.alwaysayurveda.com/wp-content/uploads/2013/05/Arjuna1.png"));
        catalogs.add(new CatalogEntity(1,"Decongestants","These drugs unclog your stuffy nose","http://www.anytimeherbal.com/images/arjuna.gif"));
        catalogs.add(new CatalogEntity(1,"Anthipollen","These drugs unclog your stuffy nose","http://www.anytimeherbal.com/images/arjuna.gif"));
    }

    private void loadData() {
        task = new LoadAllergiesBT();
        task.execute();
    }

    private void loadAdapter(final List<CatalogEntity> list) {
        Log.d(TAG, ".loadAdapter");

        if (adapter == null)
            adapter = new CatalogAdapter(context, list);
        else
            adapter.setCatalogs(list);

        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new CatalogAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position, CatalogEntity catalogEntity) {
                //setContentView(R.layout.product_info);
                startActivity(new Intent(context, ProductCatalogMapActivity.class));

            }
        });
    }

    private class LoadAllergiesBT extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            findViewById(R.id.progress_bar).setVisibility(View.GONE);
            loadAdapter(catalogs);
        }
    }
}
