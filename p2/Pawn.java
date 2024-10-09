import java.util.*;

public class Pawn extends Piece {
    public Pawn(Color c) {
        super(c);
    }
    // implement appropriate methods

    public String toString() {
        return (this.color() == Color.WHITE ? "w" : "b") + "p";
    }

    public List<String> moves(Board b, String loc) {
        Board.validateLocation(loc);        

        List<String> possibleMoves = new ArrayList<>();
        int[] indices = b.convertLocation(loc);
        int row = indices[0]; 
        int col = indices[1]; 
    
        int direction = (color() == Color.WHITE) ? 1 : -1;
    
        String forwardMove = b.convertIndexToLocation(row, col + direction);
        if (b.getPiece(forwardMove) == null) {
            possibleMoves.add(forwardMove);
    
            if ((color() == Color.WHITE && col == 1) || (color() == Color.BLACK && col == 6)) {
                String doubleMove = b.convertIndexToLocation(row, col + 2 * direction); 
                if (b.getPiece(doubleMove) == null) {
                    possibleMoves.add(doubleMove);
                }
            }
        }
    
        String captureLeft = b.convertIndexToLocation(row - 1, col + direction);
        String captureRight = b.convertIndexToLocation(row + 1, col + direction);
    
        if (row - 1 >= 0 && b.getPiece(captureLeft) != null && b.getPiece(captureLeft).color() != this.color()) {
            possibleMoves.add(captureLeft);
        }
        if (row + 1 < 8 && b.getPiece(captureRight) != null && b.getPiece(captureRight).color() != this.color()) {
            possibleMoves.add(captureRight);
        }
    
        return possibleMoves;
    }
}