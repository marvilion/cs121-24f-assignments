import java.util.*;

public class Knight extends Piece {
    public Knight(Color c) {
        super(c);
    }
    // implement appropriate methods

    public String toString() {
	    return (this.color() == Color.WHITE ? "w" : "b") + "n";
    }

    public List<String> moves(Board b, String loc) {
        Board.validateLocation(loc);

        List<String> validMoves = new ArrayList<>();
        int[] indices = b.convertLocation(loc);
        int row = indices[0];
        int col = indices[1];
        
        int[][] knightMoves = {
            {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };
        
        for (int[] move : knightMoves) {
            int newRow = row + move[0];
            int newCol = col + move[1];
            
            b.addMoveIfValid(b, newRow, newCol, validMoves, this.color());
        }
        
        return validMoves;
    }

}