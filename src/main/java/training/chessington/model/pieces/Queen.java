package training.chessington.model.pieces;

import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.ArrayList;
import java.util.List;

public class Queen extends AbstractPiece {
    public Queen(PlayerColour colour) {
        super(PieceType.QUEEN, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Board board) {
        List<Move> moves = new ArrayList<>();
        int[] shift = new int[] {-1, 0, 1};
        for (int rowDiff: shift) {
            for (int colDiff: shift) {
                if (rowDiff != 0 || colDiff != 0) {
                    exploreDirection(moves, from, rowDiff, colDiff, board);
                }
            }
        }

        return moves;
    }
}
