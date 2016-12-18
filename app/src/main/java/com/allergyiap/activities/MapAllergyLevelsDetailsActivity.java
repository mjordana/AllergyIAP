package com.allergyiap.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.allergyiap.R;
import com.allergyiap.entities.AllergyEntity;
import com.allergyiap.entities.AllergyLevelEntity;
import com.allergyiap.utils.C;

public class MapAllergyLevelsDetailsActivity extends BaseActivity {

    private AllergyLevelEntity allergy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_allergy_levels_details);

        allergy = (AllergyLevelEntity) getIntent().getSerializableExtra(C.IntentExtra.Sender.VAR_ALLERGY2);

        getSupportActionBar().setTitle(allergy.allergy_name);
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
}
