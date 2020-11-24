package training.chessington.model;

import org.junit.Test;
import training.chessington.model.pieces.King;
import training.chessington.model.pieces.Queen;
import training.chessington.model.pieces.Rook;

import static org.assertj.core.api.Assertions.assertThat;

public class StalemateTest {
    @Test
    public void checkmateIsNotStalemate() {
        // Arrange
        Board board = Board.empty();
        board.placePiece(new Coordinates(0, 7), new Rook(PlayerColour.WHITE));
        board.placePiece(new Coordinates(4,5), new King(PlayerColour.WHITE));
        board.placePiece(new Coordinates(4, 7), new King(PlayerColour.BLACK));

        // Act
        boolean isStalemate = board.isStalemate(PlayerColour.BLACK);

        // Assert
        assertThat(isStalemate).isFalse();
    }

    @Test
    public void detectsStalemate() {
        // Arrange
        Board board = Board.empty();
        board.placePiece(new Coordinates(5, 6), new Queen(PlayerColour.WHITE));
        board.placePiece(new Coordinates(6,5), new King(PlayerColour.WHITE));
        board.placePiece(new Coordinates(7, 7), new King(PlayerColour.BLACK));

        // Act
        boolean isStalemate = board.isStalemate(PlayerColour.BLACK);

        // Assert
        assertThat(isStalemate).isTrue();
    }
}
