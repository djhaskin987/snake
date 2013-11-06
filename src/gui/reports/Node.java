package gui.reports;

public interface Node {
	/**
	 * Accepts a visitor
	 * 
	 * @param v the visitor
	 */
	public void accept(ReportVisitor v);
}
