import java.util.*;

public class Rook extends Piece {
    public Rook(Color c) {
        super(c);
    }
    // implement appropriate methods

    public String toString() {
	    return (this.color() == Color.WHITE ? "w" : "b") + "r";
    }

    public List<String> moves(Board b, String loc) {
        Board.validateLocation(loc);

        List<String> possibleMoves = new ArrayList<>();
        
        int[] indices = b.convertLocation(loc);
        int row = indices[0];
        int col = indices[1];

        for (int i = row + 1; i < 8; i++) {
            if (!b.addMoveIfValid(b, i, col, possibleMoves, this.color())) break;
        }

        for (int i = row - 1; i >= 0; i--) {
            if (!b.addMoveIfValid(b, i, col, possibleMoves, this.color())) break;
        }

        for (int i = col + 1; i < 8; i++) {
            if (!b.addMoveIfValid(b, row, i, possibleMoves, this.color())) break;
        }

        for (int i = col - 1; i >= 0; i--) {
            if (!b.addMoveIfValid(b, row, i, possibleMoves, this.color())) break;
        }

        return possibleMoves;
    }

}