public class BoardPrinter implements BoardInternalIterator {
    public void visit(String loc, Piece p) {
		Board.validateLocation(loc);
		if (p != null) {
			System.out.println(loc + "=" + p.toString());
		}
		else {
			System.out.println(loc + ": empty");
		}
    }
}