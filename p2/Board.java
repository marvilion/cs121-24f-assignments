import java.util.Map;

public class Board {

    private final int BOARD_SIZE = 8;
    private Piece[][] pieces = new Piece[BOARD_SIZE][BOARD_SIZE];

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
	return null; // implement this
    }

    // Returns piece at given loc or null if no such piece
    // exists
    public Piece getPiece(String loc) {
        String firstChar = Character.toString(loc.charAt(0));
        int secondInt = Character.getNumericValue((loc.charAt(1)) - 1);
	    if (charMap.containsKey(firstChar) && secondInt >= 0 && secondInt <= BOARD_SIZE){
            return pieces[charMap.get(firstChar)][secondInt];
        }
        return null;
    }

    public void addPiece(Piece p, String loc) {
	throw new UnsupportedOperationException();
    }

    public void movePiece(String from, String to) {
	throw new UnsupportedOperationException();
    }

    public void clear() {
	throw new UnsupportedOperationException();
    }

    public void registerListener(BoardListener bl) {
	throw new UnsupportedOperationException();
    }

    public void removeListener(BoardListener bl) {
	throw new UnsupportedOperationException();
    }

    public void removeAllListeners() {
	throw new UnsupportedOperationException();
    }

    public void iterate(BoardInternalIterator bi) {
	throw new UnsupportedOperationException();
    }
}