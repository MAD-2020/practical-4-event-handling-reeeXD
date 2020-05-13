package sg.edu.np.WhackAMole;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import static java.lang.String.format;

public class Main2Activity extends AppCompatActivity {
    private static final String TAG = "Whack My Mole! 2.0";
    private TextView scoretext;
    private int Count = 0;
    private int Score = 0;
    private int previousScore = 0;
    private Button setButton;
    CountDownTimer countDown;
    private final Handler mhandler = new Handler();
    private final Runnable mrunnable = new Runnable() {
        @Override
        public void run() {
            Log.v(TAG, "New Mole Set");
            setNewMole();
            mhandler.postDelayed(this, 1000);
        }
    };

    private void readyTimer(){

        countDown = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                final Toast countDownToast = Toast.makeText(getApplicationContext(), format("Get Ready in %d seconds",millisUntilFinished/1000), Toast.LENGTH_SHORT);
                countDownToast.show();
                Handler timerHandler = new Handler();
                timerHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        countDownToast.cancel();
                    }
                }, 1000);
            }

            @Override
            public void onFinish() {
                setNewMole();
                placeMoleTimer();
                Toast.makeText(getApplicationContext(), "Go!", Toast.LENGTH_SHORT).show();
            }
        };
        countDown.start();
    }
    private void placeMoleTimer(){
        mhandler.postDelayed(mrunnable, 1000);
    }

    private static final int[] BUTTON_IDS = {R.id.button_1, R.id.button_2, R.id.button_3,
            R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9};
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //Log.v(TAG, "Current User Score: " + String.valueOf(advancedScore));
        Bundle b = getIntent().getExtras();
        previousScore = b.getInt("score");
        scoretext = findViewById(R.id.scoretext2);
        scoretext.setText(String.valueOf(previousScore));
        Score = previousScore;
        readyTimer();

        for(final int id : BUTTON_IDS){
            setButton = findViewById(id);
            setButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int clickedId = view.getId();
                    Button clickedButton = (Button)findViewById(clickedId);
                    doCheck(clickedButton);
                    Updatescore();
                    mhandler.removeCallbacks(mrunnable);
                    setNewMole();
                    placeMoleTimer();
                }
            });
        }
    }
    @Override
    protected void onStart(){
        super.onStart();
    }

    private void doCheck(Button checkButton)
    {
        if (checkButton.getText() == "*"){
            Score++;
            Log.v(TAG, "Hit, score added!");
        }
        else{
            Score--;
            Log.v(TAG, "Missed, score deducted!");
        }
    }

    public void setNewMole()
    {
        Random ran = new Random();
        int randomLocation = ran.nextInt(9);
        Button new_button = (Button)findViewById(BUTTON_IDS[randomLocation]);
        for (int id : BUTTON_IDS)
        {
            Button previous_button = (Button)findViewById(id);
            if (previous_button.getText().toString() == "*"){
                previous_button.setText("O");
            }
        }
        new_button.setText("*");
    }
    private void Updatescore()
    {
        String count = String.valueOf(Score);
        scoretext.setText(count);
    }
}

