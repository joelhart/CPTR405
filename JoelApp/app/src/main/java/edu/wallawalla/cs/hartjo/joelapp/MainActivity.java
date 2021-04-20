package edu.wallawalla.cs.hartjo.joelapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE_COLOR = 0;
    private int mOnColor;
    private int mOnColorId;
    private int gameState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // recovering the instance state
        if (savedInstanceState != null) {
            gameState = savedInstanceState.getInt("KEY_ONE");
            mOnColorId = savedInstanceState.getInt("KEY_TWO");
        }

//        mOnColorId = R.color.white;

        RatingBar ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // rating is 1 to numStars
                TextView text = (TextView)findViewById(R.id.textView2);
                if (rating < 4) {
                    text.setText("Rate Better Please");
                } else {
                    text.setText("Thats Better!");
                }
            }
        });

//        Button button1, button2;
//        final ConstraintLayout constraintLayout;
//
//        // set button 1 with its id
//        button1 = findViewById(R.id.btVar1);
//
//        // set button 2 with its id
//        button2 = findViewById(R.id.btVar2);
//
//        // set relative layout with its id
//        constraintLayout = findViewById(R.id.layout);
//
//        // onClick function for button 1
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // set the color to relative layout
//                constraintLayout.setBackgroundResource(R.color.teal_200);
//            }
//        });
//
//        // onClick function for button 2
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // set the color to relative layout
//                constraintLayout.setBackgroundResource(R.color.purple_200);
//            }
//        });
    }

    public void onToggleClicked(View view) {
        // Is the toggle on?
        boolean on = ((ToggleButton) view).isChecked();

        if (on) {
            // Enable
        } else {
            // Disable
        }
    }

    public void onChangeColorClick(View view) {
        Intent intent = new Intent(this, ColorActivity.class);
        intent.putExtra(ColorActivity.EXTRA_COLOR, mOnColorId);
        startActivityForResult(intent, REQUEST_CODE_COLOR);
    }

    private void setBackgroundColor() {
        final ConstraintLayout constraintLayout;
        constraintLayout = findViewById(R.id.layout);
        constraintLayout.setBackgroundResource(mOnColorId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_COLOR) {
            // Create the "on" button color based on the chosen color ID from ColorActivity
            mOnColorId = data.getIntExtra(ColorActivity.EXTRA_COLOR, R.color.white);
            mOnColor = ContextCompat.getColor(this, mOnColorId);
            setBackgroundColor();
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        final ConstraintLayout constraintLayout;
        constraintLayout = findViewById(R.id.layout);
        constraintLayout.setBackgroundResource(savedInstanceState.getInt("KEY_TWO"));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("KEY_ONE", gameState);
        outState.putInt("KEY_TWO", mOnColorId);
        super.onSaveInstanceState(outState);
    }


}