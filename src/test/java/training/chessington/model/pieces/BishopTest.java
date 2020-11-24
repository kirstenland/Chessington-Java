package training.chessington.model.pieces;

import org.junit.Test;
import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.List;

import static training.chessington.model.pieces.PieceAssert.*;
import static org.assertj.core.api.Assertions.*;

public class BishopTest {
    @Test
    public void bishopCanMoveAnyDirectionDiagonally() {
        // Arrange
        Board board = Board.empty();
        Piece bishop = new Bishop(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(6, 4);
        board.placePiece(coords, bishop);

        // Act
        List<Move> moves = bishop.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).contains(new Move(coords, coords.plus(1, 1)));
        assertThat(moves).contains(new Move(coords, coords.plus(1, -1)));
        assertThat(moves).contains(new Move(coords, coords.plus(-1, 1)));
        assertThat(moves).contains(new Move(coords, coords.plus(-1, -1)));
    }

    @Test
    public void bishopCanMoveAnyAmountDiagonally() {
        // Arrange
        Board board = Board.empty();
        Piece bishop = new Bishop(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(6, 4);
        board.placePiece(coords, bishop);

        // Act
        List<Move> moves = bishop.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).contains(new Move(coords, coords.plus(1, 1)));
        assertThat(moves).contains(new Move(coords, coords.plus(-2, 2)));
        assertThat(moves).contains(new Move(coords, coords.plus(-3, -3)));
    }

    @Test
    public void bishopCannotMoveThroughAllies() {
        // Arrange
        Board board = Board.empty();
        Piece bishop = new Bishop(PlayerColour.WHITE);
        Piece ally = new Bishop(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(4, 4);
        Coordinates allyCoords = coords.plus(1, 1);

        board.placePiece(coords, bishop);
        board.placePiece(allyCoords, ally);

        // Act
        List<Move> moves = bishop.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(coords, coords.plus(2, 2)));

    }

    @Test
    public void bishopCannotMoveThroughEnemies() {
        // Arrange
        Board board = Board.empty();
        Piece bishop = new Bishop(PlayerColour.WHITE);
        Piece enemy = new Bishop(PlayerColour.BLACK);
        Coordinates coords = new Coordinates(4, 4);
        Coordinates enemyCoords = coords.plus(1, 1);

        board.placePiece(coords, bishop);
        board.placePiece(enemyCoords, enemy);

        // Act
        List<Move> moves = bishop.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(coords, coords.plus(2, 2)));
    }

    @Test
    public void bishopCanTakeEnemy() {
        // Arrange
        Board board = Board.empty();
        Piece bishop = new Bishop(PlayerColour.WHITE);
        Piece enemy = new Bishop(PlayerColour.BLACK);
        Coordinates coords = new Coordinates(4, 4);
        Coordinates enemyCoords = coords.plus(1, 1);

        board.placePiece(coords, bishop);
        board.placePiece(enemyCoords, enemy);

        // Act
        List<Move> moves = bishop.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).contains(new Move(coords, enemyCoords));
    }

    @Test
    public void bishopCannotTakeAlly() {
        // Arrange
        Board board = Board.empty();
        Piece bishop = new Bishop(PlayerColour.WHITE);
        Piece ally = new Bishop(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(4, 4);
        Coordinates allyCoords = coords.plus(1, 1);

        board.placePiece(coords, bishop);
        board.placePiece(allyCoords, ally);

        // Act
        List<Move> moves = bishop.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(coords, allyCoords));
    }
}
