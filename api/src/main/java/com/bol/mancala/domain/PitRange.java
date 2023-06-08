package com.bol.mancala.domain;

public class PitRange {

    private final int start;
    private final int end;

    private PitRange(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public static PitRange forFirstPlayer(int totalPits) {
        int end = (totalPits / 2) - 2;
        return new PitRange(0,end);
    }

    public static PitRange forSecondPlayer(int totalPits) {
        int start = (totalPits / 2);
        return new PitRange(start,totalPits-2);
    }

    public static PitRange forRange(int start, int end) {
        return new PitRange(start, end);
    }

    public boolean contains(int pit){
        return pit >= start && pit <= end;
    }

    public boolean isPitMancala(int pit) {
        return pit == end + 1;
    }

    public int mancalaPosition() {
        return end+1;
    }

    public boolean isRangeEmpty(Pit[] pits) {
        int start = this.start;
        int sum = 0;
        while(start <= end)  {
            sum += pits[start].stones();
            start++;
        }
        return sum == 0;
    }

    public int start(){
        return start;
    }

    public int end() {
        return end;
    }
}
