package com.allergyiap.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.allergyiap.R;
import com.allergyiap.adapters.AllergiesAdapter;
import com.allergyiap.adapters.CatalogAdapter;
import com.allergyiap.entities.AllergyEntity;
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
        catalogs.add(new CatalogEntity(1,"Abedul","0 - 40 g/m","https://s13.postimg.org/xyzjxw45z/download_1.jpg"));
        catalogs.add(new CatalogEntity(1,"Cupresaceas","0 - 50 g/m","https://s22.postimg.org/vi6o9xd29/Juniperus_chinensis6.jpg"));
        catalogs.add(new CatalogEntity(1,"Gramineas","0 - 10 g/m","https://s13.postimg.org/g3cz7nq7b/54e_Poaceae.jpg"));
        catalogs.add(new CatalogEntity(1,"Olivo","0 - 100 g/m","https://s11.postimg.org/6ndg0fosz/el_olivo_21.jpg"));
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
