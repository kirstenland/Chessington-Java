package training.chessington.model.pieces;

import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends AbstractPiece {
    public Pawn(PlayerColour colour) {
        super(Piece.PieceType.PAWN, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Board board) {
        List<Move> moves = new ArrayList<>();

        Coordinates oneStep = from.plus(getDirection(), 0);
        Coordinates twoSteps = oneStep.plus(getDirection(), 0);
        if (board.get(oneStep) == null) {
            moves.add(new Move(from, oneStep));
        }
        if (!moved) {
            if (board.get(oneStep) == null && board.get(twoSteps) == null) {
                moves.add(new Move(from, twoSteps));
            }
        }
        return moves;
    }
}
