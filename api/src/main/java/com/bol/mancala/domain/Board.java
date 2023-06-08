package com.bol.mancala.domain;

import java.util.Arrays;

public class Board {

    private final Pit[] pits;
    private final int totalAmountPits;

    private final PitRange firstPlayerPitRange;
    private final PitRange secondPlayerPitRange;

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

    public boolean isGameOver() {
        return firstPlayerPitRange.isRangeEmpty(pits) || secondPlayerPitRange.isRangeEmpty(pits);
    }

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

    /**
     * After distributing the stones, if the last stone landed in
     * an empty pit and the pit belongs to the player, the player
     * captures its stone and the opponent opposite pit stones
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

    /**
     * When the player has no more stones on his side, the game is over and
     * the opponent captures the remaining stones on their side of the board
     */
    private void opponentCapturesRemainingStones(Player opponent) {
        for (int i = opponent.pitRange().start(); i <= opponent.pitRange().end() ; i++) {
            pits[opponent.mancalaPosition()].addStones(pits[i].grabStones());
        }
        opponent.setScore(pits[opponent.mancalaPosition()].stones());
    }

    private Pit pitWhereLatestStoneLanded(int position){
        return pits[position];
    }

    public PitRange secondPlayerPitRange() {
        return secondPlayerPitRange;
    }

    public PitRange firstPlayerPitRange() {
        return firstPlayerPitRange;
    }

    public int[] pitsToArray() {
        return Arrays.stream(pits).mapToInt(Pit::stones).toArray();
    }

    private boolean isAMancala(int index) {
        return index==(totalAmountPits/2)-1 || index==totalAmountPits-1;
    }

    private int oppositePit(int pit) {
        return (totalAmountPits-2) - pit;
    }
}
