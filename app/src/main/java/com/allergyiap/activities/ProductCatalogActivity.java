package com.allergyiap.activities;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

    private SimpleCursorAdapter adapterSuggestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_catalog);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.scrollableview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        final String[] from = new String[]{"title"};
        final int[] to = new int[]{android.R.id.text1};
        adapterSuggestion = new SimpleCursorAdapter(context,
                android.R.layout.simple_list_item_1,
                null,
                from,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.v(TAG, "onCreateOptionsMenu");
        //return super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(R.string.menu_search));
        searchView.setSuggestionsAdapter(adapterSuggestion);
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                showProduct(position);
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                showProduct(position);
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()) {
                    adapter.setCatalogs(catalogs);
                } else {
                    populateAdapter(newText);
                }
                return false;
            }
        });

        return true;
    }

    private void showProduct(int position) {
        Cursor cursor = adapterSuggestion.getCursor();
        Integer in = cursor.getInt(position);
        List<CatalogEntity> newList = new ArrayList<>();

        for (CatalogEntity catalog : catalogs) {
            if (catalog.id == in.intValue()) {
                newList.add(catalog);
            }
        }

        adapter.setCatalogs(newList);
    }

    private void createArray() {
        catalogs = new ArrayList<>();
        catalogs.add(new CatalogEntity(1, "Nasal Steroids", "These are drugs you spray into your nose", "http://www.krishnaherbals.com/images/allergy-care-zoom.jpg"));
        catalogs.add(new CatalogEntity(2, "Antihistamines", "These drugs work against the chemical histamine", "http://www.alwaysayurveda.com/wp-content/uploads/2013/05/Arjuna1.png"));
        catalogs.add(new CatalogEntity(3, "Decongestants", "These drugs unclog your stuffy nose", "http://www.anytimeherbal.com/images/arjuna.gif"));
        catalogs.add(new CatalogEntity(4, "Anthipollen", "These drugs unclog your stuffy nose", "http://www.anytimeherbal.com/images/arjuna.gif"));
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

    private void populateAdapter(String query) {
        final MatrixCursor c = new MatrixCursor(new String[]{BaseColumns._ID, "title"});

        for (CatalogEntity catalog : catalogs) {

            if (catalog.title.toLowerCase().startsWith(query.toLowerCase()))
                c.addRow(new Object[]{catalog.id, catalog.title});
        }

        adapterSuggestion.changeCursor(c);
    }
}
