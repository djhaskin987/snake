package model;

import gui.common.ITagable;

import java.util.Observable;

import org.apache.commons.lang3.tuple.Pair;

public class ModelObservable extends Observable {
	public void notifyObservers(ModelActions action,
			ITagable payload)
	{
		Pair<ModelActions, ITagable> pair = Pair.of(action, payload);
		System.out.println("Number of Observers: " + countObservers());
		setChanged();
		notifyObservers(pair);
	}
}
