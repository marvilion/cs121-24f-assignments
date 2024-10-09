import java.util.*;

public class King extends Piece {
    public King(Color c) { 
        super(c); 
    }
    // implement appropriate methods

    public String toString() {
	    return (this.color() == Color.WHITE ? "w" : "b") + "k";
    }

    public List<String> moves(Board b, String loc) {
        Board.validateLocation(loc);

        List<String> validMoves = new ArrayList<>();
        int[] indices = b.convertLocation(loc);
        int row = indices[0];
        int col = indices[1];

        int[][] kingMoves = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1},
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };

        for (int[] move : kingMoves) {
            int newRow = row + move[0];
            int newCol = col + move[1];

            b.addMoveIfValid(b, newRow, newCol, validMoves, this.color());
        }

        return validMoves;
    }

}