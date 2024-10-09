import java.util.Arrays;
import java.util.List;

public class Test {

    // Run "java -ea Test" to run with assertions enabled (If you run
    // with assertions disabled, the default, then assert statements
    // will not execute!)

    public static void test1() {
        Board b = Board.theBoard();
        Piece.registerPiece(new PawnFactory());
        Piece p = Piece.createPiece("bp");
        b.addPiece(p, "a3");
        assert b.getPiece("a3") == p;
    }

    public static void testRook() {
    Board board = Board.theBoard();
    Piece.registerPiece(new RookFactory());
    Piece whiteRook = Piece.createPiece("wr");
    board.addPiece(whiteRook, "e8");
    List<String> whiteRookMoves = whiteRook.moves(board, "e8");


    assert whiteRookMoves.containsAll(Arrays.asList(
        "a8","b8","c8","d8","f8","g8","h8","e7","e6",
        "e5","e4","e3","e2","e1"
    ));
    assert whiteRookMoves.size() == 14;

    board.movePiece("e8", "e1");

    assert board.getPiece("e8") == null;
    assert board.getPiece("e1") == whiteRook;


    board.clear();

}

public static void testQueen() {
    Board board = Board.theBoard();
    Piece.registerPiece(new QueenFactory());
    Piece blackQueen = Piece.createPiece("bq");
    board.addPiece(blackQueen, "d4");
    Piece.registerPiece(new RookFactory());
    Piece whiteRook = Piece.createPiece("wr");
    board.addPiece(whiteRook, "g1");
    assert board.getPiece("g1") == whiteRook;
    List<String> blackQueenMoves = blackQueen.moves(board, "d4");

    assert blackQueenMoves.containsAll(Arrays.asList(
        "e4", "f4", "g4", "h4", "c4", "b4", "a4", "d5", "d6", "d7", "d8", "d3", "d2", "d1", 
        "e5", "f6", "g7", "h8", "c5", "b6", "a7", "e3", "f2", "g1", "c3", "b2", "a1"
    ));

    board.movePiece("d4", "g1");

    assert board.getPiece("d4") == null;
    assert board.getPiece("g1") == blackQueen;


    board.clear();

}
    
    public static void main(String[] args) {
	test1();
    testRook();
    testQueen();
    }

}