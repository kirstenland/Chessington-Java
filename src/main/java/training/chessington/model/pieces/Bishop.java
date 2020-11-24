package training.chessington.model.pieces;

import training.chessington.model.*;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends AbstractPiece {
    public Bishop(PlayerColour colour) {
        super(PieceType.BISHOP, colour);
    }

    @Override
    public List<Move> getMovesBeforeCheck(Coordinates from, Board board) {
        List<Move> moves = new ArrayList<>();
        for (Direction direction: Direction.diagonal()) {
            exploreDirection(moves, from, direction, board);
        }
        return moves;
    }
}
