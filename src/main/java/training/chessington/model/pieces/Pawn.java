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
    public List<Move> getMovesBeforeCheck(Coordinates from, Board board) {
        List<Move> moves = new ArrayList<>();

        Coordinates oneStep = from.plus(getDirection(), 0);
        Coordinates twoSteps = oneStep.plus(getDirection(), 0);
        addIfEmpty(moves, from, oneStep, board);
        if (!getMoved() && board.get(oneStep) == null) {
            addIfEmpty(moves, from, twoSteps, board);
        }

        Coordinates[] diagonals = new Coordinates[]{from.plus(getDirection(), -1), from.plus(getDirection(), 1)};
        for (Coordinates diagonal: diagonals) {
            addIfEnemy(moves, from, diagonal, board);
            if (canEnPassant(from, diagonal, board)) {
                addIfEmpty(moves, from, diagonal, board);
            }
        }

        return moves;
    }

    private boolean canEnPassant(Coordinates from, Coordinates to, Board board) {
        Piece pieceToTake = board.get(to.plus(-getDirection(),0));
        return (pieceToTake != null &&
                pieceToTake.getType() == PieceType.PAWN &&
                pieceToTake.getMoves() == 1 &&
                from.getRow() == passantRank()) &&
                board.getLastPiece() == pieceToTake;
    }

    private int passantRank() {
        return ((colour == PlayerColour.WHITE) ? 3 : 4);
    }

}
