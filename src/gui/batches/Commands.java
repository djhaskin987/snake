package gui.batches;

import java.util.Deque;
import java.util.LinkedList;

/** Commands acts as an ICommand manager, un-doing and re-doing
 * commands in the order that they were received.
 * @author Daniel Jay Haskin
 *
 */
public class Commands {
	private Deque<ICommand> undoStack;
	private Deque<ICommand> redoStack;
	public Commands() {
		undoStack = new LinkedList<ICommand>();
		redoStack = new LinkedList<ICommand>();
	}
	/**
	 * Adds a command to the undo stack and executes it.
	 * @param c a command to be managed by this Commands object.
	 
	 * {@pre c is a non-null, valid command; the pre-conditions of
	 * c.execute() are met.}
	 * 
	 * {@post the post-conditions of c.execute() are met;
	 * the instance c is pushed onto the undo stack; the redo stack is cleared.}
	 */
	public void execute(ICommand c)
	{
		c.execute();
		undoStack.push(c);
		redoStack.clear();
	}
	
	/**
	 * Undos the last command executed.
	 * @param none.
	 
	 * {@pre The post-condition of the top command on the undo stack is met; the
	 * undo stack is non-empty.}
	 * 
	 * {@post the command which was on the top of the undo stack is popped; 
	 * its c.execute() pre-conditons are met; it is pushed onto the redo stack.}
	 */
	public void undo()
	{
		ICommand top = undoStack.pop();
		top.undo();
		redoStack.push(top);
	}
	/**
	 * Redoes the last undone command.
	 * @param none.
	 
	 * {@pre The pre-condition of the last undone command is met; the redo stack is non-empty.}
	 * 
	 * {@post the command which was on the top of the redo stack is popped; 
	 * its c.execute() post-conditons are met; it is pushed onto the undo stack.}
	 */
	public void redo()
	{
		ICommand top = redoStack.pop();
		top.execute();
		undoStack.push(top);
	}
	/**
	 * Returns if it is possible to undo any commands.
	 * @param none.
	 
	 * {@pre This object is initialized}
	 * 
	 * {@post Same}
	 */
	public boolean canUndo()
	{
		return undoStack.size() > 0;
	}
	/**
	 * Returns if it is possible to redo any commands.
	 * @param none.
	 
	 * {@pre This object is initialized}
	 * 
	 * {@post Same}
	 */
	public boolean canRedo()
	{
		return redoStack.size() > 0;
	}
}