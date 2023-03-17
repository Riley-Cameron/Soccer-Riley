package cs301.Soccer;

import android.util.Log;
import cs301.Soccer.soccerPlayer.SoccerPlayer;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Soccer player database -- presently, all dummied up
 *
 * @author *** put your name here ***
 * @version *** put date of completion here ***
 *
 */
public class SoccerDatabase implements SoccerDB {

    // dummied up variable; you will need to change this
    private Hashtable<String,SoccerPlayer> database = new Hashtable();

    public String makeKey(String first, String last) {
        return first + "##" + last;
    }

    /**
     * add a player
     *
     * @see SoccerDB#addPlayer(String, String, int, String)
     */
    @Override
    public boolean addPlayer(String firstName, String lastName,
                             int uniformNumber, String teamName) {

        String key = makeKey(firstName,lastName);
        if (database.containsKey(key)) {
            return false;
        } else {
            SoccerPlayer newPlayer = new SoccerPlayer(firstName, lastName, uniformNumber, teamName);
            database.put(key, newPlayer);
            return true;
        }
    }

    /**
     * remove a player
     *
     * @see SoccerDB#removePlayer(String, String)
     */
    @Override
    public boolean removePlayer(String firstName, String lastName) {
        String key = makeKey(firstName,lastName);

        if (database.containsKey(key)) {
            database.remove(key);
            return true;
        } else {
            return false;
        }
    }

    /**
     * look up a player
     *
     * @see SoccerDB#getPlayer(String, String)
     */
    @Override
    public SoccerPlayer getPlayer(String firstName, String lastName) {
        String key = firstName + "##" + lastName;

        return database.get(key);
    }

    /**
     * increment a player's goals
     *
     * @see SoccerDB#bumpGoals(String, String)
     */
    @Override
    public boolean bumpGoals(String firstName, String lastName) {
        String key = makeKey(firstName,lastName);

        if (database.containsKey(key)) {
            database.get(key).bumpGoals();
            return true;
        } else {
            return false;
        }
    }

    /**
     * increment a player's yellow cards
     *
     * @see SoccerDB#bumpYellowCards(String, String)
     */
    @Override
    public boolean bumpYellowCards(String firstName, String lastName) {
        String key = makeKey(firstName,lastName);

        if (database.containsKey(key)) {
            database.get(key).bumpYellowCards();
            return true;
        } else {
            return false;
        }
    }

    /**
     * increment a player's red cards
     *
     * @see SoccerDB#bumpRedCards(String, String)
     */
    @Override
    public boolean bumpRedCards(String firstName, String lastName) {
        String key = makeKey(firstName,lastName);

        if (database.containsKey(key)) {
            database.get(key).bumpRedCards();
            return true;
        } else {
            return false;
        }
    }

    /**
     * tells the number of players on a given team
     *
     * @see SoccerDB#numPlayers(String)
     */
    @Override
    // report number of players on a given team (or all players, if null)
    public int numPlayers(String teamName) {
        if (teamName == null) {
            return database.size();
        } else {
            int playerCount = 0;
            Collection<SoccerPlayer> players = database.values();

            for (SoccerPlayer s : players) {
                if (s.getTeamName().equals(teamName)) {
                    playerCount++;
                }
            }
            return playerCount;
        }
    }

    /**
     * gives the nth player on a the given team
     *
     * @see SoccerDB#playerIndex(int, String)
     */
    // get the nTH player
    @Override
    public SoccerPlayer playerIndex(int idx, String teamName) {
        int index = 0;
        Collection<SoccerPlayer> players = database.values();

        if (teamName == null) {
            for (SoccerPlayer s : players) {
                if (index == idx) {
                    return s;
                } else {
                    index++;
                }
            }
        } else {
            for (SoccerPlayer s : players) {
                if (s.getTeamName().equals(teamName)) {
                    if (index == idx) {
                        return s;
                    } else {
                        index++;
                    }
                }
            }
        }
        return null;
    }

    /**
     * reads database data from a file
     *
     * @see SoccerDB#readData(java.io.File)
     */
    // read data from file
    @Override
    public boolean readData(File file) {
        return file.exists();
    }

    /**
     * write database data to a file
     *
     * @see SoccerDB#writeData(java.io.File)
     */
    // write data to file
    @Override
    public boolean writeData(File file) {
        try {
            FileWriter fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(fw);

            Collection<SoccerPlayer> players = database.values();
            for (SoccerPlayer s : players) {
                pw.println(logString(s.getFirstName()));
                pw.println(logString(s.getLastName()));
                pw.println(logString(s.getTeamName()));
                pw.println(logString(String.valueOf(s.getGoals())));
                pw.println(logString(String.valueOf(s.getRedCards())));
                pw.println(logString(String.valueOf(s.getYellowCards())));
                pw.println(logString(String.valueOf(s.getUniform())));
                pw.println(logString("\n"));
            }

            pw.close();
            return true;

        } catch (IOException e) {
            return false;
        }
    }

        /**
     * helper method that logcat-logs a string, and then returns the string.
     * @param s the string to log
     * @return the string s, unchanged
     */
    private String logString(String s) {
        Log.i("write string", s);
        return s;
    }

    /**
     * returns the list of team names in the database
     *
     * @see cs301.Soccer.SoccerDB#getTeams()
     */
    // return list of teams
    @Override
    public HashSet<String> getTeams() {
        return new HashSet<String>();
    }

    /**
     * Helper method to empty the database and the list of teams in the spinner;
     * this is faster than restarting the app
     */
    public boolean clear() {
        if(database != null) {
            database.clear();
            return true;
        }
        return false;
    }
}
