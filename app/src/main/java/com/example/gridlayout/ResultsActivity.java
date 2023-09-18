package com.example.gridlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_page);

        TextView resultMessage = findViewById(R.id.result_message);
        Button startGameButton = findViewById(R.id.start_game_button);

       //Game result retrieved
        boolean gameResult = getIntent().getBooleanExtra("game_result", false);

        // Placeholders until clock is set
        if (gameResult) {
            resultMessage.setText("Congratulations! You won!");

        } else {
            resultMessage.setText("Sorry, you lost.");

        }
        //Set up button
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                finish();
            }
        });
    }
}
