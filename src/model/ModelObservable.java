package model;

import java.util.Observable;

import org.apache.commons.lang3.tuple.Pair;

public class ModelObservable extends Observable {
	public void notifyObservers(ModelActions action,
			IModelTagable payload)
	{
		Pair<ModelActions, IModelTagable> pair = Pair.of(action, payload);
		setChanged();
		notifyObservers(pair);
	}
}
