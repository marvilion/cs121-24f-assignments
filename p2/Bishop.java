import java.util.*;

public class Bishop extends Piece {
    public Bishop(Color c) {
        super(c);
    }
    // implement appropriate methods

    public String toString() {
	    return (this.color() == Color.WHITE ? "w" : "b") + "b";
    }

    public List<String> moves(Board b, String loc) {
        Board.validateLocation(loc);

        List<String> possibleMoves = new ArrayList<>();

        int[] indices = b.convertLocation(loc); 
        int row = indices[0];
        int col = indices[1];
 
        for (int i = 1; row + i < 8 && col + i < 8; i++) {
            if (!b.addMoveIfValid(b, row + i, col + i, possibleMoves, this.color())) break;
        }
    
        for (int i = 1; row - i >= 0 && col + i < 8; i++) {
            if (!b.addMoveIfValid(b, row - i, col + i, possibleMoves, this.color())) break;
        }
    
        for (int i = 1; row + i < 8 && col - i >= 0; i++) {
            if (!b.addMoveIfValid(b, row + i, col - i, possibleMoves, this.color())) break;
        }
    
        for (int i = 1; row - i >= 0 && col - i >= 0; i++) {
            if (!b.addMoveIfValid(b, row - i, col - i, possibleMoves, this.color())) break;
        }
    
        return possibleMoves;
    }

}