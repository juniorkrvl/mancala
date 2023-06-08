package com.bol.mancala.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PitRangeUnitTest {

    @Test
    void shouldReturnTheCorrectRangeForFirstPlayer() {
        PitRange range = PitRange.forFirstPlayer(20);
        assertEquals(0, range.start());
        assertEquals(8, range.end());
    }

    @Test
    void shouldReturnTheCorrectRangeForSecondPlayer() {
        PitRange range = PitRange.forSecondPlayer(20);
        assertEquals(10, range.start());
        assertEquals(18, range.end());
    }

    @Test
    void shouldCorrectlyVerifyIfPitRangeContainsPit() {
        PitRange range = PitRange.forFirstPlayer(14);
        assertTrue(range.contains(5));

        range = PitRange.forFirstPlayer(14);
        assertFalse(range.contains(8));
    }

    @Test
    void shouldReturnIfGivenPitIsAMancala() {
        PitRange range = PitRange.forFirstPlayer(20);
        assertTrue(range.isPitMancala(9));

        range = PitRange.forSecondPlayer(20);
        assertTrue(range.isPitMancala(19));
    }

    @Test
    void shouldReturnTheCorrectMancalaPositions() {
        PitRange range = PitRange.forFirstPlayer(14);
        assertEquals(6, range.mancalaPosition());

        range = PitRange.forSecondPlayer(14);
        assertEquals(13, range.mancalaPosition());
    }

    @Test
    void shouldVerifyIfPitRangeIsEmpty() {
        PitRange range = PitRange.forFirstPlayer(14);
        Pit[] pits = new Pit[14];

        // Initialize pits without stones to validate
        // that the range will be empty
        for (int i = 0; i < 14; i++) {
            pits[i] = Pit.empty();
        }

        assertTrue(range.isRangeEmpty(pits));

        // initialize pits with stones to validate
        // that the range will not be empty
        for (int i = 0; i < 14; i++) {
            pits[i] = Pit.withStoneAmount(4);
        }

        assertFalse(range.isRangeEmpty(pits));
    }

    @Test
    void shouldReturnStartAndEndPitPosition() {
        // First player range
        PitRange range = PitRange.forFirstPlayer(14);
        assertEquals(0, range.start());
        assertEquals(5, range.end());

        // Second player range
        range = PitRange.forSecondPlayer(14);
        assertEquals(7, range.start());
        assertEquals(12, range.end());


    }
}