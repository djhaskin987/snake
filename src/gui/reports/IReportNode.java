package gui.reports;

public interface IReportNode {
	/**
	 * Accepts a visitor
	 * 
	 * @param v the visitor
	 */
	public void accept(ReportVisitor v);
}
