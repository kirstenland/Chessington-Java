package training.chessington.model.pieces;

import org.junit.Test;
import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.List;

import static training.chessington.model.pieces.PieceAssert.*;
import static org.assertj.core.api.Assertions.*;

public class KnightTest {

    @Test
    public void knightCanMove() {
        // Arrange
        Board board = Board.empty();
        Piece knight = new Knight(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(3, 4);
        board.placePiece(coords, knight);

        // Act
        List<Move> moves = knight.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).contains(new Move(coords, coords.plus(1, 2)));
        assertThat(moves).contains(new Move(coords, coords.plus(2, 1)));
        assertThat(moves).contains(new Move(coords, coords.plus(1, -2)));
        assertThat(moves).contains(new Move(coords, coords.plus(2, -1)));
        assertThat(moves.contains(new Move(coords, coords.plus(-1, 2))));
        assertThat(moves.contains(new Move(coords, coords.plus(-1, -2))));
        assertThat(moves.contains(new Move(coords, coords.plus(-2, 1))));
        assertThat(moves.contains(new Move(coords, coords.plus(-2, -1))));
        assertThat(moves).hasSize(8);
    }

    @Test
    public void knightCanTakeEnemy() {
        // Arrange
        Board board = Board.empty();
        Piece knight = new Knight(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(3, 4);
        Piece enemy = new Pawn(PlayerColour.BLACK);
        Coordinates enemyCoords = coords.plus(1, 2);

        board.placePiece(coords, knight);
        board.placePiece(enemyCoords, enemy);

        // Act
        List<Move> moves = knight.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).contains(new Move(coords, enemyCoords));
    }

    @Test
    public void knightCannotTakeAlly() {
        // Arrange
        Board board = Board.empty();
        Piece knight = new Knight(PlayerColour.BLACK);
        Coordinates coords = new Coordinates(3, 4);
        Piece ally = new Pawn(PlayerColour.BLACK);
        Coordinates allyCoords = coords.plus(1, 2);

        board.placePiece(coords, knight);
        board.placePiece(allyCoords, ally);

        // Act
        List<Move> moves = knight.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(coords, allyCoords));
    }

    @Test
    public void knightCanJump() {
        // Arrange
        Board board = Board.empty();
        Piece knight = new Knight(PlayerColour.BLACK);
        Coordinates coords = new Coordinates(3, 4);
        Piece ally = new Pawn(PlayerColour.BLACK);

        board.placePiece(coords, knight);
        board.placePiece(coords.plus(1, 1), ally);

        // Act
        List<Move> moves = knight.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).contains(new Move(coords, coords.plus(1, 2)));
    }

    @Test
    public void knightCannotMoveOffBoard() {
        // Arrange
        Board board = Board.empty();
        Piece knight = new Knight(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(7, 0);

        board.placePiece(coords, knight);

        // Act
        List<Move> moves = knight.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).hasSize(2);
        for (Move move: moves) {
            assertThat(move.getTo().isOnBoard());
        }
    }
}
