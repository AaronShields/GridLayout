package com.example.gridlayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
//import java.util.logging.Handler;


public class MainActivity extends AppCompatActivity {

    private static final int COLOR_VISIBLE = Color.parseColor("lightgrey");
    private static final int COLOR_INVISIBLE = Color.rgb(102, 204, 0);

    private ArrayList<TextView> cell_tvs;
    private TextView activity_main_secondsUsed;
    private Game game;

    private int clock = 0;
    private boolean clock_running = false;
    private int tool = R.string.pick;
    private Handler clockHandler = new Handler();
    private Runnable clockRunnable;
    /*
    private int getRowFromTextView(TextView textView) {
        // Get the layout parameters associated with the TextView
        ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();

        if (layoutParams instanceof GridLayout.LayoutParams) {
            // If the layout parameters are of type GridLayout.LayoutParams
            GridLayout.LayoutParams gridLayoutParams = (GridLayout.LayoutParams) layoutParams;
            return gridLayoutParams.rowSpec.span;
        } else {
            // Handle the case where the layout parameters are not GridLayout.LayoutParams
            // You can provide a default value or handle this according to your layout structure.
            return -1; // Default value or appropriate handling
        }
    }

    private int getColumnFromTextView(TextView textView) {
        // Get the layout parameters associated with the TextView
        ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();

        if (layoutParams instanceof GridLayout.LayoutParams) {
            // If the layout parameters are of type GridLayout.LayoutParams
            GridLayout.LayoutParams gridLayoutParams = (GridLayout.LayoutParams) layoutParams;
            return gridLayoutParams.columnSpec.span;
        } else {
            // Handle the case where the layout parameters are not GridLayout.LayoutParams
            // You can provide a default value or handle this according to your layout structure.
            return -1; // Default value or appropriate handling
        }
    }
    */


    public void onClickCellTextView(View view) {
        TextView clickedTextView = (TextView) view;

        /*int row = getRowFromTextView(clickedTextView);
        int col = getColumnFromTextView(clickedTextView);

        // Check if the clicked cell contains a mine
        if (!game.isMineAt(row, col)) {
            // Cell does not contain a mine, so reveal it (change background color to grey)
            clickedTextView.setBackgroundColor(COLOR_VISIBLE);
        } else {
            // Cell contains a mine, handle the game over logic (you can implement this)
            //handleGameOver();
            System.out.println("Game over");
        }
        // Add your additional game logic here
         */
    }
    private void runTimer() {
            clock_running = true;
            clockRunnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        clock++;
                        activity_main_secondsUsed.setText(String.format("%02d", clock));
                        clockHandler.postDelayed(this, 1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            clockHandler.postDelayed(clockRunnable, 1000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new Game();

        cell_tvs = new ArrayList<>();
        //create the cell's textViews in the gridLayout
        GridLayout gridLayout = (GridLayout) findViewById(R.id.activity_main_gridLayout);
        LayoutInflater li = LayoutInflater.from(this);
        for (int i = 0; i < Game.ROW_COUNT; i++) {
            for (int j = 0; j < Game.COLUMN_COUNT; j++) {
                TextView tv = (TextView) li.inflate(R.layout.custom_cell_layout, gridLayout, false);

                tv.setText("");
                tv.setBackgroundColor(COLOR_INVISIBLE);
                tv.setOnClickListener(this::onClickCellTextView);

                GridLayout.LayoutParams lp = (GridLayout.LayoutParams) tv.getLayoutParams();
                lp.rowSpec = GridLayout.spec(i);
                lp.columnSpec = GridLayout.spec(j);
                gridLayout.addView(tv, lp);

                cell_tvs.add(tv);


            }
        }

        TextView tv_minesLeft = (TextView) findViewById(R.id.activity_main_minesLeft);
        tv_minesLeft.setText(String.valueOf(game.MINE_COUNT));
        activity_main_secondsUsed = findViewById(R.id.activity_main_secondsUsed);

        clock = 0;
        clock_running = true;
        runTimer();
    }

}