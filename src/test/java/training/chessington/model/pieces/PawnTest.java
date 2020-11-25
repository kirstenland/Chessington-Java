package training.chessington.model.pieces;

import org.junit.Test;
import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PawnTest {
    @Test
    public void whitePawnCanMoveUpOneSquare() {
        // Arrange
        Board board = Board.empty();
        Piece pawn = new Pawn(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(6, 4);
        board.placePiece(coords, pawn);

        // Act
        List<Move> moves = pawn.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).contains(new Move(coords, coords.plus(-1, 0)));
    }

    @Test
    public void blackPawnCanMoveDownOneSquare() {
        // Arrange
        Board board = Board.empty();
        Piece pawn = new Pawn(PlayerColour.BLACK);
        Coordinates coords = new Coordinates(1, 4);
        board.placePiece(coords, pawn);

        // Act
        List<Move> moves = pawn.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).contains(new Move(coords, coords.plus(1, 0)));
    }

    @Test
    public void whitePawnCanMoveUpTwoSquaresIfNotMoved() {
        // Arrange
        Board board = Board.empty();
        Piece pawn = new Pawn(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(6, 4);
        board.placePiece(coords, pawn);

        // Act
        List<Move> moves = pawn.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).contains(new Move(coords, coords.plus(-2, 0)));
    }

    @Test
    public void blackPawnCanMoveDownTwoSquaresIfNotMoved() {
        // Arrange
        Board board = Board.empty();
        Piece pawn = new Pawn(PlayerColour.BLACK);
        Coordinates coords = new Coordinates(1, 4);
        board.placePiece(coords, pawn);

        // Act
        List<Move> moves = pawn.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).contains(new Move(coords, coords.plus(2, 0)));
    }

    @Test
    public void whitePawnCannotMoveUpTwoSquaresIfAlreadyMoved() {
        // Arrange
        Board board = Board.empty();
        Piece pawn = new Pawn(PlayerColour.WHITE);
        Coordinates initial = new Coordinates(6, 4);
        board.placePiece(initial, pawn);

        Coordinates moved = initial.plus(-1, 0);
        board.move(initial, moved);

        // Act
        List<Move> moves = pawn.getAllowedMoves(moved, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(moved, moved.plus(-2, 0)));
    }

    @Test
    public void blackPawnCannotMoveDownTwoSquaresIfAlreadyMoved() {
        // Arrange
        Board board = Board.empty();
        Piece pawn = new Pawn(PlayerColour.BLACK);
        Coordinates initial = new Coordinates(1, 4);
        board.placePiece(initial, pawn);

        Coordinates moved = initial.plus(1, 0);
        board.move(initial, moved);

        // Act
        List<Move> moves = pawn.getAllowedMoves(moved, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(moved, moved.plus(2, 0)));
    }

    @Test
    public void pawnsCannotMoveIfPieceInFront() {
        // Arrange
        Board board = Board.empty();

        Piece blackPawn = new Pawn(PlayerColour.BLACK);
        Coordinates blackCoords = new Coordinates(3, 4);
        board.placePiece(blackCoords, blackPawn);

        Piece whitePawn = new Pawn(PlayerColour.WHITE);
        Coordinates whiteCoords = new Coordinates(4, 4);
        board.placePiece(whiteCoords, whitePawn);

        // Act
        List<Move> blackMoves = blackPawn.getAllowedMoves(blackCoords, board);
        List<Move> whiteMoves = whitePawn.getAllowedMoves(whiteCoords, board);

        // Assert
        assertThat(blackMoves).isEmpty();
        assertThat(whiteMoves).isEmpty();
    }

    @Test
    public void pawnsCannotMoveTwoSquaresIfPieceTwoInFront() {
        // Arrange
        Board board = Board.empty();

        Piece blackPawn = new Pawn(PlayerColour.BLACK);
        Coordinates blackCoords = new Coordinates(2, 4);
        board.placePiece(blackCoords, blackPawn);

        Piece whitePawn = new Pawn(PlayerColour.WHITE);
        Coordinates whiteCoords = new Coordinates(4, 4);
        board.placePiece(whiteCoords, whitePawn);

        // Act
        List<Move> blackMoves = blackPawn.getAllowedMoves(blackCoords, board);
        List<Move> whiteMoves = whitePawn.getAllowedMoves(whiteCoords, board);

        // Assert
        assertThat(blackMoves).doesNotContain(new Move(blackCoords, blackCoords.plus(2, 0)));
        assertThat(whiteMoves).doesNotContain(new Move(blackCoords, blackCoords.plus(-2, 0)));
    }

    @Test
    public void whitePawnsCannotMoveAtTopOfBoard() {
        // Arrange
        Board board = Board.empty();
        Piece pawn = new Pawn(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(0, 4);
        board.placePiece(coords, pawn);

        // Act
        List<Move> moves = pawn.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).isEmpty();
    }

    @Test
    public void blackPawnsCannotMoveAtBottomOfBoard() {
        // Arrange
        Board board = Board.empty();
        Piece pawn = new Pawn(PlayerColour.BLACK);
        Coordinates coords = new Coordinates(7, 4);
        board.placePiece(coords, pawn);

        // Act
        List<Move> moves = pawn.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).isEmpty();
    }

    @Test
    public void whitePawnsCanCaptureDiagonally() {
        // Arrange
        Board board = Board.empty();
        Piece pawn = new Pawn(PlayerColour.WHITE);
        Piece enemyPiece = new Rook(PlayerColour.BLACK);
        Coordinates pawnCoords = new Coordinates(4, 4);
        board.placePiece(pawnCoords, pawn);

        Coordinates enemyCoords = pawnCoords.plus(-1, 1);
        board.placePiece(enemyCoords, enemyPiece);

        // Act
        List<Move> moves = pawn.getAllowedMoves(pawnCoords, board);

        // Assert
        assertThat(moves).contains(new Move(pawnCoords, enemyCoords));
    }

    @Test
    public void blackPawnsCanCaptureDiagonally() {
        // Arrange
        Board board = Board.empty();
        Piece pawn = new Pawn(PlayerColour.BLACK);
        Piece enemyPiece = new Rook(PlayerColour.WHITE);
        Coordinates pawnCoords = new Coordinates(3, 4);
        board.placePiece(pawnCoords, pawn);

        Coordinates enemyCoords = pawnCoords.plus(1, 1);
        board.placePiece(enemyCoords, enemyPiece);

        // Act
        List<Move> moves = pawn.getAllowedMoves(pawnCoords, board);

        // Assert
        assertThat(moves).contains(new Move(pawnCoords, enemyCoords));
    }

    @Test
    public void pawnsCannotMoveDiagonallyOffBoard() {
        // Arrange
        Board board = Board.empty();

        Piece blackPawn = new Pawn(PlayerColour.BLACK);
        Coordinates blackCoords = new Coordinates(3, 0);
        board.placePiece(blackCoords, blackPawn);

        Piece whitePawn = new Pawn(PlayerColour.WHITE);
        Coordinates whiteCoords = new Coordinates(4, 0);
        board.placePiece(whiteCoords, whitePawn);

        // Act
        List<Move> blackMoves = blackPawn.getAllowedMoves(blackCoords, board);
        List<Move> whiteMoves = whitePawn.getAllowedMoves(whiteCoords, board);

        // Assert
        assertThat(blackMoves).isEmpty();
        assertThat(whiteMoves).isEmpty();
    }

    @Test
    public void whitePawnsCannotMoveDiagonallyNotToCapture() {
        // Arrange
        Board board = Board.empty();
        Piece pawn = new Pawn(PlayerColour.WHITE);
        Piece friendlyPiece = new Rook(PlayerColour.WHITE);
        Coordinates pawnCoords = new Coordinates(4, 4);
        board.placePiece(pawnCoords, pawn);

        Coordinates rookCoords = pawnCoords.plus(-1, 1);
        board.placePiece(rookCoords, friendlyPiece);

        // Act
        List<Move> moves = pawn.getAllowedMoves(pawnCoords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(pawnCoords, rookCoords));
        Coordinates otherDiagonal = pawnCoords.plus(-1, -1);
        assertThat(moves).doesNotContain(new Move(pawnCoords, otherDiagonal));
    }

    @Test
    public void blackPawnsCannotMoveDiagonallyNotToCapture() {
        // Arrange
        Board board = Board.empty();
        Piece pawn = new Pawn(PlayerColour.BLACK);
        Piece friendlyPiece = new Rook(PlayerColour.BLACK);
        Coordinates pawnCoords = new Coordinates(3, 4);
        board.placePiece(pawnCoords, pawn);

        Coordinates rookCoords = pawnCoords.plus(1, 1);
        board.placePiece(rookCoords, friendlyPiece);

        // Act
        List<Move> moves = pawn.getAllowedMoves(pawnCoords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(pawnCoords, rookCoords));
        Coordinates otherDiagonal = pawnCoords.plus(1, -1);
        assertThat(moves).doesNotContain(new Move(pawnCoords, otherDiagonal));
    }

    @Test
    public void blackPawnCanEnPassant() {
        // Arrange
        Board board = Board.empty();
        Piece pawn = new Pawn(PlayerColour.BLACK);
        Piece enemyPawn = new Pawn(PlayerColour.WHITE);

        Coordinates coords = new Coordinates(4, 4);
        board.placePiece(coords, pawn);

        Coordinates enemyCoords = coords.plus(2, 1);
        board.placePiece(enemyCoords, enemyPawn);

        board.move(enemyCoords, enemyCoords.plus(-2, 0));

        // Act
        List<Move> moves = pawn.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).contains(new Move(coords, coords.plus(1,1)));
    }

    @Test
    public void whitePawnCanEnPassant(){
        // Arrange
        Board board = Board.empty();
        Piece pawn = new Pawn(PlayerColour.WHITE);
        Piece enemyPawn = new Pawn(PlayerColour.BLACK);

        Coordinates coords = new Coordinates(3, 4);
        board.placePiece(coords, pawn);

        Coordinates enemyCoords = coords.plus(-2, 1);
        board.placePiece(enemyCoords, enemyPawn);

        board.move(enemyCoords, enemyCoords.plus(2, 0));

        // Act
        List<Move> moves = pawn.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).contains(new Move(coords, coords.plus(-1,1)));
    }

    @Test
    public void cannotEnPassantIfPieceToTakeMovedOne(){
        // Arrange
        Board board = Board.empty();
        Piece pawn = new Pawn(PlayerColour.WHITE);
        Piece enemyPawn = new Pawn(PlayerColour.BLACK);

        Coordinates coords = new Coordinates(3, 4);
        board.placePiece(coords, pawn);

        Coordinates enemyCoords = coords.plus(-2, 1);
        board.placePiece(enemyCoords, enemyPawn);

        board.move(enemyCoords, enemyCoords.plus(1, 0));
        board.move(enemyCoords.plus(1, 0), enemyCoords.plus(2, 0));

        // Act
        List<Move> moves = pawn.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(coords, coords.plus(-1,1)));
    }

    @Test
    public void cannotEnPassantIfMovesInBetween(){
        // Arrange
        Board board = Board.empty();
        Piece pawn = new Pawn(PlayerColour.WHITE);
        Piece enemyPawn = new Pawn(PlayerColour.BLACK);
        Piece rook = new Rook(PlayerColour.WHITE);
        Piece enemyRook = new Rook(PlayerColour.BLACK);

        Coordinates coords = new Coordinates(3, 4);
        board.placePiece(coords, pawn);

        Coordinates enemyCoords = coords.plus(-2, 1);
        board.placePiece(enemyCoords, enemyPawn);

        Coordinates rookCoords = new Coordinates(0, 0);
        board.placePiece(rookCoords, rook);

        Coordinates enemyRookCoords = new Coordinates(7,7);
        board.placePiece(enemyRookCoords, enemyRook);

        board.move(enemyCoords, enemyCoords.plus(2, 0));
        board.move(rookCoords, rookCoords.plus(1,0));
        board.move(enemyRookCoords, enemyRookCoords.plus(-1, 0));

        // Act
        List<Move> moves = pawn.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(coords, coords.plus(-1,1)));
    }


}
