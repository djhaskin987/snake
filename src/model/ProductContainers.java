package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 * Wrapper for a map of all ProductGroups
 * @author Nathan Standiford
 *
 */
public class ProductContainers implements Serializable, Collection<IProductContainer> {
	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = -3554355105443939099L;
	private Map<NonEmptyString, IProductContainer> productContainers;
	
	/**
	 * creates a new ProductGroup instance
	 * 
	 * {@pre none}
	 * 
	 * {@post a ProductGroup instance}
	 */
	public ProductContainers() {
		productContainers = new HashMap<NonEmptyString, IProductContainer>();
	}
	
	/**
	 * Gets the underlying map of ProductGroups
	 * 
	 * @return a map
	 * 
	 * {@pre none}
	 * 
	 * {@post a map}
	 */
	public Map<NonEmptyString, IProductContainer> getProductContainers() {
		return productContainers;
	}

	/**
	 * Adds a key value pair to ProductGroups
	 * 
	 * @param key
	 * 
	 * @param value
	 *
	 * {@pre key != null && value != null}
	 */
	public void setProductGroup(NonEmptyString key, IProductContainer value)
	{
		productContainers.put(key, value);
	}
	
	/**
	 * Not supported.
	 */
	@Override
	public boolean addAll(Collection<? extends IProductContainer> arg0) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Not supported.
	 */
	@Override
	public void clear() {
		throw new UnsupportedOperationException();		
	}

	/**
	 * @see Collection
	 */
	@Override
	public boolean contains(Object arg0) {
		return productContainers.values().contains(arg0);
	}

	/**
	 * @see Collection
	 */
	@Override
	public boolean containsAll(Collection<?> arg0) {
		return productContainers.values().containsAll(arg0);
	}

	/**
	 * @see Collection
	 */
	@Override
	public boolean isEmpty() {
		return productContainers.values().isEmpty();
	}

	/**
	 * @see Collection
	 */
	@Override
	public Iterator<IProductContainer> iterator() {
		return productContainers.values().iterator();
	}

	/**
	 * Removes a ProductGroup based on either a String, NonEmptyString, or the ProductGroup itself.
	 * 
	 * @param arg0 object to be removed
	 *
	 * @return true if removed
	 * 
	 * @pre non
	 * 
	 * @post item is removed if it exists and boolean is returned
	 */
	@Override
	public boolean remove(Object arg0) {
		if (arg0.getClass() == String.class) {
			NonEmptyString nes = new NonEmptyString((String)arg0);
			removeProductContainer(nes);
			return true;
		} else if (arg0.getClass() == NonEmptyString.class) {
			removeProductContainer((NonEmptyString)arg0);
			return true;
		} else if (arg0.getClass() == ProductGroup.class) {
			NonEmptyString name = ((ProductGroup)arg0).getName();
			removeProductContainer(name);
			return true;
		}
		
		return false;
	}

	private void removeProductContainer(NonEmptyString name) {
		if (productContainers.containsKey(name))
		{
			IProductContainer toRemove = productContainers.get(name);
			Collection<IProduct> ps = toRemove.getProductsRecursive();
			for (IProduct p : ps)
			{
				toRemove.removeProductRecursive(p);
			}
			productContainers.remove(name);
		}
	}

	/**
	 * Not supported.
	 */
	@Override
	public boolean removeAll(Collection<?> arg0) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Not supported.
	 */
	@Override
	public boolean retainAll(Collection<?> arg0) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see Collection
	 */
	@Override
	public int size() {
		return productContainers.values().size();
	}

	/**
	 * @see Collection
	 */
	@Override
	public Object[] toArray() {
		return productContainers.values().toArray();
	}

	/**
	 * @see Collection
	 */
	@Override
	public <T> T[] toArray(T[] arg0) {
		return productContainers.values().toArray(arg0);
	}

	/**
	 * Adds a ProductGroup
	 * 
	 * @param pg product group
	 * 
	 * @return true always
	 * 
	 * @pre pg != null
	 * 
	 * @post pg is added and returned as true
	 */
	@Override
	public boolean add(IProductContainer pg) {
		NonEmptyString pgName = pg.getName();
		productContainers.put(pgName, pg);
		return true;
	}

}
