import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Chess {

    public static void main(String[] args) {
	if (args.length != 2) {
	    System.out.println("Usage: java Chess layout moves");
	}
	Piece.registerPiece(new KingFactory());
	Piece.registerPiece(new QueenFactory());
	Piece.registerPiece(new KnightFactory());
	Piece.registerPiece(new BishopFactory());
	Piece.registerPiece(new RookFactory());
	Piece.registerPiece(new PawnFactory());
	Board.theBoard().registerListener(new Logger());
	// args[0] is the layout file name
	// args[1] is the moves file name
	// Put your code to read the layout file and moves files
	// here.
	String layoutFile = args[0];
	String movesFile = args[1];
	

	try {
		List<String> layoutList = Files.readAllLines(Paths.get(layoutFile));
		for (String line : layoutList) {
			if (!line.startsWith("#") && !line.trim().isEmpty()) {
				parseLayoutLine(line);
			}
		}
	} catch (IOException e) {
		System.out.println("Error while reading layout file.");
		e.printStackTrace();
	}
	

	try {
		List<String> movesList = Files.readAllLines(Paths.get(movesFile));
		for (String line : movesList) {
			if (!line.startsWith("#") && !line.trim().isEmpty()) {
				parseMoveLine(line);
			}
		}
	} catch (IOException e) {
		System.out.println("Error while reading moves file.");
		e.printStackTrace();
	}



	// Leave the following code at the end of the simulation:
	System.out.println("Final board:");
	Board.theBoard().iterate(new BoardPrinter());
    }

	private static void parseLayoutLine(String line) {
        // format must be xn=cp
        String[] parts = line.split("=");
        if (parts.length != 2) throw new IllegalArgumentException("Invalid layout line: " + line);

        String loc = parts[0];
        String pieceCode = parts[1];
        Board.validateLocation(loc);
		validatePieceCode(pieceCode);
        
        Board.theBoard().addPiece(Piece.createPiece(pieceCode), loc);
    }

    private static void parseMoveLine(String line) {
        // format must be xn-ym
        String[] parts = line.split("-");
        if (parts.length != 2) throw new IllegalArgumentException("Invalid move line: " + line);

        String from = parts[0]; 
        String to = parts[1]; 

		Board.validateLocation(from);
		Board.validateLocation(to);

        Board.theBoard().movePiece(from, to);
    }

	private static void validatePieceCode(String pieceCode) {
        if (pieceCode.length() != 2) {
            throw new IllegalArgumentException("Invalid piece code: " + pieceCode);
        }

        char color = pieceCode.charAt(0);
        char piece = pieceCode.charAt(1);

        if (color != 'w' && color != 'b') {
            throw new IllegalArgumentException("Invalid color: " + color);
        }

        if (piece != 'k' && piece != 'q' && piece != 'n' && piece != 'b' && piece != 'r' && piece != 'p') {
            throw new IllegalArgumentException("Invalid piece: " + piece);
        }
    }
}