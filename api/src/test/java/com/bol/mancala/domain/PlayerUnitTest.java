package com.bol.mancala.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerUnitTest {

    private PitRange pitRangeFirstPlayer;
    private PitRange pitRangeSecondPlayer;
    private Player firstPlayer;
    private Player secondPlayer;

    @BeforeEach
    void setup(){
        int totalPits = 14;
        pitRangeFirstPlayer = PitRange.forFirstPlayer(totalPits);
        pitRangeSecondPlayer = PitRange.forSecondPlayer(totalPits);
        firstPlayer = new Player(1, pitRangeFirstPlayer);
        secondPlayer = new Player(2, pitRangeSecondPlayer);
    }

    @Test
    void shouldTestIfPlayerOwnsTheGivenPit() {

        /*
         * Player 1 should own pits from 0 to 5.
         * 6 is a mancala and does not count
         */
        boolean playerOwnsPit = firstPlayer.ownsPit(3);
        assertTrue(playerOwnsPit);

        playerOwnsPit = firstPlayer.ownsPit(7);
        assertFalse(playerOwnsPit);

        /*
         * Player 2 should own pits from 7 to 12.
         * 13 is a mancala and does not count
         */
        playerOwnsPit = secondPlayer.ownsPit(8);
        assertTrue(playerOwnsPit);

        playerOwnsPit = secondPlayer.ownsPit(5);
        assertFalse(playerOwnsPit);

    }

    @Test
    void shouldReturnTrueIfPitIsAMancala() {
        boolean isMancala = firstPlayer.isPitMancala(6);
        assertTrue(isMancala);

        isMancala = secondPlayer.isPitMancala(13);
        assertTrue(isMancala);
    }

    @Test
    void shouldReturnFalseIfPitIsNotAMancala() {
        boolean isMancala = firstPlayer.isPitMancala(5);
        assertFalse(isMancala);

        isMancala = secondPlayer.isPitMancala(8);
        assertFalse(isMancala);
    }

    @Test
    void shouldReturnMancalaPosition() {
        int position = firstPlayer.mancalaPosition();
        assertEquals(6, position);

        position = secondPlayer.mancalaPosition();
        assertEquals(13, position);
    }

    @Test
    void shouldSetScore() {
        firstPlayer.setScore(10);
        secondPlayer.setScore(4);
        assertEquals(10, firstPlayer.score());
        assertEquals(4, secondPlayer.score());

    }

    @Test
    void shouldReturnTheCorrectId() {
        assertEquals(1, firstPlayer.id());
        assertEquals(2, secondPlayer.id());
    }

    @Test
    void shouldReturnCorrectPitRange() {
        assertEquals(pitRangeFirstPlayer, firstPlayer.pitRange());
        assertEquals(pitRangeSecondPlayer, secondPlayer.pitRange());
    }
}