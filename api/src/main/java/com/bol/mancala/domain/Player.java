package com.bol.mancala.domain;

public class Player {

    private final int id;
    private final PitRange pitRange;
    private int score;

    public Player(int id, PitRange pitRange) {
        this.id = id;
        this.pitRange = pitRange;
        this.score = 0;
    }

    public boolean ownsPit(int pit){
        return pitRange.contains(pit);
    }

    public boolean isPitMancala(int pit) {
        return pitRange.isPitMancala(pit);
    }

    public int mancalaPosition(){
        return pitRange.mancalaPosition();
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int score() {
        return score;
    }

    public int id() {
        return this.id;
    }

    public PitRange pitRange(){
        return pitRange;
    }
}
