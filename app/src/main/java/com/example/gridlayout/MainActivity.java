package com.example.gridlayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

    private boolean isFlag = false;

    public void onClickTool(View view) {
        TextView toolTextView = (TextView) view;

        if (isFlag) {
            // Switch to the pick tool
            toolTextView.setText(R.string.pick);
        } else {
            // Switch to the flag tool
            toolTextView.setText(R.string.flag);
        }
        isFlag = !isFlag;
    }


    public void onClickCellTextView(View view) {

        TextView clickedTextView = (TextView) view;
        int row = cell_tvs.indexOf(clickedTextView) / Game.COLUMN_COUNT;
        int col = cell_tvs.indexOf(clickedTextView) % Game.COLUMN_COUNT;
        int backgroundColor = ((ColorDrawable) clickedTextView.getBackground()).getColor();
        boolean containsFlag = clickedTextView.getText().toString().equals("🚩");


        if(isFlag && backgroundColor != COLOR_VISIBLE) {

            if (containsFlag) {
                clickedTextView.setText("");
            } else {
                clickedTextView.setText("🚩");
            }
        }
        else {
            if (!game.isMineAt(row, col)) {
                //If cell doesn't contain mine then mine is revealed
                clickedTextView.setBackgroundColor(COLOR_VISIBLE);
            } else {
                //For now, if cell contains mine then print game over
                System.out.println("Game over");
                revealAllMines();
            }
        }
    }

    private void revealAllMines() {
        for (TextView cell : cell_tvs) {
            int row = cell_tvs.indexOf(cell) / Game.COLUMN_COUNT;
            int col = cell_tvs.indexOf(cell) % Game.COLUMN_COUNT;

            if (game.isMineAt(row, col)) {
                // If the cell contains a mine, reveal it
                cell.setBackgroundColor(COLOR_VISIBLE);
                cell.setText("💣");
            }
        }
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