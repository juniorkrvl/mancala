package com.bol.mancala.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PitUnitTest {

    @Test
    void shouldHaveCorrectNumberOfStones() {
        Pit pit = Pit.withStoneAmount(6);
        assertEquals(6, pit.stones());
    }

    @Test
    void shouldBeEmpty() {
        Pit pit = Pit.empty();
        assertEquals(0, pit.stones());
    }

    @Test
    void shouldGrabAllStonesAndBecomeEmpty() {
        Pit pit = Pit.withStoneAmount(6);
        pit.grabStones();
        assertEquals(0, pit.stones());
    }

    @Test
    void shouldAddOneStone() {
        Pit pit = Pit.withStoneAmount(6);
        pit.addStone();
        assertEquals(7, pit.stones());
    }

    @Test
    void shouldAddANumberOfStones() {
        Pit pit = Pit.withStoneAmount(6);
        pit.addStones(3);
        assertEquals(9, pit.stones());
    }
}