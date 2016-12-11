package com.allergyiap.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.allergyiap.R;
import com.allergyiap.adapters.AllergiesAdapter;
import com.allergyiap.entities.AllergyEntity;
import com.allergyiap.entities.AllergyLevelEntity;
import com.allergyiap.entities.StationEntity;
import com.allergyiap.services.AllergyLevelProxyClass;
import com.allergyiap.utils.C;

import java.util.ArrayList;
import java.util.List;

public class MapAllergyLevelsActivity extends BaseActivity {

    static final String TAG = "MapAllergyLevels";

    private AllergiesAdapter adapter;
    private RecyclerView recyclerView;
    private AsyncTask<Void, Void, Void> task;

    List<AllergyLevelEntity> allergy;
    //List<AllergyEntity> allergy = new ArrayList<>();
    StationEntity station;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_allergy_levels);

        //Integer id = getIntent().getIntExtra(C.IntentExtra.Sender.VAR_ALLERGY, 1);
        station = (StationEntity)getIntent().getSerializableExtra(C.IntentExtra.Sender.VAR_ALLERGY);

        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle(station.name);

        recyclerView = (RecyclerView) findViewById(R.id.scrollableview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);


        createArray(station.id);
        loadData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createArray(Integer station) {
        try {
            allergy = AllergyLevelProxyClass.getLevels(station);
        } catch (Exception e) {
            allergy = new ArrayList<>();
            Log.e(TAG, "createArray:", e);
        }
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

    private void loadData() {
        task = new LoadAllergiesBT();
        task.execute();
    }

    private void loadAdapter(final List<AllergyLevelEntity> list) {
        Log.d(TAG, ".loadAdapter");

        if (adapter == null)
            adapter = new AllergiesAdapter(context, list);
        else
            adapter.setAllergies(list);

        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new AllergiesAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position, AllergyLevelEntity alertEntity) {
                showAllergyDetail(alertEntity);
            }
        });
    }

    private void showAllergyDetail(AllergyLevelEntity alertEntity) {
        Intent intent = new Intent(this, MapAllergyLevelsDetailsActivity.class);
        Bundle b = new Bundle();
        b.putSerializable(C.IntentExtra.Sender.VAR_ALLERGY2, alertEntity);
        intent.putExtras(b);
        startActivity(intent);
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
            loadAdapter(allergy);
        }
    }
}
