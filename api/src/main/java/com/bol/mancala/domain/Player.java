package com.bol.mancala.domain;

/***
 * Represents a player for a match
 * which means we only represent a player when there is a game match
 * The Player class keeps track of the score and the pit range of the player
 */
public class Player {

    private final int id;
    private final PitRange pitRange;
    private int score;

    /***
     * Creates a new player
     * @param id the identifier of the player
     * @param pitRange the playable range the player can select the pits from
     */
    public Player(int id, PitRange pitRange) {
        this.id = id;
        this.pitRange = pitRange;
        this.score = 0;
    }

    /***
     * Checks if the player owns the given pit position
     * @param pit the position we want to check
     * @return true if the given position is within the player pit range
     */
    public boolean ownsPit(int pit){
        return pitRange.contains(pit);
    }

    /***
     * Checks if a given pit is a mancala
     * @param pit the position to check
     * @return true if the given position is a mancala
     */
    public boolean isPitMancala(int pit) {
        return pitRange.isPitMancala(pit);
    }

    /***
     * @return the mancala position
     */
    public int mancalaPosition(){
        return pitRange.mancalaPosition();
    }

    /***
     * Sets the player score
     * @param score the current score of the player
     */
    public void setScore(int score) {
        this.score = score;
    }

    /***
     * @return player score
     */
    public int score() {
        return score;
    }

    /***
     * @return player id
     */
    public int id() {
        return this.id;
    }

    /***
     * @return player playable pit range
     */
    public PitRange pitRange(){
        return pitRange;
    }
}
