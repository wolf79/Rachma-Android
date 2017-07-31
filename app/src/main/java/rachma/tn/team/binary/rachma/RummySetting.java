package rachma.tn.team.binary.rachma;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//Author Marwen Doukh

public class RummySetting extends AppCompatActivity {

    Integer playersNumber = 2;
    TextView playersNumberTV;
    Button increasePlayersNumber, decreasePlayersNumber, done;
    EditText player1Name, player2Name, player3Name, player4Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rummy_setting);

        // players names
        player1Name = (EditText) findViewById(R.id.player1Name);
        player2Name = (EditText) findViewById(R.id.player2Name);
        player3Name = (EditText) findViewById(R.id.player3Name);
        player4Name = (EditText) findViewById(R.id.player4Name);


        done = (Button) findViewById(R.id.done);


        // Increase/Decrease players number
        playersNumberTV = (TextView) findViewById(R.id.playersNumber);
        increasePlayersNumber = (Button) findViewById(R.id.increasePlayersNumber);
        decreasePlayersNumber = (Button) findViewById(R.id.decreasePlayersNumber);

        increasePlayersNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playersNumber < 4)
                    playersNumber++;
                playersNumberTV.setText(playersNumber.toString());
                updateTextViewsVisibility();

            }
        });

        decreasePlayersNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playersNumber > 2)
                    playersNumber--;
                playersNumberTV.setText(playersNumber.toString());
                updateTextViewsVisibility();

            }
        });


        // move to Rummy scoreboard
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent rummyScoreboard = new Intent(getApplicationContext(), RummyScoreboard.class);
                rummyScoreboard.putExtra("playersNumber", playersNumber);

                switch (playersNumber) {

                    case 2:
                        rummyScoreboard.putExtra("player1Name", player1Name.getText());
                        rummyScoreboard.putExtra("player2Name", player2Name.getText());
                        break;
                    case 3:
                        rummyScoreboard.putExtra("player1Name", player1Name.getText());
                        rummyScoreboard.putExtra("player2Name", player2Name.getText());
                        rummyScoreboard.putExtra("player3Name", player3Name.getText());
                        break;
                    case 4:
                        rummyScoreboard.putExtra("player1Name", player1Name.getText());
                        rummyScoreboard.putExtra("player2Name", player2Name.getText());
                        rummyScoreboard.putExtra("player3Name", player3Name.getText());
                        rummyScoreboard.putExtra("player4Name", player4Name.getText());
                        break;

                }

                startActivity(rummyScoreboard);

            }

        });



    }


    // update TextViews visibility according to players number chosen
    void updateTextViewsVisibility() {
        switch (playersNumber) {
            case 2:
                player1Name.setVisibility(View.VISIBLE);
                player2Name.setVisibility(View.VISIBLE);
                player3Name.setVisibility(View.INVISIBLE);
                player4Name.setVisibility(View.INVISIBLE);
                break;
            case 3:
                player1Name.setVisibility(View.VISIBLE);
                player2Name.setVisibility(View.VISIBLE);
                player3Name.setVisibility(View.VISIBLE);
                player4Name.setVisibility(View.INVISIBLE);
                break;
            case 4:
                player1Name.setVisibility(View.VISIBLE);
                player2Name.setVisibility(View.VISIBLE);
                player3Name.setVisibility(View.VISIBLE);
                player4Name.setVisibility(View.VISIBLE);
                break;
        }
    }
}