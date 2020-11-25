package training.chessington.model.pieces;

import training.chessington.model.*;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPiece implements Piece {

    protected final Piece.PieceType type;
    protected final PlayerColour colour;
    protected int moveCount;

    protected AbstractPiece(Piece.PieceType type, PlayerColour colour) {
        this.type = type;
        this.colour = colour;
        moveCount = 0;
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

    public void move() {
        moveCount += 1;
    }

    public void reverseMove() {
        moveCount -= 1;
    }

    public boolean getMoved() {
        return (moveCount > 0);
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

    protected void moveOrTake(List<Move> moves, Coordinates from, Coordinates to, Board board) {
        addIfEmpty(moves, from, to, board);
        addIfEnemy(moves, from, to, board);
    }

    protected void exploreDirection(List<Move> moves, Coordinates from, Direction direction, Board board) {
        boolean searching = true;
        Coordinates current = from;
        while (searching) {
            current = current.plus(direction);
            moveOrTake(moves, from, current, board);
            if (!current.isOnBoard() || board.get(current) != null) {
                searching = false;
            }
        }
    }

    public List<Move> getAllowedMoves(Coordinates from, Board board) {
        List<Move> moves = getMovesBeforeCheck(from, board);
        List<Move> filteredMoves = new ArrayList<>();
        for (Move move: moves) {
            if (board.testMove(this, from, move.getTo())) {
                filteredMoves.add(move);
            }
        }
        return filteredMoves;
    };

}
