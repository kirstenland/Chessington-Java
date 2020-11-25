package training.chessington.model.pieces;

import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.List;

public interface Piece {
    int getMoves();

    int getDirection();

    enum PieceType {
        PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING
    }

    PieceType getType();
    PlayerColour getColour();

    List<Move> getAllowedMoves(Coordinates from, Board board);
    List<Move> getMovesBeforeCheck(Coordinates from, Board board);
    void move();
    void reverseMove();
}
