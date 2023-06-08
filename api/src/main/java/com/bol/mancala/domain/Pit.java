package com.bol.mancala.domain;

/***
 * Represents a pit where the stones are grabbed from.
 * The pit can have all its stones grabbed, and it
 * can have one stone added at a time
 */
public class Pit {

    private int stones;

    private Pit(int stones) {
        this.stones = stones;
    }

    /***
     * Create pit with stones
     * @param stones is the amount of stones we want to pit to be initialized with
     * @return a Pit with stones
     */
    public static Pit withStoneAmount(int stones) {
        return new Pit(stones);
    }

    /***
     * @return a pit without stones
     */
    public static Pit empty() {
        return new Pit(0);
    }

    /***
     * Remove all the stones from the pit
     * @return the removed stones amount
     */
    public int grabStones() {
        var tmp = this.stones;
        this.stones = 0;
        return tmp;
    }

    /***
     * Adds a single stone to the pit
     */
    public void addStone() {
        this.stones++;
    }

    /***
     * Add a number of stones to the pit
     * @param stones that we want to add to the pit
     */
    public void addStones(int stones) {
        this.stones+=stones;
    }

    /***
     * @return total stones in the pit
     */
    public int stones() {
        return this.stones;
    }


}
