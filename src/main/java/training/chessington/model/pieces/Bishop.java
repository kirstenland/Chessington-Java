package training.chessington.model.pieces;

import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Bishop extends AbstractPiece {
    public Bishop(PlayerColour colour) {
        super(PieceType.BISHOP, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Board board) {
        List<Move> moves = new ArrayList<>();
        int[] shifts = {-1, 1};
        for (int xShift: shifts) {
            for (int yShift: shifts) {
                addAllShifts(moves, from, xShift, yShift, board);
            }
        }

        return moves;
    }
}
