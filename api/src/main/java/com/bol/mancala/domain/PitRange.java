package com.bol.mancala.domain;

/***
 * Represents a range of playable pits,
 * meaning that a mancala should not be considered within the pit range
 * It is used to defined whether a player has ownership of a given
 * board position
 */
public class PitRange {

    private final int start;
    private final int end;

    /***
     * Creates a pit range
     * @param start the starting pit
     * @param end the final pit, it should be not a mancala
     */
    private PitRange(int start, int end) {
        this.start = start;
        this.end = end;
    }

    /***
     * Creates a pit range configuration for the first player given the total of pits
     * @param totalPits amount of pits in the board including the 2 mancalas
     * @return a playable pit range for the first player
     */
    public static PitRange forFirstPlayer(int totalPits) {
        int end = (totalPits / 2) - 2;
        return new PitRange(0,end);
    }

    /***
     * Creates a pit range configuration for the second player given the total of pits
     * @param totalPits amount of pits in the board including the 2 mancalas
     * @return a playable pit range for the second player
     */
    public static PitRange forSecondPlayer(int totalPits) {
        int start = (totalPits / 2);
        return new PitRange(start,totalPits-2);
    }

    /***
     * Create a pit range for the given range
     * @param start start of the range
     * @param end end of the range
     * @return a new playable pit range
     */
    public static PitRange forRange(int start, int end) {
        return new PitRange(start, end);
    }

    /***
     * Checks if the pit range contains the given pit position
     * @param pit position we want to check if is within the range
     * @return true if the pit is within the range.
     * e.g: (start:0) (end:5) -> return true for pit position 3
     */
    public boolean contains(int pit){
        return pit >= start && pit <= end;
    }

    /***
     * Checks if given pit position is a mancala
     * @param pit position to check
     * @return true if the given position is a mancala.
     * The mancala is always the end value + 1
     */
    public boolean isPitMancala(int pit) {
        return pit == end + 1;
    }

    /***
     * @return The mancala position
     */
    public int mancalaPosition() {
        return end+1;
    }

    /***
     * Given an array of Pits, checks if within all the playable pits there are no stones
     * @param pits represents all the pits from the board including the mancalas
     * @return true if within range all the pits are empty
     */
    public boolean isRangeEmpty(Pit[] pits) {
        int start = this.start;
        int sum = 0;
        while(start <= end)  {
            sum += pits[start].stones();
            start++;
        }
        return sum == 0;
    }

    /***
     * @return range start
     */
    public int start(){
        return start;
    }

    /***
     * @return range end
     */
    public int end() {
        return end;
    }
}
