package chess2.Adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.botna.chess2.R;
import com.idunnololz.widgets.AnimatedExpandableListView;

import chess2.Activities.CampaignActivity;
import chess2.source.GameContainer;

/**
 * Created by Botna on 5/29/2015.
 */
public class ExpandableGameListAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {

    private final static String myTurn = "MYTURN";
    private final static String theirTurn = "THEIRTURN";
    private final static String loss = "LOSS";
    private final static String win = "WIN";

    private Activity activity;
    private ArrayList<Object> childtems;
    private LayoutInflater inflater;
    private ArrayList<String> parentItems, child;
    private GameContainer[] myGames;
    private GameList[] gameList;
    private CampaignActivity parent;


    public ExpandableGameListAdapter(Context context)
    {
        inflater = LayoutInflater.from(context);

    }


    public ExpandableGameListAdapter(GameContainer[] myGames, String myName,  CampaignActivity parent)
    {
        this.myGames = myGames;
        this.parent = parent;

        String theirName;
        char theirVar;
        char myVar;
        String state;
        SingleGame tempGame;
        boolean found = false;
        if(myGames != null) {
            for (int i = 0; i < myGames.length; i++) {

                if(myGames[i].getBlackTeam().equals(myName)) {
                    theirName = myGames[i].getWhiteTeam();
                    myVar = myGames[i].getBlackVar();
                    theirVar = myGames[i].getWhiteVar();
                }
                else {
                    theirName = myGames[i].getBlackTeam();
                    theirVar = myGames[i].getBlackVar();
                    myVar = myGames[i].getWhiteVar();
                }


                if(myGames[i].getBlackVar() < 65 || myGames[i].getBlackVar() > 90) {
                    state = theirTurn;
                }
                else if(myGames[i].getWinner() == null) {
                    if (myGames[i].getTurn().equals(myName)) {
                        if (myGames[i].isPromptOffense())
                            state = theirTurn;
                        else
                            state = myTurn;
                    } else {
                        if (myGames[i].isPromptOffense())
                            state = myTurn;
                        else
                            state = theirTurn;
                    }
                }
                else
                {
                    if(myGames[i].getWinner().equals(myName))
                        state = win;
                    else
                        state=loss;
                }
                if(gameList == null) {

                    gameList = new GameList[1];
                    tempGame = new SingleGame(myVar, theirVar, myGames[i].getGuid(), state);
                    gameList[0] = new GameList(theirName, tempGame);
                    if(state.equals(myTurn))
                    {
                        //update the gamelist header to suggest its my turn on one or more games.
                        gameList[0].setMyTurn(true);
                    }
                }
                else {
                    found = false;
                    for (int j = 0; j < gameList.length; j++) {
                        //figure out who this is.
                        if (gameList[j].getOpponent().equals(theirName)) //this denotes the first string in the list, which correlates to the opponent.
                        {
                            //they already have an entry in our list, lets add on to it.
                            tempGame = new SingleGame(myVar, theirVar, myGames[i].getGuid(), state);
                            gameList[j].addGame(tempGame);
                            found = true;

                            if(state.equals(myTurn))
                            {
                                //update the gamelist header to suggest its my turn on one or more games.
                                gameList[j].setMyTurn(true);
                            }
                        }
                    }
                    if (!found) {
                        //expand GameList and add this new record.
                        tempGame = new SingleGame(myVar, theirVar, myGames[i].getGuid(), state);
                        GameList[] tempList = new GameList[gameList.length+1];

                        for (int j = 0; j < gameList.length; j++) {
                            tempList[j] = gameList[j];
                        }
                        tempList[gameList.length] = new GameList(theirName, tempGame);
                        if(state.equals(myTurn))
                        {
                            //update the gamelist header to suggest its my turn on one or more games.
                            tempList[gameList.length].setMyTurn(true);
                        }

                        gameList = tempList;

                    }
                }
            }
        }
    }

    public void setInflater(LayoutInflater inflater, Activity activity) {
        this.inflater = inflater;
        this.activity = activity;
    }


    @Override
    public View getRealChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.expandablegroup, null);
        }
        TextView textView = null;
        String myVariant="";
        String theirVariant="";

        SingleGame tempGame = gameList[groupPosition].getGames()[childPosition];

        switch ( tempGame.getMyVariant())
        {
            case 'C':
                myVariant = "Classic";
                break;
            case 'R':
                myVariant = "Reapers";
                break;
            case 'N':
                myVariant ="Nemesis";
                break;
            case 'E':
                myVariant ="Empowered";
                break;
            case 'A':
                myVariant = "Animals";
                break;
            case 'T':
                myVariant = "Two-Kings";
                break;
        }
        switch ( tempGame.getTheirVariant())
        {
            case 'C':
                theirVariant = "Classic";
                break;
            case 'R':
                theirVariant = "Reapers";
                break;
            case 'N':
                theirVariant ="Nemesis";
                break;
            case 'E':
                theirVariant ="Empowered";
                break;
            case 'A':
                theirVariant = "Animals";
                break;
            case 'T':
                theirVariant = "Two-Kings";
                break;
        }


        textView = (TextView) convertView.findViewById(R.id.myVariant);
        textView.setText(myVariant);


        ImageView image = null;
        image = (ImageView) convertView.findViewById(R.id.whoseTurn);




        switch(tempGame.getState())
        {
            case myTurn:
                image.setImageResource(R.drawable.greenarrow);
                break;

            case theirTurn:
                image.setImageResource(R.drawable.redarrow);
                break;

            case win:
                image.setImageResource(R.drawable.victory);
                break;
            case loss:
                image.setImageResource(R.drawable.skull);
                break;


        }



        if(!myVariant.equals("")) {
            textView = (TextView) convertView.findViewById(R.id.theirVariant);
            textView.setText(theirVariant);
        }
        else
        {
            textView = (TextView) convertView.findViewById(R.id.theirVariant);
            textView.setText("????");
        }

        final CampaignActivity parentClass = this.parent;
        convertView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {

                parentClass.startGame(gameList[groupPosition].getGames()[childPosition].getGuid());
            }
        });

        return convertView;
    }



    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.expandablerow, null);
        }


        TextView textView = null;

        textView = (TextView) convertView.findViewById(R.id.opponentName);
        textView.setText(gameList[groupPosition].getOpponent());


        //do something to determine what picture to give to parent

        if(gameList[groupPosition].isMyTurn()) {
            ImageView image = null;
            image = (ImageView) convertView.findViewById(R.id.notifyMove);

            image.setImageResource(R.drawable.notification);
        }
        else
        {
            ImageView image = null;
            image = (ImageView) convertView.findViewById(R.id.notifyMove);

            image.setImageResource(0);
        }
        // ImageButton button = (ImageButton) convertView.findViewById(R.id.create_game);
        //button.setFocusable(false);

        return convertView;
    }

//    @Override
//    public Object getChild(int groupPosition, int childPosition) {
//        return null;
//    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return gameList[groupPosition].getGames()[childPosition];
    }


//    @Override
//    public long getChildId(int groupPosition, int childPosition) {
//        return 0;
//    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    @Override
    public int getRealChildrenCount(int groupPosition) {
        return (gameList[groupPosition].getGames().length);
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return gameList.length;
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void addItems(GameContainer[] myGames, String myName, CampaignActivity parent) {

        this.myGames = myGames;
        this.parent = parent;

        String theirName;
        char theirVar;
        char myVar;
        String state;
        SingleGame tempGame;
        boolean found = false;
        if(myGames != null) {
            for (int i = 0; i < myGames.length; i++) {

                if(myGames[i].getBlackTeam().equals(myName)) {
                    theirName = myGames[i].getWhiteTeam();
                    myVar = myGames[i].getBlackVar();
                    theirVar = myGames[i].getWhiteVar();
                }
                else {
                    theirName = myGames[i].getBlackTeam();
                    theirVar = myGames[i].getBlackVar();
                    myVar = myGames[i].getWhiteVar();
                }


                if((myGames[i].getBlackVar() < 65 || myGames[i].getBlackVar() > 90)
                        &&
                        myName.equals(myGames[i].getWhiteTeam())    ) {
                    state = theirTurn;
                }
                else if((myGames[i].getBlackVar() < 65 || myGames[i].getBlackVar() > 90)
                        &&
                        myName.equals(myGames[i].getBlackTeam())    )
                {
                    state = myTurn;
                }
                else if(myGames[i].getWinner() == null) {
                    if (myGames[i].getTurn().equals(myName)) {
                        if (myGames[i].isPromptOffense())
                            state = theirTurn;
                        else
                            state = myTurn;
                    } else {
                        if (myGames[i].isPromptOffense())
                            state = myTurn;
                        else
                            state = theirTurn;
                    }
                }
                else
                {
                    if(myGames[i].getWinner().equals(myName))
                        state = win;
                    else
                        state=loss;
                }
                if(gameList == null) {

                    gameList = new GameList[1];
                    tempGame = new SingleGame(myVar, theirVar, myGames[i].getGuid(), state);
                    gameList[0] = new GameList(theirName, tempGame);
                    if(state.equals(myTurn))
                    {
                        //update the gamelist header to suggest its my turn on one or more games.
                        gameList[0].setMyTurn(true);
                    }
                }
                else {
                    found = false;
                    for (int j = 0; j < gameList.length; j++) {
                        //figure out who this is.
                        if (gameList[j].getOpponent().equals(theirName)) //this denotes the first string in the list, which correlates to the opponent.
                        {
                            //they already have an entry in our list, lets add on to it.
                            tempGame = new SingleGame(myVar, theirVar, myGames[i].getGuid(), state);
                            gameList[j].addGame(tempGame);
                            found = true;

                            if(state.equals(myTurn))
                            {
                                //update the gamelist header to suggest its my turn on one or more games.
                                gameList[j].setMyTurn(true);
                            }
                        }
                    }
                    if (!found) {
                        //expand GameList and add this new record.
                        tempGame = new SingleGame(myVar, theirVar, myGames[i].getGuid(), state);
                        GameList[] tempList = new GameList[gameList.length+1];

                        for (int j = 0; j < gameList.length; j++) {
                            tempList[j] = gameList[j];
                        }
                        tempList[gameList.length] = new GameList(theirName, tempGame);
                        if(state.equals(myTurn))
                        {
                            //update the gamelist header to suggest its my turn on one or more games.
                            tempList[gameList.length].setMyTurn(true);
                        }

                        gameList = tempList;

                    }
                }
            }
        }



    }


    public class SingleGame
    {
        private char theirVariant;
        private UUID guid;



        private String state;
        private char myVariant;

        public SingleGame( char myVar,char theirVar, UUID guid, String state)
        {
            this.myVariant = myVar;
            this.guid = guid;
            this.state = state;
            this.theirVariant = theirVar;

        }
        public char getMyVariant() {
            return myVariant;
        }
        public void setMyVariant(char myVariant) {
            this.myVariant = myVariant;
        }
        public char getTheirVariant() {
            return theirVariant;
        }
        public void setTheirVariant(char theirVariant) {
            this.theirVariant = theirVariant;
        }
        public UUID getGuid() {
            return guid;
        }
        public void setGuid(UUID guid) {
            this.guid = guid;
        }
        public String getState() {
            return state;
        }
        public void setState(String state) {
            this.state = state;
        }

    }

    private class GameList{

        private String opponent;
        private SingleGame[] games;



        private boolean myTurn;
        public GameList(String opponent, SingleGame myGame)
        {
            this.opponent = opponent;
            games = new SingleGame[1];
            games[0] = myGame;
        }

        public void addGame(SingleGame myGame)
        {
            SingleGame[] tempGameList = new SingleGame[games.length+1];

            for(int i=0; i<games.length;i++)
            {
                tempGameList[i] = games[i];
            }
            tempGameList[games.length] = myGame;
            games = tempGameList;
        }

        public boolean isMyTurn() {
            return myTurn;
        }

        public void setMyTurn(boolean myTurn) {
            this.myTurn = myTurn;
        }
        public String getOpponent() {
            return opponent;
        }
        public void setOpponent(String opponent) {
            this.opponent = opponent;
        }
        public SingleGame[] getGames() {
            return games;
        }
        public void setGames(SingleGame[] games) {
            this.games = games;
        }








    }



}

