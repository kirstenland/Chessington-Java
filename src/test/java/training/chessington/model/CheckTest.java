package training.chessington.model;

import org.junit.Test;
import training.chessington.model.pieces.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CheckTest {
    @Test
    public void threateningKingIsCheck() {
        // Arrange
        Board board = Board.empty();
        Piece king = new King(PlayerColour.BLACK);
        Coordinates coords = new Coordinates(6, 4);
        board.placePiece(coords, king);

        Piece bishop = new Bishop(PlayerColour.WHITE);
        Coordinates bishopCoords = coords.plus(1, 1);
        board.placePiece(bishopCoords, bishop);

        // Act
        boolean blackCheck = board.isCheck(PlayerColour.BLACK);

        // Assert
        assertThat(blackCheck).isTrue();
    }

    @Test
    public void cannotThreatenKingWithOwnPiece() {
        // Arrange
        Board board = Board.empty();
        Piece king = new King(PlayerColour.BLACK);
        Coordinates coords = new Coordinates(6, 4);
        board.placePiece(coords, king);

        Piece bishop = new Bishop(PlayerColour.BLACK);
        Coordinates bishopCoords = coords.plus(1, 1);
        board.placePiece(bishopCoords, bishop);

        // Act
        boolean blackCheck = board.isCheck(PlayerColour.BLACK);

        // Assert
        assertThat(blackCheck).isFalse();
    }

    @Test
    public void cannotMoveKingIntoCheck() {
        // Arrange
        Board board = Board.empty();
        Piece king = new King(PlayerColour.BLACK);
        Coordinates coords = new Coordinates(6, 4);
        board.placePiece(coords, king);

        Piece bishop = new Bishop(PlayerColour.WHITE);
        Coordinates bishopCoords = coords.plus(1, 2);
        board.placePiece(bishopCoords, bishop);

        // Act
        List<Move> moves = king.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(coords, coords.plus(0,1)));
    }

    @Test
    public void mustProtectKingCheck() {
        // Arrange
        Board board = Board.empty();
        Piece king = new King(PlayerColour.BLACK);
        Coordinates coords = new Coordinates(4, 4);
        board.placePiece(coords, king);

        Piece ally = new Bishop(PlayerColour.BLACK);
        Coordinates allyCoords = coords.plus(1, 0);
        board.placePiece(allyCoords, ally);

        Piece enemy = new Rook(PlayerColour.WHITE);
        Coordinates enemyCoords = coords.plus(2, 0);
        board.placePiece(enemyCoords, enemy);

        // Act
        List<Move> moves = ally.getAllowedMoves(allyCoords, board);

        // Assert
        assertThat(moves).isEmpty();
    }

    @Test
    public void mustMoveOutOfCheck() {
        // Arrange
        Board board = Board.empty();
        Piece king = new King(PlayerColour.BLACK);
        Coordinates coords = new Coordinates(4, 4);
        board.placePiece(coords, king);

        Piece ally = new Pawn(PlayerColour.BLACK);
        Coordinates allyCoords = new Coordinates(1, 0);
        board.placePiece(allyCoords, ally);

        Piece enemy = new Rook(PlayerColour.WHITE);
        Coordinates enemyCoords = coords.plus(2, 0);
        board.placePiece(enemyCoords, enemy);

        // Act
        List<Move> moves = ally.getAllowedMoves(allyCoords, board);

        // Assert
        assertThat(moves).isEmpty();
    }

    @Test
    public void canDetectCheckmate() {
        // Arrange
        Board board = Board.empty();
        board.placePiece(new Coordinates(0, 7), new Rook(PlayerColour.WHITE));
        board.placePiece(new Coordinates(4,5), new King(PlayerColour.WHITE));
        board.placePiece(new Coordinates(4, 7), new King(PlayerColour.BLACK));

        // Act
        boolean isCheckmateBlack = board.isCheckmate(PlayerColour.BLACK);
        boolean isCheckmateWhite = board.isCheckmate(PlayerColour.WHITE);

        // Assert
        assertThat(isCheckmateBlack).isTrue();
        assertThat(isCheckmateWhite).isFalse();
    }
}
