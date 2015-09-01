package chess2.Activities;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.botna.chess2.R;

import chess2.Views.SimulationView;
import chess2.source.GameBoard;

public class SimulateActivity
        extends ActionBarActivity {
    private View myView = null;
    GameBoard myGB = null;
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        myGB = (GameBoard) extras.getSerializable("GAME");
        myGB.clearHighlight();
        myGB.clearSelected();
        myGB.setPlayable(true);


        myView = new SimulationView(this);



        myView.setBackgroundColor(-1);



        ((SimulationView)myView).setMyName(extras.getString("NAME"));
        ((SimulationView)myView).setMyGame(myGB);
        //myView.setBackgroundColor(0xFF00A2E8);
       // Bitmap background  =  BitmapFactory.decodeResource(getResources(), R.drawable.background);
        myView.setBackgroundResource(R.drawable.background);
        setContentView(myView);



    }


}