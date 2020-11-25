package training.chessington.model;

import training.chessington.model.pieces.Piece;

import java.util.ArrayList;

public class History extends ArrayList<HistoryItem> {
    public Piece getLastPiece() {
        return lastPiece;
    }

    public void setLastPiece(Piece lastPiece) {
        this.lastPiece = lastPiece;
    }

    private Piece lastPiece;
}
