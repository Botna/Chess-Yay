package chess2.Services;


import android.app.Service;
import android.content.Context;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;


import junit.runner.Version;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import chess2.customEXC.VersionMismatchException;
import chess2.source.GameBoard;
import chess2.source.GameContainer;
import chess2.source.GameHistoryContainer;
import serverClasses.ChessClass;

public class ChessService extends Service {

    protected ChessClass theGame;
    protected String myState = "INITIAL";
    protected String returnState = "INITIAL";
    protected String returnMessage = "";
    protected GameBoard myGB = null;
    protected GameContainer[] myGC = null;
    protected int myGameIndex = -1;
    private static final String RM = "RETURNMESSSAGE";
    private static final String RS = "RETURNSTATE";
    private static final String RO = "RETURNOBJECT";
    private static final String RA = "RETURNOBJECTARRAY";

    public static final String SERVER_RESPONSE = "com.botna.chess2.server_response";
    private final Handler handler = new Handler();
    Intent intent;
    private Runnable sendUpdatesToActivity = new Runnable() {
        public void run() {

            intent.putExtra(RS, returnState);
            intent.putExtra(RM, returnMessage);


            if (returnState.equals("REQUESTEDGAMES")) {
                Bundle b = new Bundle();
                b.putSerializable(RA,myGC);
                intent.putExtra(RA,myGC);
                intent.putExtras(b);
            }
            if (returnState.equals("GAMELOADED"))
            {
                Bundle b = new Bundle();
                b.putSerializable(RO,myGB);
                intent.putExtras(b);
            }


            sendBroadcast(intent);
        }

    };



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Check that we have Network/Internet Connectivity first.


        //make sure our connection is still setup.
        try {
            if (theGame == null) {

                theGame = new ChessClass();
            } else {
                //check to make sure the connection is still alive.


            }
        } catch (Exception e) {
            returnState = "DISCONNECTED";
            returnMessage = e.getMessage();
        }


        //find out if the connection was created correctly


        //Gather information out fo the intent and decipher what
        //we need to send to the server based on MODE, STATE, PAYLOAD etc

        //SEnd it to the server, get our response

        //PUt the response in our intent.


        //post our updates, which will trigger stuff in the activity.
        if(theGame != null)
        {
            String state = intent.getStringExtra("STATE");
            String result = null;
            String[] payload;

            switch (state) {
//
                case "LOGINATTEMPT":
                    payload = intent.getStringArrayExtra("PAYLOAD");

                    try {
                        result = theGame.login(payload[0], payload[1], payload[2]);

                        //login was a success.
                        returnState = "AUTHENTICATED";
                        returnMessage = result;
                        myState = "LOGGEDIN";
                    }catch( VersionMismatchException e)
                    {
                        returnState = "UPDATE";
                        returnMessage = e.getMessage();
                    }
                    catch (Exception e) {

                        //Wrong username/pwrod/ or generic error.
                        returnState = "ERROR";
                        returnMessage = e.getMessage();
                    }
                    break;

                case "REGISTERATTEMPT":


                    try {
                        result = theGame.register(intent.getExtras());
                        //login was a success.

                        String[] split = result.split(":");
                        if(split[1].equals("0"))
                        {
                            //user created
                            returnState = "USERCREATED";
                            returnMessage = split[2];
                            //put the username in the intent that gets passed back so we can immediately use it for a logon.

                            this.intent.putExtra("USERNAME", intent.getExtras().getString("USERNAME"));
                            this.intent.putExtra("PASSWORD", intent.getExtras().getString("PASSWORD"));
                        }
                        else
                        {
                            //user already exists.
                            returnState = "ERROR";
                            returnMessage =  split[2];
                        }

                    } catch (Exception e) {

                        //invalid username, username already exists, something else.
                        returnState = "ERROR";
                        returnMessage = "Problems -Kill and reopen";
                    }

                    break;

                case "CREATEGAME":


                    try {

                        result = theGame.createNewGame(intent.getExtras());
                        returnState = "GAMECREATED";
                        returnMessage = result;

                    } catch (Exception e) {

                        //invalid username, username already exists, something else.
                        returnState = "ERROR";
                        returnMessage = "Problems -Kill and reopen";
                    }
                    break;
                case "REQUESTGAMES":
                    try {

                        theGame.refreshGames(intent.getExtras());
                        GameContainer[] myGames = theGame.getCurrentGames();
                        myState = returnState = "REQUESTEDGAMES";
                        returnMessage = "";
                        myGC = myGames;

                    } catch (Exception e) {

                        //invalid username, username already exists, something else.
                        returnState = "ERROR";
                        returnMessage = "Problems -Kill and reopen";
                    }

                    break;

                case "CHOOSEBLACK":

                    intent.putExtra("GUID", myGB.getGuid().toString());

                    try {
                        theGame.updateClassChoice(intent.getExtras());
                        //success, start a game jsut the same

                        returnState = "GAMELOADED";
                        returnMessage = intent.getStringExtra("USERNAME");
                        myGB = theGame.getGame();
                        checkAndSaveGameHistory(myGB);
                    } catch (Exception e) {

                        //invalid username, username already exists, something else.
                        returnState = "ERROR";
                        returnMessage = "Problems -Kill and reopen";
                    }
                    break;

                case "LOADGAME":
                    int index = intent.getIntExtra("INDEX", -1);
                    myGameIndex = index;
                    try {

                        result = theGame.loadGame(intent.getExtras());
                        myGB = theGame.getGame();
                        if (result.equals("SUCCESS")) {
                            returnState = "GAMELOADED";
                            returnMessage = intent.getStringExtra("USERNAME");
                            checkAndSaveGameHistory(myGB);

                        } else {
                            //black needs update
                            returnState = "BLACKUPDATE";
                            returnMessage = "";
                        }
                    } catch (Exception e) {

                        //invalid username, username already exists, something else.
                        returnState = "ERROR";
                        returnMessage = "Problems -Kill and reopen";
                    }

                    break;

                case "ATTEMPTMOVE":

                    try {
                        result = theGame.sendMove(intent.getExtras());
                        returnState = "MOVEMADE";
                        returnMessage = result;

                    } catch (Exception e) {

                        //invalid username, username already exists, something else.
                        returnState = "ERROR";
                        returnMessage = "Problems -Kill and reopen";
                    }

                    break;

                case "REFRESHGAME":
                    intent.putExtra("INDEX", myGameIndex);
                    try {

                        result = theGame.loadGame(intent.getExtras());
                        if (result.equals("SUCCESS")) {
                            returnState = "GAMELOADED";
                            returnMessage = intent.getStringExtra("USERNAME");
                            myGB = theGame.getGame();
                            checkAndSaveGameHistory(myGB);
                        }
                    } catch (Exception e) {

                        //invalid username, username already exists, something else.
                        returnState = "ERROR";
                        returnMessage = "Problems -Kill and reopen";
                    }

                    break;

                case "ENACTSKIRMISH":
                    try {
                        result = theGame.sendSkirmish(intent.getExtras());
                        returnState = "SKIRMISHSENT";
                    } catch (Exception e) {

                        //invalid username, username already exists, something else.
                        returnState = "ERROR";
                        returnMessage = "Problems -Kill and reopen";
                    }

                    break;

                case "FINISHSKIRMISH":
                    try {

                        result = theGame.finishSkirmish(intent.getExtras());
                        returnState = "SKIRMISHSENT";
                    } catch (Exception e) {

                        //invalid username, username already exists, something else.
                        returnState = "ERROR";
                        returnMessage = "Problems -Kill and reopen";
                    }

                    break;

                case "FORFEIT":
                    try {

                         result = theGame.forfeitGame(intent.getExtras());
                        returnMessage = result;
                        returnState = "JUSTTOAST";
                    } catch (Exception e)
                    {
                        //invalid username, username already exists, something else.
                        returnState = "ERROR";
                        returnMessage = "Couldnt forfeit";
                    }


            }


        //now that we've done our business, disconnect the server.
        theGame.disconnect();
        theGame = null;
        //state suggests what we are being sent from the activity.
        //do the needful based on its payload and other stuff,
        //send to server, and send back the servers response so our
        //activity can do wahtever is necessary from taht point forward.

        }
        handler.removeCallbacks(sendUpdatesToActivity);
        handler.post(sendUpdatesToActivity);
        return Service.START_NOT_STICKY;

    }

    private void checkAndSaveGameHistory(GameBoard myGB) {
        //Using this method, whenever we load a game, we attempt to open up the file that correlates to its history.

        //if one doesnt exist, we create it blank.

        //if one does exist, we push our current gameboard to the historyContainer, so that the phone holds on to this
        //history information for the user to cycle through when inside a specific game.
        UUID guid = myGB.getGuid();
        File file = new File(this.getFilesDir(), guid.toString());
        try{
            if(file.exists())
            {
                //file exists, load it, modify, and re-save.
                InputStream fileIn = new FileInputStream(file);
                InputStream bufferIn = new BufferedInputStream(fileIn);
                ObjectInputStream in = new ObjectInputStream(bufferIn);
                GameHistoryContainer theHistory = (GameHistoryContainer) in.readObject();

                in.close();
                bufferIn.close();
                fileIn.close();
                //find if the game has a new state to write to the history.
                int numMoves = myGB.getNumberOfMoves();
                int numHistory = theHistory.getLength();

                //since the gameboard starts at 0 (with zero moves), but this gets put into the history bumping it up to 1,
                //a correctly maintained history is gameboards NumMoves + 1.   If numHistory is less than that, then it needs
                //to push the

                if(numHistory < numMoves + 1)
                {
                    myGB.clearHighlight();
                    myGB.clearSelected();
                    //Identify whether or not it is a turn or a challenge.
                    if(myGB.isPromptOffense())
                    {
                        //this means the defender challenged, so a challenge needs to be pushed.
                        theHistory.pushChallenge(myGB);
                    }
                    else
                    {
                        theHistory.pushTurn(myGB);
                    }

                    //we need to update our History file.  Output it to the file.
                    OutputStream fileOut = new FileOutputStream(file);
                    OutputStream bufferOut = new BufferedOutputStream(fileOut);
                    ObjectOutputStream out = new ObjectOutputStream(bufferOut);
                    out.writeObject(theHistory);
                    out.close();
                    bufferOut.close();
                    fileOut.close();
                }



            }
            else
            {
                //file doesnt exist, which implies move hasnt been made yet.
                //create it and we will wait for the first move to start making our history
                file.createNewFile();
                OutputStream fileOut = new FileOutputStream(file);
                OutputStream bufferOut = new BufferedOutputStream(fileOut);
                ObjectOutputStream out = new ObjectOutputStream(bufferOut);

                GameHistoryContainer theHistory = new GameHistoryContainer(myGB);
                out.writeObject(theHistory);
                out.close();
                bufferOut.close();
                fileOut.close();
            }
            //TODO
            //load up the gameHistory and modify it.


        }
        catch(FileNotFoundException e)
        {
            //file wasnt found.  First time loading a game for this file.  Lets create the file and thats it.  Dont really need to
            //save the very first game state, as its not important.

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        intent = new Intent(SERVER_RESPONSE);


    }

    @Override
    public void onDestroy()
    {
        //theGame.disconnect();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
