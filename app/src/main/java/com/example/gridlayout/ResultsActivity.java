package com.example.gridlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_page);

        TextView resultMessage = findViewById(R.id.result_message);
        Button startGameButton = findViewById(R.id.start_game_button);

       //Game result retrieved
        boolean gameResult = getIntent().getBooleanExtra("game_result", false);
       //Time received
       int clockTime = getIntent().getIntExtra("clockTime", 0);

        // Clock
        if (gameResult) {
            resultMessage.setText("Used " + clockTime + " seconds.\n" + "       You won.\n" + "      Good job!");

        } else {
            resultMessage.setText("Used " + clockTime + " seconds.\n" + "      You lost.\n" + "        Sorry!");

        }
        //Set up button
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                Intent intent = new Intent(ResultsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
