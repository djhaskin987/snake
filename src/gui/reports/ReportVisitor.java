package gui.reports;

public abstract class ReportVisitor {

	public ReportVisitor() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Visits the HeadingNode for formatting in report.
	 * 
	 * @param n the heading node
	 * 
	 * {@pre n != null}
	 * 
	 * {@post formatted}
	 */
	public abstract void visit(HeadingNode n);
	
	/**
	 * Visits the body node for formatting in report.
	 * 
	 * @param n the body node
	 * 
	 * {@pre n != null}
	 * 
	 * {@post formatted}
	 */
	public abstract void visit(BodyNode n);
	
	/**
	 * formats the report node for formatting in report.
	 * 
	 * @param n the report node
	 * 
	 * {@pre n != null}
	 * 
	 * {@post formatted}
	 */
	public abstract void visit(ReportNode n);
	
	/**
	 * formats the list node for formatting in report.
	 * 
	 * @param n
	 * 
	 * {@pre n != null}
	 * 
	 * {@post formatted}
	 */
	public abstract void visit(ListNode n);
	
	/**
	 * formats the sub heading node for formatting in report.
	 * 
	 * @param n
	 *
	 * {@pre n != null}
	 * 
	 * {@post formatted}
	 */
	public abstract void visit(SubHeadingNode n);
	
	/**
	 * Displays the formatted result.
	 * 
	 * {@pre none}
	 * 
	 * {@post result is displayed}
	 */
	public void display() {
		
	}
}
