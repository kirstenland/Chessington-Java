package training.chessington.model;

import training.chessington.model.pieces.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {

    private Piece lastPiece;
    private Piece[][] board = new Piece[8][8];

    private Board() {
    }

    public static Board forNewGame() {
        Board board = new Board();
        board.setBackRow(0, PlayerColour.BLACK);
        board.setBackRow(7, PlayerColour.WHITE);

        for (int col = 0; col < 8; col++) {
            board.board[1][col] = new Pawn(PlayerColour.BLACK);
            board.board[6][col] = new Pawn(PlayerColour.WHITE);
        }

        return board;
    }

    public static Board empty() {
        return new Board();
    }

    private void setBackRow(int rowIndex, PlayerColour colour) {
        board[rowIndex][0] = new Rook(colour);
        board[rowIndex][1] = new Knight(colour);
        board[rowIndex][2] = new Bishop(colour);
        board[rowIndex][3] = new Queen(colour);
        board[rowIndex][4] = new King(colour);
        board[rowIndex][5] = new Bishop(colour);
        board[rowIndex][6] = new Knight(colour);
        board[rowIndex][7] = new Rook(colour);
    }

    public Piece get(Coordinates coords) {
        if (coords.isOnBoard()) {
            return board[coords.getRow()][coords.getCol()];
        } else {
            return null;
        }
    }

    public History move(Coordinates from, Coordinates to) {
        History history = new History();
        history.setLastPiece(getLastPiece());
        boolean isEnPassant = isEnPassant(from, to);

        Piece piece = get(from);
        lastPiece = piece;
        piece.move();
        setAndStore(to, piece, history);
        setAndStore(from, null, history);
        if (isEnPassant) {
            setAndStore(to.plus(-piece.getDirection(), 0), null, history);
        }

        Collections.reverse(history); // Return history in order to reverse
        return history;
    }

    public Piece getLastPiece() {
        return lastPiece;
    }

    public void reverse(History history, Piece piece) {
        piece.reverseMove();
        lastPiece = history.getLastPiece();
        for (HistoryItem item : history) {
            Coordinates coords = item.getCoords();
            placePiece(coords, item.getPiece());
        }
    }

    private void setAndStore(Coordinates coords, Piece piece, History history) {
        Piece oldPiece = get(coords);
        board[coords.getRow()][coords.getCol()] = piece;
        history.add(new HistoryItem(oldPiece, coords));
    }

    public void placePiece(Coordinates coords, Piece piece) {
        board[coords.getRow()][coords.getCol()] = piece;
    }

    public Coordinates findKing(PlayerColour playerColour) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Coordinates coords = new Coordinates(i, j);
                Piece piece = get(coords);
                if (piece != null && piece.getColour() == playerColour && piece.getType() == Piece.PieceType.KING) {
                    return coords;
                }
            }
        }
        return null;
    }

    public boolean isCheck(PlayerColour playerColour) {
        Coordinates kingCoords = findKing(playerColour);
        for (Coordinates coord: allCoordinates()) {
            Piece piece = get(coord);
            if (piece != null &&
                piece.getMovesBeforeCheck(coord, this)
                    .contains(new Move(coord, kingCoords))) {
                return true;
            }
        }
        return false;
    }

    public boolean isCheckmate(PlayerColour playerColour) {
        return isCheck(playerColour) && !canMove(playerColour);
    }

    public boolean isStalemate(PlayerColour playerColour) {
        return !isCheck(playerColour) && !canMove(playerColour);
    }

    public boolean canMove(PlayerColour playerColour) {
        for (Coordinates coord: allCoordinates()) {
            Piece piece = get(coord);
            if (piece != null && piece.getColour() == playerColour && !piece.getAllowedMoves(coord, this).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private List<Coordinates> allCoordinates() {
        List<Coordinates> coords = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                coords.add(new Coordinates(i, j));
            }
        }
        return coords;
    }

    public boolean testMove(Piece piece, Coordinates from, Coordinates to) {
        History history = move(from, to);
        boolean result = !isCheck(piece.getColour());
        reverse(history, piece);
        return result;
    }

    private boolean isEnPassant(Coordinates from, Coordinates to) {
        return get(to) == null &&
                to.difference(from).isDiagonal() &&
                get(from).getType() == Piece.PieceType.PAWN;
    }
}
