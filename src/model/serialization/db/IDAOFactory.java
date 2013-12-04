package model.serialization.db;

import java.io.Closeable;

public interface IDAOFactory extends Closeable {
	IItemDAO getItemDAO();
	IRootDAO getRootDAO();
	IProductDAO getProductDAO();
	IProductContainerDAO getProductContainerDAO();
}
