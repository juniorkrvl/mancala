package com.bol.mancala.domain;

import java.util.Arrays;

/**
 * Represents a game board in the game of Mancala.
 * <p>
 * The board is divided into two sides, each belonging to one player.
 * Each side contains a number of pits, and each pit contains a number of stones.
 * Players take turns picking up all the stones in one of their pits and dropping them into subsequent pits one at a time.
 * The game ends when all pits on one side of the board are empty.
 * </p>
 */
public class Board {

    private final Pit[] pits;
    private final int totalAmountPits;
    private final PitRange firstPlayerPitRange;
    private final PitRange secondPlayerPitRange;

    /**
     * Creates a new game board with a specified number of pits and stones per pit.
     *
     * @param totalAmountPits the total number of pits on the board.
     * @param totalStonesPerPit the initial number of stones in each pit.
     * @throws CannotCreateBoard if the total number of pits is not even.
     */
    public Board(int totalAmountPits, int totalStonesPerPit) throws CannotCreateBoard {

        if (totalAmountPits%2 != 0) {
            throw CannotCreateBoard.becauseTotalAmountOfPitsShouldBeEven(totalAmountPits);
        }

        this.totalAmountPits = totalAmountPits;
        pits = new Pit[totalAmountPits];

        // Configure the pit range for both players
        this.firstPlayerPitRange = PitRange.forFirstPlayer(totalAmountPits);
        this.secondPlayerPitRange = PitRange.forSecondPlayer(totalAmountPits);

        /*
         * We are initializing the board with an equal amount of pits
         * and mancalas per player.
         *
         * A playable pit is a pit where the player can grab stones from
         * while a mancala can only receive stones.
         */
        for (int i = 0; i < totalAmountPits ; i++) {
            if (isAMancala(i)){
                pits[i] = Pit.empty();
            } else {
                pits[i] = Pit.withStoneAmount(totalStonesPerPit);
            }
        }
    }

    /**
     * Creates a new game board from an array of pit counts.
     *
     * @param pitsArray an array where the element at index i gives the number of stones in pit i.
     */
    public Board(int[] pitsArray){
        this.totalAmountPits = pitsArray.length;
        this.pits = new Pit[totalAmountPits];

        for (int i = 0; i < pitsArray.length ; i++) {
            pits[i] = Pit.withStoneAmount(pitsArray[i]);
        }

        // Configure the pit range for both players
        this.firstPlayerPitRange = PitRange.forFirstPlayer(totalAmountPits);
        this.secondPlayerPitRange = PitRange.forSecondPlayer(totalAmountPits);
    }

    /**
     * Checks if the game is over.
     *
     * @return true if all pits on one side of the board are empty, and false otherwise.
     */
    public boolean isGameOver() {
        return firstPlayerPitRange.isRangeEmpty(pits) || secondPlayerPitRange.isRangeEmpty(pits);
    }

    /***
     * Represents a move of a player.
     *
     * @param move a move is when a player selects a pit
     * @return the player that play the next turn in the game
     * @throws CannotMakeMove if the game is over or if the selected pit is empty
     *
     */
    public Player play(Move move) throws CannotMakeMove {

        if (isGameOver()){
            throw CannotMakeMove.becauseGameIsOver();
        }

        if (pits[move.startingPit()].stones() == 0){
            throw CannotMakeMove.becauseChosenPitIsEmtpy();
        }

        Player player = move.player();
        Player opponent = move.opponent();
        int startingPit = move.startingPit();

        int stones = pits[startingPit].grabStones();
        int position = move.startingPit();

        while(stones > 0) {
            // Keep it circular with modulo operation
            position = (position + 1) % totalAmountPits;

            // Skip opponent mancala when distributing stones in the board
            if (!opponent.isPitMancala(position)){
                pits[position].addStone();
                stones--;
            }
        }

        // Current player keeps playing if stone landed in mancala
        Player nextTurnPlayer = opponent;
        if (player.isPitMancala(position)) {
            nextTurnPlayer = player;
        }

        // Capture opponent stones if latest stone landed in empty player pit
        if (pitWhereLatestStoneLanded(position).stones() == 1 && player.ownsPit(position)) {
            captureStones(position, player);
        }


        // Opponent capture all his stones if game is over in player move
        if (isGameOver()){
            opponentCapturesRemainingStones(opponent);
        }

        player.setScore(pits[player.mancalaPosition()].stones());

        return nextTurnPlayer;
    }

    /***
     * After distributing the stones, if the last stone landed in
     * an empty pit and the pit belongs to the player, the player
     * captures its stone and the opponent opposite pit stones
     * @param position represents position where the latest stone landed
     * @param player is the current player making the move
     */
    private void captureStones(int position, Player player) {
        int playerMancalaPosition = player.mancalaPosition();
        int oppositePit = oppositePit(position);

        // Capture opponent's stones
        int capturedStones = pits[oppositePit].grabStones();

        /*
         * If we have stones in the opposite side, we can capture it
         * otherwise the game just continues and the last stone that
         * landed in an empty pit stays there
         */
        if (capturedStones > 0) {
            Pit latestPlayerPitWhereStoneLanded = pits[position];
            int playerStones = latestPlayerPitWhereStoneLanded.grabStones();

            // Add captured stones and the player's last stone to player's Mancala
            pits[playerMancalaPosition].addStones(capturedStones + playerStones);
        }
    }

    /***
     * When the player has no more stones on his side, the game is over and
     * the opponent captures the remaining stones on their side of the board
     * @param opponent is the other player waiting its turn
     */
    private void opponentCapturesRemainingStones(Player opponent) {
        for (int i = opponent.pitRange().start(); i <= opponent.pitRange().end() ; i++) {
            pits[opponent.mancalaPosition()].addStones(pits[i].grabStones());
        }
        opponent.setScore(pits[opponent.mancalaPosition()].stones());
    }

    /***
     * The pit where the latest stone landed after a move
     * @param position of the pit where the latest stone landed
     * @return Pit
     */
    private Pit pitWhereLatestStoneLanded(int position){
        return pits[position];
    }

    /***
     * The board is responsible to keep the state of the stones
     * and also knows where each player pit range starts and ends
     *
     * @return the second player pit range
     */
    public PitRange secondPlayerPitRange() {
        return secondPlayerPitRange;
    }

    /***
     * The board is responsible to keep the state of the stones
     * and also knows where each player pit range starts and ends
     *
     * @return the first player pit range
     */
    public PitRange firstPlayerPitRange() {
        return firstPlayerPitRange;
    }

    /***
     * @return the pits state as an array of integer
     */
    public int[] pitsToArray() {
        return Arrays.stream(pits).mapToInt(Pit::stones).toArray();
    }

    /***
     * Checks if a given pit position is a mancala of either players
     * @param position of the pit to check if it is a mancala
     * @return true if the pit position is a mancala
     */
    private boolean isAMancala(int position) {
        return position==(totalAmountPits/2)-1 || position==totalAmountPits-1;
    }

    /***
     * Represents the opposite pit in the board for the given pit position
     * @param pit position of the pit we want to find the opposite pit for
     * @return the opposite pit
     */
    private int oppositePit(int pit) {
        return (totalAmountPits-2) - pit;
    }
}
