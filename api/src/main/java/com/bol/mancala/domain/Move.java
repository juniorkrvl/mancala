package com.bol.mancala.domain;

public class Move {

    private final int pit;
    private final Player player;
    private final Player opponent;

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

    public int startingPit() {
        return pit;
    }

    public Player player() {
        return this.player;
    }

    public Player opponent() {
        return this.opponent;
    }
}
