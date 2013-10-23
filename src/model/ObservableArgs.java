package model;

import java.util.ArrayList;

import gui.common.ITagable;

public class ObservableArgs<E> extends ArrayList<E> implements ITagable {

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
