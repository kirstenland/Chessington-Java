package training.chessington.model.pieces;

import training.chessington.model.*;

import java.util.ArrayList;
import java.util.List;

public class Rook extends AbstractPiece {
    public Rook(PlayerColour colour) {
        super(PieceType.ROOK, colour);
    }

    @Override
    public List<Move> getMovesBeforeCheck(Coordinates from, Board board) {
        List<Move> moves = new ArrayList<>();
        for (Direction direction: Direction.orthogonal()) {
            exploreDirection(moves, from, direction, board);
        }
        return moves;
    }
}
