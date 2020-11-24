package training.chessington.model.pieces;

import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.List;

public abstract class AbstractPiece implements Piece {

    protected final Piece.PieceType type;
    protected final PlayerColour colour;
    protected boolean moved;

    protected AbstractPiece(Piece.PieceType type, PlayerColour colour) {
        this.type = type;
        this.colour = colour;
        moved = false;
    }

    @Override
    public Piece.PieceType getType() {
        return type;
    }

    @Override
    public PlayerColour getColour() {
        return colour;
    }

    @Override
    public String toString() {
        return colour.toString() + " " + type.toString();
    }

    public Integer getDirection() {
        switch(colour) {
            case WHITE: return -1;
            case BLACK: return 1;
        }
        return null;
    }

    public void setMoved() {
        moved = true;
    }

    protected boolean isEnemy(Piece piece) {
        return piece.getColour() != colour;
    }

    protected void addIfEnemy(List<Move> moves, Coordinates from, Coordinates to, Board board) {
        Piece piece = board.get(to);
        if (piece != null && isEnemy(piece)) {
            moves.add(new Move(from, to));
        }
    }

    protected void addIfEmpty(List<Move> moves, Coordinates from, Coordinates to, Board board) {
        Piece piece = board.get(to);
        if (to.isOnBoard() && piece == null) {
            moves.add(new Move(from, to));
        }
    }
}
