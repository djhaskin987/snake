package gui.common;

import gui.batches.ICommand;

import java.util.ArrayDeque;
import java.util.Deque;

public class Commands {
	private Deque<ICommand> undoStack;
	private Deque<ICommand> redoStack;
	
	public Commands() {
		undoStack = new ArrayDeque<ICommand>();
		redoStack = new ArrayDeque<ICommand>();
	}
	
	public void execute(ICommand command) {
		undoStack.push(command);
		redoStack.clear();
		command.execute();
	}
	
	public void undo() {
		ICommand command = undoStack.pop();
		redoStack.push(command);
		command.undo();
	}
	
	public void redo() {
		ICommand command = redoStack.pop();
		undoStack.push(command);
		command.execute();
	}

	public boolean canUndo() {
		return !undoStack.isEmpty();
	}

	public boolean canRedo() {
		return !redoStack.isEmpty();
	}
}
