package training.chessington.model;

import training.chessington.model.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class Board {

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

    public void move(Coordinates from, Coordinates to) {
        Piece piece = board[from.getRow()][from.getCol()];
        piece.setMoved();
        board[to.getRow()][to.getCol()] = piece;
        board[from.getRow()][from.getCol()] = null;
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
        Piece takenPiece = board[to.getRow()][to.getCol()];
        board[to.getRow()][to.getCol()] = piece;
        board[from.getRow()][from.getCol()] = null;

        boolean result = !isCheck(piece.getColour());

        board[to.getRow()][to.getCol()] = takenPiece;
        board[from.getRow()][from.getCol()] = piece;

        return result;
    }
}
