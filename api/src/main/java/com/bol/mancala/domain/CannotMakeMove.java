package com.bol.mancala.domain;

public class CannotMakeMove extends MancalaException {

    private CannotMakeMove(String message) {
        super(message);
    }

    public static CannotMakeMove becauseGameIsOver() {
        return new CannotMakeMove("The game is over");
    }

    public static CannotMakeMove becauseItIsNotPlayerTurn(Player currentPlayer) {
        return new CannotMakeMove(
            String.format(
                "Oops! It's not your turn to play. It is the player %d turn.",
                currentPlayer.id()
            )
        );
    }

    public static CannotMakeMove becausePitDoesNotBelongToPlayer(PitRange pitRange) {
        return new CannotMakeMove(
            String.format(
                "Can not make move because selected pit does not belong to player. The pit range for player is between %d and %d",
                pitRange.start(),
                pitRange.end()
            )
        );
    }

    public static CannotMakeMove becauseChosenPitIsAMancala(PitRange pitRange) {
        return new CannotMakeMove(
                String.format(
                        "Can not make move because selected pit is a mancala. The pit range for player is between %d and %d",
                        pitRange.start(),
                        pitRange.end()
                )
        );
    }

    public static CannotMakeMove becauseChosenPitIsEmtpy() {
        return new CannotMakeMove(
            "Can not make move because selected pit is empty. Select a pit that has stones"
        );
    }

}
