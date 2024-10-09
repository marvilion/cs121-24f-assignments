import java.util.*;

abstract public class Piece {
    private Color pieceColor;

    private static Map <String, PieceFactory> pieceFactoryMap = new HashMap<>();

    public Piece(Color color) {
        this.pieceColor = color;
    }
    public static void registerPiece(PieceFactory pf) {
	    pieceFactoryMap.put(String.valueOf(pf.symbol()), pf);
    }

    public static Piece createPiece(String name) {
        if (name.length() != 2) {
            throw new IllegalArgumentException("Invalid piece name: " + name);
        }
    
        char colorChar = name.charAt(0);
        if (colorChar != 'w' && colorChar != 'b') {
            throw new IllegalArgumentException("Invalid color in the piece's name: " + name);
        }
    
        Color pieceColor = (colorChar == 'w') ? Color.WHITE : Color.BLACK;
    
        char pieceChar = name.charAt(1);
    
        PieceFactory factory = pieceFactoryMap.get(String.valueOf(pieceChar));
        if (factory != null) {
            return factory.create(pieceColor); 
        }
    
        throw new IllegalArgumentException("Invalid string plugged into createPiece: " + name);
    }

    public Color color() {
	// You should write code here and just inherit it in
	// subclasses. For this to work, you should know
	// that subclasses can access superclass fields.
	    return this.pieceColor;
    }

    abstract public String toString();

    abstract public List<String> moves(Board b, String loc);
}