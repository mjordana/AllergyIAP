package com.allergyiap.activities;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allergyiap.R;
import com.allergyiap.adapters.CatalogAdapter;
import com.allergyiap.entities.CatalogEntity;
import com.allergyiap.entities.ProductCatalogEntity;
import com.allergyiap.services.ProductCatalogProxyClass;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment {

    static final String TAG = "ProductsFragment";

    MainActivity activity;
    Context context;
    RecyclerView recyclerView;
    CatalogAdapter adapter;

    List<ProductCatalogEntity> catalogs = new ArrayList<>();
    private AsyncTask<Void, Void, List<ProductCatalogEntity>> task;

    private int defaultImage = R.drawable.logo;

    public ProductsFragment() {

    }

    // after Activity.onCreate
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v(TAG + ".onCreate", ".");
        super.onCreate(savedInstanceState);

        activity = (MainActivity) getActivity();
        context = getActivity().getApplicationContext();

        activity.updateLocale();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v(TAG + ".onCreateView", ".");
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);
        view.setEnabled(false); /** disable swipe to reload data in view **/

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.v(TAG + ".onViewCreated", ".");
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.scrollableview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        loadData();
    }

    // after Activity.onStart

    @Override
    public void onStart() {
        Log.v(TAG + ".onStart", ".");
        super.onStart();
    }

    // after Activity.onResume
    @Override
    public void onResume() {
        Log.v(TAG + ".onResume", ".");
        super.onResume();
    }

    // after Activity.onPause

    @Override
    public void onPause() {
        Log.v(TAG + ".onPause", ".");
        super.onPause();
    }

    // after Activity.onStop

    @Override
    public void onStop() {
        Log.v(TAG + ".onStop", ".");
        super.onStop();
    }

    // after Activity.onDestroy

    @Override
    public void onDestroyView() {
        Log.v(TAG + "onDestroyView", ".");
        super.onDestroyView();

        /*if (task_init != null) {
            task_init.cancel(true);
            task_init = null;
        }*/
        adapter = null;
    }

    @Override
    public void onDestroy() {
        Log.v(TAG + ".onDestroy", ".");
        super.onDestroy();
    }

    private void loadData() {

        task = new LoadProductsBT();
        task.execute();
    }

    private void loadAdapter(final List<ProductCatalogEntity> list) {
        Log.d(TAG, ".loadAdapter");

        if (adapter == null)
            adapter = new CatalogAdapter(context, list);
        else
            adapter.setCatalogs(list);

        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new CatalogAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position, ProductCatalogEntity catalogEntity) {
                //setContentView(R.layout.product_info);
                startActivity(new Intent(context, ProductCatalogMapActivity.class));

            }
        });
    }

    private class LoadProductsBT extends AsyncTask<Void, Void, List<ProductCatalogEntity>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        }

        @Override
        protected List<ProductCatalogEntity> doInBackground(Void... params) {

            List<ProductCatalogEntity> prods = new ArrayList<>();
            try {

                prods = ProductCatalogProxyClass.getProductCatalog();
            } catch (Exception e) {
                Log.e(TAG, "LoadAllergiesBT", e);
            }
            return prods;
        }

        @Override
        protected void onPostExecute(List<ProductCatalogEntity> result) {
            super.onPostExecute(result);

            activity.findViewById(R.id.progress_bar).setVisibility(View.GONE);

            if(result != null && !result.isEmpty())
                catalogs = result;
            loadAdapter(catalogs);
        }
    }
}
