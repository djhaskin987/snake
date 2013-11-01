package model;

import java.util.ArrayList;

public class ObservableArgs<E> extends ArrayList<E> implements IModelTagable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7374929275966449579L;

	@Override
	public Object getTag() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void setTag(Object o) {
		// do nothing
	}

	@Override
	public boolean hasTag() {
		// always true
		return true;
	}

}
