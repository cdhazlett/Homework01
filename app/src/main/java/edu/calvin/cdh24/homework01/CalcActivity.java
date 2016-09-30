package edu.calvin.cdh24.homework01;

/*  This is homework01 app for CS262, Fall 2016.  It is a simple calculator that stores the user's
    entered values in a SharedPreferences store on pause, and retrieves them on resume.

    @author Christiaan Hazlett, cdh24
    @version 1.0
*/

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class CalcActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        // Get handles to UI elements
        final Spinner spinner = (Spinner) findViewById(R.id.opsSpinner);
        final TextView val1 = (TextView) findViewById(R.id.val1);
        final TextView val2 = (TextView) findViewById(R.id.val2);
        final TextView res_box = (TextView) findViewById(R.id.out_view);
        final Button calc_button = (Button) findViewById(R.id.calc_button);

        // Set up operation spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.operations_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        // Add event listener for calculate button hit
        calc_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the vars for the calculations
                float result;
                float value1 = Float.parseFloat(val1.getText().toString());
                float value2 = Float.parseFloat(val2.getText().toString());


                if (spinner.getSelectedItemPosition() == 0)
                {
                    // Addition
                    result = value1 + value2;
                }
                else if (spinner.getSelectedItemPosition() == 1)
                {
                    // Subtraction
                    result = value1 - value2;
                }
                else if (spinner.getSelectedItemPosition() == 2)
                {
                    // Multiplication
                    result = value1 * value2;
                }
                else
                {
                    // Division
                    result = value1 / value2;
                }

                // Set the output box to the result
                res_box.setText(Float.toString(result));
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();

        // Get handles to UI elements
        final Spinner spinner = (Spinner) findViewById(R.id.opsSpinner);
        final TextView val1 = (TextView) findViewById(R.id.val1);
        final TextView val2 = (TextView) findViewById(R.id.val2);
        final TextView res_box = (TextView) findViewById(R.id.out_view);
        final Button calc_button = (Button) findViewById(R.id.calc_button);

        // Save the preferences from the UI into the strings file
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("saved_v1", val1.getText().toString());
        editor.putString("saved_v2", val2.getText().toString());
        editor.putString("saved_op", Integer.toString(spinner.getSelectedItemPosition()));
        editor.commit();
    }

    @Override
    public void onResume() {
        super.onResume();

        // Get handles to UI elements
        final Spinner spinner = (Spinner) findViewById(R.id.opsSpinner);
        final TextView val1 = (TextView) findViewById(R.id.val1);
        final TextView val2 = (TextView) findViewById(R.id.val2);
        final TextView res_box = (TextView) findViewById(R.id.out_view);
        final Button calc_button = (Button) findViewById(R.id.calc_button);

        // Get the preferences from the strings file and put them back into the UI
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        val1.setText(sharedPref.getString("saved_v1", "0"));
        val2.setText(sharedPref.getString("saved_v2", "0"));
        spinner.setSelection(Integer.parseInt(sharedPref.getString("saved_op", "0")));
    }
}
