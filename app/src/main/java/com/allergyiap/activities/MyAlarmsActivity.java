package com.allergyiap.activities;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.allergyiap.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class MyAlarmsActivity extends BaseActivity {


    AlertDialog dialog;
    TimePickerDialog mTimePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_alarms);

        final Button button = (Button) findViewById(R.id.daysOfWeek);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final CharSequence[] items = {" Monday "," Tuesday "," Wednesday "," Thursday "," Friday "," Saturday "," Sunday "};
                // arraylist to keep the selected items
                final ArrayList seletedItems=new ArrayList();

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Select the days of the week");
                builder.setMultiChoiceItems(items, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            // indexSelected contains the index of item (of which checkbox checked)
                            @Override
                            public void onClick(DialogInterface dialog, int indexSelected,
                                                boolean isChecked) {
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                    // write your code when user checked the checkbox
                                    seletedItems.add(indexSelected);
                                } else if (seletedItems.contains(indexSelected)) {
                                    // Else, if the item is already in the array, remove it
                                    // write your code when user Uchecked the checkbox
                                    seletedItems.remove(Integer.valueOf(indexSelected));
                                }
                            }
                        })
                        // Set the action buttons
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            String result;
                            String myText;
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                              //  for (int i=0; i<seletedItems.size();i++) {
                               //     result += Arrays.toString(ArraysseletedItems.toArray());

                              //  }
                              //  button.setText( result);

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                //  Your code when user clicked on Cancel

                            }
                        });

                dialog = builder.create();//AlertDialog dialog; create like this outside onClick
                dialog.show();
            }
        });

        final Button button2 = (Button) findViewById(R.id.hour);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                mTimePicker = new TimePickerDialog(MyAlarmsActivity.this,R.style.AppTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        button2.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");

                mTimePicker.show();

            }
        });


    }


}
