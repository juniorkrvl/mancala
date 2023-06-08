package com.bol.mancala.domain;

/***
 * Represents a player move in a match
 */
public class Move {

    private final int pit;
    private final Player player;
    private final Player opponent;

    /***
     * Creates a new move with a pit, player and opponent
     * @param pit is the selected pit for the move
     * @param player is the current player playing the move
     * @param opponent is the other player waiting for the move
     * @throws CannotMakeMove if the selected pit is a mancala or
     * if the selected pit does not belong to the player
     */
    public Move(int pit, Player player, Player opponent) throws CannotMakeMove {

        if (player.isPitMancala(pit)){
            throw CannotMakeMove.becauseChosenPitIsAMancala(player.pitRange());
        }

        if (!player.ownsPit(pit)){
            throw CannotMakeMove.becausePitDoesNotBelongToPlayer(player.pitRange());
        }

        this.pit = pit;
        this.player = player;
        this.opponent = opponent;
    }

    /***
     * The selected pit
     * @return the number representing the pit position
     */
    public int startingPit() {
        return pit;
    }

    /***
     * @return the player making the move
     */
    public Player player() {
        return this.player;
    }

    /***
     * @return the player waiting for the move to finish
     */
    public Player opponent() {
        return this.opponent;
    }
}
