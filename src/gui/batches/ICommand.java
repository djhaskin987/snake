package gui.batches;
/**
 * The ICommand class is the central class in the command pattern
 * used in this package for do/undo/redo purposes.
 * @author Daniel Jay Haskin
 */
public interface ICommand {
	/**
	 * Executes this command.
	 * @param none.
	 
	 * {@pre Each concrete instance of ICommand has its own pre-condition.}
	 * 
	 * {@post Each concrete instance of ICommand has its own post-condition.}
	 */
	void execute();
	/**
	 * Un-executes this command.
	 * @param none.
	 
	 * {@pre Must reflect the post-condition of the execute() method.}
	 * 
	 * {@post Must reflect the pre-condtion of the execute() method.}
	 */
	void undo();
}
