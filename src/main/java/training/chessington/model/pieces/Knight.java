package training.chessington.model.pieces;

import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Knight extends AbstractPiece {
    public Knight(PlayerColour colour) {
        super(PieceType.KNIGHT, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Board board) {
        List<Move> moves = new ArrayList<>();
        int[] shifts = new int[]{-2, -1, 1, 2};
        for (int xChange : shifts) {
            for (int yChange : shifts) {
                if (Math.abs(xChange) != Math.abs(yChange)) {
                    moveOrTake(moves, from, from.plus(xChange, yChange), board);
                }
            }
        }
        return moves;
    }
}
