package model;

public interface IPersistance {
	
	public void store();
	
	public void update(Object newObject);
	
	public void load();
	
}
