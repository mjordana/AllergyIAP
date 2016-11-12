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
import com.allergyiap.utils.C;

import java.util.ArrayList;
import java.util.List;

public class MapAllergyLevelsActivity extends BaseActivity {

    static final String TAG = "MapAllergyLevels";

    private AllergiesAdapter adapter;
    private RecyclerView recyclerView;
    private AsyncTask<Void, Void, Void> task;
    List<AllergyEntity> allergy = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_allergy_levels);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.scrollableview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        Integer id = getIntent().getIntExtra(C.IntentExtra.Sender.VAR_ALLERGY, 1);

        createArray(id);
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
        allergy = new ArrayList<>();
        allergy.add(new AllergyEntity(0, "Urticaceae", 1, "Stable"));
        allergy.add(new AllergyEntity(1, "Gramineae (Poaceae)", 1, "Stable"));
        allergy.add(new AllergyEntity(2, "Olea", 0, "Stable"));
        allergy.add(new AllergyEntity(3, "Artemisia", 2, "Increase"));
        allergy.add(new AllergyEntity(4, "Chenopodiaceae/Amaranthaceae", 1, "Stable"));
        allergy.add(new AllergyEntity(5, "Erica", 1, "Stable"));
        allergy.add(new AllergyEntity(6, "Casuarina", 0, "Stable"));
        allergy.add(new AllergyEntity(7, "Compositae (Asteraceae)", 2, "Increase"));
        allergy.add(new AllergyEntity(8, "Cruciferae (Brassicaceae)", 1, "Stable"));
        allergy.add(new AllergyEntity(9, "Mercurialis", 0, "Stable"));
        allergy.add(new AllergyEntity(10, "Palmae (Arecaceae)", 0, "Stable"));
        allergy.add(new AllergyEntity(11, "Cupressaceae", 1, "Increase"));
        allergy.add(new AllergyEntity(12, "Alternaria", 4, "Stable"));
        allergy.add(new AllergyEntity(13, "Cladosporium", 4, "Stable"));

        if (station == 2) {
            //Maresa
            allergy.get(0).risk = 2;
            allergy.get(0).preview = "Stable";
            allergy.get(1).risk = 1;
            allergy.get(1).preview = "Stable";
            allergy.get(2).risk = 0;
            allergy.get(2).preview = "Stable";
            allergy.get(3).risk = 1;
            allergy.get(3).preview = "Stable";
            allergy.get(4).risk = 1;
            allergy.get(4).preview = "Stable";
            allergy.get(5).risk = 1;
            allergy.get(5).preview = "Stable";
            allergy.get(6).risk = 0;
            allergy.get(6).preview = "Stable";
            allergy.get(7).risk = 1;
            allergy.get(7).preview = "Stable";
            allergy.get(8).risk = 1;
            allergy.get(8).preview = "Stable";
            allergy.get(9).risk = 1;
            allergy.get(9).preview = "Stable";
            allergy.get(10).risk = 0;
            allergy.get(10).preview = "Stable";
            allergy.get(11).risk = 2;
            allergy.get(11).preview = "Increase";
            allergy.get(12).risk = 4;
            allergy.get(12).preview = "Stable";
            allergy.get(13).risk = 4;
            allergy.get(13).preview = "Stable";

        } else if (station == 3) {
            //Barcelona
            allergy.get(0).risk = 1;
            allergy.get(0).preview = "Stable";
            allergy.get(1).risk = 1;
            allergy.get(0).preview = "Stable";
            allergy.get(2).risk = 0;
            allergy.get(0).preview = "Stable";
            allergy.get(3).risk = 1;
            allergy.get(0).preview = "Stable";
            allergy.get(4).risk = 1;
            allergy.get(0).preview = "Stable";
            allergy.get(5).risk = 0;
            allergy.get(0).preview = "Stable";
            allergy.get(6).risk = 1;
            allergy.get(0).preview = "Stable";
            allergy.get(7).risk = 1;
            allergy.get(0).preview = "Stable";
            allergy.get(8).risk = 1;
            allergy.get(0).preview = "Stable";
            allergy.get(9).risk = 1;
            allergy.get(0).preview = "Stable";
            allergy.get(10).risk = 1;
            allergy.get(0).preview = "Stable";
            allergy.get(11).risk = 2;
            allergy.get(0).preview = "Stable";
            allergy.get(12).risk = 4;
            allergy.get(0).preview = "Stable";
            allergy.get(13).risk = 4;
            allergy.get(0).preview = "Stable";
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

    private void loadAdapter(final List<AllergyEntity> list) {
        Log.d(TAG, ".loadAdapter");

        if (adapter == null)
            adapter = new AllergiesAdapter(context, list);
        else
            adapter.setAllergies(list);

        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new AllergiesAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position, AllergyEntity alertEntity) {
                showAllergyDetail(alertEntity);
            }
        });
    }

    private void showAllergyDetail(AllergyEntity alertEntity) {
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
