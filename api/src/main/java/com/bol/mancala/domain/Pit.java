package com.bol.mancala.domain;

public class Pit {

    private int stones;

    private Pit(int stones) {
        this.stones = stones;
    }

    public static Pit withStoneAmount(int stones) {
        return new Pit(stones);
    }

    public static Pit empty() {
        return new Pit(0);
    }

    public int grabStones() {
        var tmp = this.stones;
        this.stones = 0;
        return tmp;
    }

    public void addStone() {
        this.stones++;
    }

    public void addStones(int stones) {
        this.stones+=stones;
    }

    public int stones() {
        return this.stones;
    }


}
