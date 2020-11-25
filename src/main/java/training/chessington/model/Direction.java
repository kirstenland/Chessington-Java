package training.chessington.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class Direction {
    private int rowDiff;
    private int colDiff;

    public Direction (int rowDiff, int colDiff) {
        this.rowDiff = rowDiff;
        this.colDiff = colDiff;
    }

    public static List<Direction> orthogonal() {
        return Arrays.asList(new Direction(1, 0),
                new Direction(-1, 0),
                new Direction(0, 1),
                new Direction(0, -1));
    }

    public static List<Direction> diagonal() {
        List<Direction> directions= new ArrayList<>();
        int[] shift = new int[] {-1, 1};
        for (int rowDiff: shift ) {
            for (int colDiff: shift) {
                directions.add(new Direction(rowDiff, colDiff));
            }
        }
        return directions;
    }

    public static List<Direction> all() {
        List<Direction> directions = new ArrayList<>();
        directions.addAll(diagonal());
        directions.addAll(orthogonal());
        return directions;
    }

    public boolean isDiagonal() {
        return Math.abs(rowDiff) == 1 && Math.abs(colDiff) == 1;
    }
}
