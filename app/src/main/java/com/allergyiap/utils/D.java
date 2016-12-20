package com.allergyiap.utils;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.widget.TimePicker;

import com.allergyiap.R;
import com.allergyiap.activities.ConfigurationActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Created by Globalia-5 on 09/11/2016.
 */

public class D {

    public static void showAlert(final Context context) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        context.startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    /**
     * Simple Dialog
     */
    public static void showSimpleDialog(final Activity activity, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        if (title != null && !title.isEmpty())
            builder.setTitle(title);
        if (message != null && !message.isEmpty())
            builder.setMessage(message);
        builder.setPositiveButton(activity.getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void showWeekDaysDialog(final Activity activity, final String[] items, List items_selected) {

        final ArrayList selectedItems = new ArrayList();
        boolean[] selecteds = new boolean[items.length];
        if (items_selected != null) {
            for (int i = 0; i < items.length; i++) {
                if (items_selected.contains(Double.parseDouble(String.valueOf(i)))) {
                    selecteds[i] = true;
                    selectedItems.add(i);
                }
            }
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        //android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getContext(), R.style.AlertsTheme);
        builder.setTitle(R.string.dialog_week_days_title);
        builder.setMultiChoiceItems(items, selecteds,
                new DialogInterface.OnMultiChoiceClickListener() {
                    // indexSelected contains the index of item (of which checkbox checked)
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected,
                                        boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            // write your code when user checked the checkbox
                            selectedItems.add(indexSelected);
                        } else if (selectedItems.contains(indexSelected)) {
                            // Else, if the item is already in the array, remove it
                            // write your code when user Uchecked the checkbox
                            selectedItems.remove(Integer.valueOf(indexSelected));
                        }
                    }
                })
                // Set the action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    String result = "";
                    String myText;

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Collections.sort(selectedItems);
                        if (activity instanceof ConfigurationActivity) {
                            ((ConfigurationActivity) activity).setTextButtonDays(selectedItems);
                        }
                        //button.setText(result);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //  Your code when user clicked on Cancel

                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void showHourDialog(final Activity activity) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(activity, R.style.AlertsTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String result = selectedHour + ":" + String.format(Locale.getDefault(), "%02d", selectedMinute);

                if (activity instanceof ConfigurationActivity) {
                    ((ConfigurationActivity) activity).setTextButtonHour(result);
                }
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle(R.string.dialog_hour_title);
        mTimePicker.setButton(DialogInterface.BUTTON_POSITIVE, activity.getString(R.string.ok), mTimePicker);
        mTimePicker.setButton(DialogInterface.BUTTON_NEGATIVE, activity.getString(R.string.cancel), mTimePicker);
        mTimePicker.show();
    }

    public static void showAllergyDialog(final Activity activity, final String[] allergies, List<String> allergies_selected) {

        final ArrayList selectedItems = new ArrayList();
        boolean[] selecteds = new boolean[allergies.length];
        if (allergies_selected != null) {
            for (int i = 0; i < allergies.length; i++) {
                if (allergies_selected.contains(allergies[i])) {
                    selecteds[i] = true;
                    selectedItems.add(i);
                }
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        //android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getContext(), R.style.AlertsTheme);
        builder.setTitle(R.string.dialog_allergies_title);
        builder.setMultiChoiceItems(allergies, selecteds,
                new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                        if (isChecked) {
                            selectedItems.add(indexSelected);
                        } else if (selectedItems.contains(indexSelected)) {
                            selectedItems.remove(Integer.valueOf(indexSelected));
                        }
                    }
                })
                // Set the action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    List<String> result = new ArrayList();

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Collections.sort(selectedItems);

                        for (int i = 0; i < selectedItems.size(); i++) {
                            int indice = (int) selectedItems.get(i);
                            result.add(allergies[indice]);
                        }
                        if (activity instanceof ConfigurationActivity) {
                            ((ConfigurationActivity) activity).setAllergies(result);
                        }
                        //button.setText(result);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //  Your code when user clicked on Cancel

                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
