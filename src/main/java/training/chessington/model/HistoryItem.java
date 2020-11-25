package training.chessington.model;

import lombok.Getter;
import training.chessington.model.pieces.Piece;

@Getter
public class HistoryItem {
    private Piece piece;
    private Coordinates coords;

    public HistoryItem(Piece piece, Coordinates coords) {
        this.piece = piece;
        this.coords = coords;
    }
}
