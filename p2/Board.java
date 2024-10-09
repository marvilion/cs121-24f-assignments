import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Board {


    private static Board board = null;
    private final int BOARD_SIZE = 8;
    private Piece[][] pieces = new Piece[BOARD_SIZE][BOARD_SIZE];
    private List<BoardListener> listeners = new ArrayList<>();

    private Map<String, Integer> charMap = Map.of(
    "a", 0,
    "b", 1,
    "c", 2,
    "d", 3,
    "e", 4,
    "f", 5,
    "g", 6,
    "h", 7
    );


    private Board() { }
    
    public static Board theBoard() {
	    if (board == null) {
            board = new Board();
        }
        return board;
    }

    // Returns piece at given loc or null if no such piece
    // exists
    public Piece getPiece(String loc) {
        if (loc.length() != 2) {
            throw new IllegalArgumentException("Invalid location format: " + loc);
        }

        String firstChar = Character.toString(loc.charAt(0));
        int secondInt = Character.getNumericValue((loc.charAt(1)) - 1);

        if (!charMap.containsKey(firstChar) || secondInt < 0 || secondInt >= BOARD_SIZE) {
            throw new IllegalArgumentException("Invalid board location: " + loc);
        }
        
        return pieces[charMap.get(firstChar)][secondInt];
    }

    public void addPiece(Piece p, String loc) {
        String firstChar = Character.toString(loc.charAt(0));
        int secondInt = Character.getNumericValue(loc.charAt(1)) - 1;

        if (getPiece(loc) != null) {
            throw new IllegalStateException("Location already occupied: " + loc);
        }
    
        if (charMap.containsKey(firstChar) && secondInt >= 0 && secondInt < BOARD_SIZE) {

            pieces[charMap.get(firstChar)][secondInt] = p;
        } else {
            throw new IllegalArgumentException("Invalid arguments");
        }
    }

    public void movePiece(String from, String to) {
        Piece piece = getPiece(from);

        if(piece == null) throw new IllegalArgumentException("No piece at that location");

        List<String> validMoves = piece.moves(board, from);

        if (validMoves.contains(to)) {        
            Piece captured = getPiece(to);

            int[] indicesFrom = convertLocation(from);
            int[] indicesTo = convertLocation(to);
            pieces[indicesFrom[0]][indicesFrom[1]] = null;
            pieces[indicesTo[0]][indicesTo[1]] = piece;

            notifyMove(from, to, piece);

            if (captured != null) {
                notifyCapture(piece, captured);
            }

        }
        else {
            throw new IllegalArgumentException("Invalid move");
        }
    }

    public void clear() {
	    for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                pieces[row][col] = null;
            }
        }
    }

    public void registerListener(BoardListener bl) {
	    listeners.add(bl);
    }

    public void removeListener(BoardListener bl) {
	    listeners.remove(bl);
    }

    public void removeAllListeners() {
        listeners.clear();
    }

    private void notifyMove(String from, String to, Piece p) {
        for (BoardListener bl : listeners) {
            bl.onMove(from, to, p);
        }
    }

    private void notifyCapture(Piece attacker, Piece captured) {
        for (BoardListener bl : listeners) {
            bl.onCapture(attacker, captured);
        }
    }

    public void iterate(BoardInternalIterator bi) {
	    for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {

                String location = convertIndexToLocation(row, col);
                Piece piece = getPiece(location);
                
                bi.visit(location, piece);
            }
        }
    }

    public int[] convertLocation(String loc) {
        if (loc.length() != 2 || charMap.get(String.valueOf(loc.charAt(0))) == null) {
            throw new IllegalArgumentException("Invalid location: " + loc);
        }
        
        int row = charMap.get(String.valueOf(loc.charAt(0)));
        int col = Character.getNumericValue(loc.charAt(1)) - 1; // -1 to account for difference
        

        if (col < 0 || col > 7 || row < 0 || row > 7) {
            throw new IllegalArgumentException("Invalid board location: " + loc);
        }
    
        return new int[] { row, col };  // return as array
    }

    public String convertIndexToLocation(int row, int col) {
        if (row < 0 || row > 7 || col < 0 || col > 7) {
            return null;
        }

        char rowChar = (char) ('a' + row);
        
    
        char colChar = (char) ('1' + col);
    

    
        return "" + rowChar + colChar;
    }

    public static void validateLocation(String loc) {
        if (loc.length() != 2) {
            throw new IllegalArgumentException("Invalid location: " + loc);
        }

        char col = loc.charAt(0);
        char row = loc.charAt(1);

        if (col < 'a' || col > 'h') {
            throw new IllegalArgumentException("Invalid column at location: " + col);
        }

        if (row < '1' || row > '8') {
            throw new IllegalArgumentException("Invalid row at location: " + row);
        }


	}

    public boolean addMoveIfValid(Board b, int row, int col, List<String> possibleMoves, Color currentPieceColor) {
        if (row < 0 || row >= 8 || col < 0 || col >= 8) {
            return false;  // invalid move out of bounds
        }

        String loc = b.convertIndexToLocation(row, col);
        Piece piece = b.getPiece(loc);

        if (piece == null) {
            // empty square is valid
            possibleMoves.add(loc);
            return true;  // keep going
        } else if (piece.color() != currentPieceColor) {
            // enemy piece, valid move but stop right after
            possibleMoves.add(loc);
            return false; 
        } else {
            // friendly piece case, stop here
            return false; 
        }
    }
}