package edu.wallawalla.cs.hartjo.joelapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
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
    private int mInitX;
    private TextView textV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hideSecretMessage();

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

//        setContentView(R.layout.activity_main);
//        fullView = (View)findViewById(R.layout.activity_main);
        textV = (TextView) findViewById(R.id.secretMessage);

        // Move finger left or right to change dice number
        textV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                TextView secretText = (TextView) findViewById(R.id.secretMessage);
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        mInitX = (int) event.getX();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        int x = (int) event.getX();

                        // See if movement is at least 20 pixels
                        if (Math.abs(x - mInitX) >= 20) {
                            if (x > mInitX) {
                                secretText.setText("You Win... For Now         Swipe Left to Revert");
                            }
                            else {
                               secretText.setText("You have found our secret, swipe to continue");
                            }
//                            showDice();
                            mInitX = x;
                        }

                        return true;
                }
                return false;
            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
//
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // Determine which menu option was chosen
//        if (item.getItemId() == R.id.action_add) {
//            // Add selected
//            return true;
//        }
//        else if (item.getItemId() == R.id.action_logout) {
//            // Logout selected
//            return true;
//        }
//        else if (item.getItemId() == R.id.action_about) {
//            // About selected
//            return true;
//        }

            //call dialogue menu here
        Dialog testAlert = testFunction();
        testAlert.show();


//        return super.onOptionsItemSelected(item);
        return true;
    }

    public Dialog testFunction() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this); // could be  getActivity()
        builder.setTitle("Are you sure?");
        builder.setItems(R.array.length_array, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                showSecretMessage();
            }
        });
        return builder.create();
    }

    public void showSecretMessage() {
        TextView secretText = (TextView) findViewById(R.id.secretMessage);
        secretText.setVisibility(View.VISIBLE);
    }

    public void hideSecretMessage() {
        TextView secretText = (TextView) findViewById(R.id.secretMessage);
        secretText.setVisibility(View.GONE);
    }
}