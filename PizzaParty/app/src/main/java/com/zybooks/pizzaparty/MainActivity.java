package com.zybooks.pizzaparty;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public final static int SLICES_PER_PIZZA = 8;
    private final static String TAG = "MainActivity";

    private final String KEY_TOTAL_PIZZAS = "totalPizzas";
    private int mTotalPizzas;

    private EditText mNumAttendEditText;
    private TextView mNumPizzasTextView;
    private Spinner mHowHungrySpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate was called");

        // Assign the widgets to fields
        mNumAttendEditText = findViewById(R.id.attendEditText);
        mNumPizzasTextView = findViewById(R.id.answerTextView);
        mHowHungrySpinner = findViewById(R.id.hunger_spinner);

        // Watch for changes to number attending
        mNumAttendEditText .addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                mNumPizzasTextView.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
//                mNumPizzasTextView.setText("");
            }
        });

        //Set up the hunger spinner

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.hunger_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mHowHungrySpinner.setAdapter(adapter);
        mHowHungrySpinner.setSelection(0, false);
        mHowHungrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String)parent.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, item, Toast.LENGTH_SHORT).show();
//                mNumPizzasTextView.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Restore state
        if (savedInstanceState != null) {
            mTotalPizzas = savedInstanceState.getInt(KEY_TOTAL_PIZZAS);
            displayTotal();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_TOTAL_PIZZAS, mTotalPizzas);
    }

    public void calculateClick(View view) {

        // Get how many are attending the party
        int numAttend;
        try {
            String numAttendStr = mNumAttendEditText.getText().toString();
            numAttend = Integer.parseInt(numAttendStr);
        }
        catch (NumberFormatException ex) {
            numAttend = 0;
        }

        // Get hunger level selection
//        int checkedId = mHowHungryRadioGroup.getCheckedRadioButtonId();
        PizzaCalculator.HungerLevel hungerLevel = PizzaCalculator.HungerLevel.LIGHT;
        switch (mHowHungrySpinner.getSelectedItemPosition()) {
            case 1:
                hungerLevel = PizzaCalculator.HungerLevel.MEDIUM;
                break;
            case 2:
                hungerLevel = PizzaCalculator.HungerLevel.RAVENOUS;
                break;
            default:
                hungerLevel = PizzaCalculator.HungerLevel.LIGHT;
                break;
        }
//        if (mHowHungry == R.id.hunger_spinner) {
//            hungerLevel = PizzaCalculator.HungerLevel.LIGHT;
//        }
//        else if (checkedId == R.id.hunger_spinner) {
//            hungerLevel = PizzaCalculator.HungerLevel.MEDIUM;
//        }

        // Get the number of pizzas needed
        PizzaCalculator calc = new PizzaCalculator(numAttend, hungerLevel);
        int totalPizzas = calc.getTotalPizzas();

        mTotalPizzas = calc.getTotalPizzas();
        displayTotal();
    }

    private void displayTotal() {
        String totalText = getString(R.string.total_pizzas_calc, mTotalPizzas);
        mNumPizzasTextView.setText(totalText);
    }
}