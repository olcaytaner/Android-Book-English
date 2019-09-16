package com.mobile.pyramid;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import java.util.Random;

public class Pyramid extends Activity {
    private Puzzle[] puzzles;
    private Screen screen;

    protected void onCreate(Bundle savedInstanceState) {
        Random random = new Random();
        String[] puzzleInformation = {"443252145336141522663", "234524435626143614625",
                "161524246313452326215", "355424315665631243245", "653634542621351325265",
                "543236612135654465432", "445385279314268683141725276595138379834941283",
                "345342468929768161215485464767167583529398619",
                "233154981265474421359918793858356165737438971",
                "134925548326192374564412375353491475182356918"};
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        puzzles = new Puzzle[puzzleInformation.length];
        for (int i = 0; i < puzzleInformation.length; i++){
            puzzles[i] = new Puzzle(puzzleInformation[i]);
        }
        screen = (Screen) findViewById(R.id.screen);
        screen.puzzle = puzzles[random.nextInt(puzzleInformation.length)];
        screen.cellWidth = displayMetrics.widthPixels / (screen.puzzle.puzzleSize() + 2);
    }

}
