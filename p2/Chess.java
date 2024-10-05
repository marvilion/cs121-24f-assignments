import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Chess {

    public static void main(String[] args) {
	if (args.length != 2) {
	    System.out.println("Usage: java Chess layout moves");
	}
	// Piece.registerPiece(new KingFactory());
	// Piece.registerPiece(new QueenFactory());
	// Piece.registerPiece(new KnightFactory());
	// Piece.registerPiece(new BishopFactory());
	// Piece.registerPiece(new RookFactory());
	// Piece.registerPiece(new PawnFactory());
	// Board.theBoard().registerListener(new Logger());
	// args[0] is the layout file name
	// args[1] is the moves file name
	// Put your code to read the layout file and moves files
	// here.
	String layout = args[0];
	String moves = args[1];

	List<String> list = new ArrayList<>();

		try (Stream<String> stream = Files.lines(Paths.get(layout))) {


			list = stream
					.filter(line -> !line.startsWith("#"))
					.collect(Collectors.toList());
		} catch (IOException e) {
						e.printStackTrace();
		}

		System.out.println(list.get(1).charAt(0));




	// Leave the following code at the end of the simulation:
	// System.out.println("Final board:");
	// Board.theBoard().iterate(new BoardPrinter());
    }

	public void setUpBoard(List<String> layoutFile){

	}
}