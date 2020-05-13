package sg.edu.np.WhackAMole;

import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int highscore = 0;
    private Button RightButton;
    private Button MiddleButton;
    private Button LeftButton;
    private TextView scoretext;
    private int Score = 0;
    private static final String TAG = "ButtonActivity";
    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 2.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The function doCheck() also decides if the user qualifies for the advance level and triggers for a dialog box to ask for user to decide.
        - The function nextLevelQuery() builds the dialog box and shows. It also triggers the nextLevel() if user selects Yes or return to normal state if user select No.
        - The function nextLevel() launches the new advanced page.
        - Feel free to modify the function to suit your program.
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RightButton = (Button) findViewById(R.id.RightButton);
        MiddleButton = (Button) findViewById(R.id.MiddleButton);
        LeftButton = (Button) findViewById(R.id.LeftButton);
        scoretext = (TextView) findViewById(R.id.scoretext);

        Log.v(TAG, "Finished Pre-Initialisation!");

    }
    @Override
    protected void onStart(){
        super.onStart();
        setNewMole();
        Log.v(TAG, "Starting GUI!");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Paused Whack-A-Mole!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Whack-A-Mole!");
        finish();
    }

    public void whenClicked(View v)
    {
        /* Checks for hit or miss and if user qualify for advanced page.
        Triggers nextLevelQuery().
         */
        switch(v.getId())
        {
            case R.id.RightButton:
                Log.v(TAG, "Right button clicked");
                break;

            case R.id.LeftButton:
                Log.v(TAG, "Left button clicked");
                break;

            case R.id.MiddleButton:
                Log.v(TAG, "Middle button clicked");
        }

        //downcast v to button
        Button button = (Button) v;
        if (checkmole((String) button.getText().toString()))
        {
            Score++;
            Log.v(TAG, "Hit, score added!");
        }
        else
        {
            Score--;
            Log.v(TAG, "Missed, score deducted!");
        }
        setNewMole();
        Updatescore();
        if (Score % 10 == 0)
        {
            nextLevelQuery();
        }
    }

    private void nextLevelQuery(){
        /*
        Builds dialog box here.
        Log.v(TAG, "User accepts!");
        Log.v(TAG, "User decline!");
        Log.v(TAG, "Advance option given to user!");
        belongs here*/
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning! Insane Whack-A-Mole incoming!");
        builder.setMessage("Would you like to advance to advanced mode?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v(TAG, "User accepts!");
                sendData();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v(TAG, "User decline!");

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void sendData()
    {
        Intent activity = new Intent(MainActivity.this, Main2Activity.class);
        Bundle score = new Bundle();
        score.putInt("score", Score);
        activity.putExtras(score);
        startActivity(activity);
    }

    private void setNewMole() {
        Random ran = new Random();
        int randomLocation = ran.nextInt(3);
        Button[] allbutton = {RightButton, MiddleButton, LeftButton};
        Button cmole = allbutton[randomLocation];
        for (Button b : allbutton)
        {
            if (b == cmole)
            {
                b.setText("*");
            }
            else
            {
                b.setText("O");
            }
        }
    }

    private boolean checkmole(String s)
    {
        if (s == "*")
        {
            return true;
        }
        return false;
    }

    private void Updatescore()
    {
        String count = String.valueOf(Score);
        scoretext.setText(count);
    }
}