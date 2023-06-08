package com.bol.mancala.domain;

public class CannotCreateBoard extends MancalaException {
    private CannotCreateBoard(String message) {
        super(message);
    }

    public static CannotCreateBoard becauseTotalAmountOfPitsShouldBeEven(int pits) {
        return new CannotCreateBoard(
            String.format(
                "Can not create board because the total amount of pits should be even. The number given was %d",
                pits
            )
        );
    }
}
