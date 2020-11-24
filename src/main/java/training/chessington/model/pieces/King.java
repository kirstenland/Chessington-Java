package training.chessington.model.pieces;

import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.ArrayList;
import java.util.List;

public class King extends AbstractPiece {
    public King(PlayerColour colour) {
        super(PieceType.KING, colour);
    }

    @Override
    public List<Move> getMovesBeforeCheck(Coordinates from, Board board) {
        List<Move> moves = new ArrayList<>();
        int[] shifts = new int[]{-1, 0, 1};
        for (int xChange : shifts) {
            for (int yChange : shifts) {
                if (xChange != 0 || yChange != 0) {
                    moveOrTake(moves, from, from.plus(xChange, yChange), board);
                }
            }
        }
        return moves;
    }
}
