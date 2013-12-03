package model.serialization.db;

public interface IDAOFactory {
	IItemDAO getItemDAO();
	IRootDAO getRootDAO();
	IProductDAO getProductDAO();
	IProductContainerDAO getProductContainerDAO();
	
	/** Closes all connections; renders all objects made from this factory
	 * unusable.
	 */
	void terminate();
}
