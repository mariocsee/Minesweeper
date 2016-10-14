package mariocsee.android.minesweeper;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import mariocsee.android.minesweeper.model.MinesweeperModel;
import mariocsee.android.minesweeper.view.MinesweeperView;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = (LinearLayout) findViewById(R.id.activity_main);

        Button btnRestart = (Button) findViewById(R.id.btnRestart);
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MinesweeperModel.getInstance().cleanBoard();
                MinesweeperModel.getInstance().setMines();
                MinesweeperModel.getInstance().setMineCount();

                Snackbar restartSnackbar = Snackbar.make(linearLayout, "Game restarted", Snackbar.LENGTH_LONG);
                restartSnackbar.show();
            }
        });

        Button btnFlag = (Button) findViewById(R.id.btnFlag);
        btnFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Function that sets touches to place flags

                MinesweeperModel.getInstance().actionFlag();

                Snackbar flagSnackbar = Snackbar.make(linearLayout, "Flagging on", Snackbar.LENGTH_LONG);
                flagSnackbar.show();
            }
        });

        Button btnReveal = (Button) findViewById(R.id.btnReveal);
        btnReveal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Function that sets touches to open tiles
                MinesweeperModel.getInstance().actionReveal();

                Snackbar revealSnackbar = Snackbar.make(linearLayout, "Flagging off", Snackbar.LENGTH_LONG);
                revealSnackbar.show();
            }
        });

    }

    public void showSnackBarWithDelete(String msg) {
        Snackbar.make(linearLayout, msg,
                Snackbar.LENGTH_LONG).setAction(
                "Restart", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Restart the game
                        MinesweeperModel.getInstance().cleanBoard();
                        MinesweeperModel.getInstance().setMines();
                        MinesweeperModel.getInstance().setMineCount();
                    }
                }
        ).show();
    }
}
