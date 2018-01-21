package sunshine.sudharsan.com.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SeekBar progressSeekBar;

    TextView timerTextView;

    boolean isCounterActive;

    public void updateTimer(int secondsLeft) {
        final int minutes =  secondsLeft/ 60;
        String seconds = String.valueOf(secondsLeft - minutes * 60);


        if (seconds.equals("0")) {
            seconds = "00";
        }
        timerTextView.setText(String.valueOf(minutes) + ":" + seconds);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTimer();
    }

    public void controlTimer(final View view) {
        if (!isCounterActive) {
            Toast.makeText(this, "Button clicked", Toast.LENGTH_LONG).show();

            isCounterActive = true;
            progressSeekBar.setEnabled(false);
            final Button button = findViewById(R.id.button);
            button.setText("STOP");

            button.setVisibility(View.INVISIBLE);


            new CountDownTimer(progressSeekBar.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    Log.i("Egg Timer", Long.toString(millisUntilFinished));
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    Toast.makeText(MainActivity.this, "Timer finished", Toast.LENGTH_SHORT).show();
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();

                    isCounterActive = false;
                    progressSeekBar.setEnabled(true);
                    button.setText("GO");
                    button.setVisibility(View.VISIBLE);
                }
            }.start();
        }
    }

    private void setTimer() {

        progressSeekBar = findViewById(R.id.seekBar);
        progressSeekBar.setMax(600);
        progressSeekBar.setProgress(30);

        timerTextView = findViewById(R.id.textView);

        progressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


}
