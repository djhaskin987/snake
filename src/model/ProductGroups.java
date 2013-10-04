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
public class ProductGroups implements Serializable, Collection<ProductGroup> {
	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = -3554355105443939099L;
	private Map<NonEmptyString, ProductGroup> productGroups;
	
	/**
	 * creates a new ProductGroup instance
	 * 
	 * {@pre none}
	 * 
	 * {@post a ProductGroup instance}
	 */
	public ProductGroups() {
		productGroups = new HashMap<NonEmptyString, ProductGroup>();
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
	public Map<NonEmptyString, ProductGroup> getProductGroups() {
		return productGroups;
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
	public void setProductGroup(NonEmptyString key, ProductGroup value)
	{
		productGroups.put(key, value);
	}
	
	/**
	 * Not supported.
	 */
	@Override
	public boolean addAll(Collection<? extends ProductGroup> arg0) {
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
		return productGroups.values().contains(arg0);
	}

	/**
	 * @see Collection
	 */
	@Override
	public boolean containsAll(Collection<?> arg0) {
		return productGroups.values().containsAll(arg0);
	}

	/**
	 * @see Collection
	 */
	@Override
	public boolean isEmpty() {
		return productGroups.values().isEmpty();
	}

	/**
	 * @see Collection
	 */
	@Override
	public Iterator<ProductGroup> iterator() {
		return productGroups.values().iterator();
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
			productGroups.remove(nes);
			return true;
		} else if (arg0.getClass() == NonEmptyString.class) {
			productGroups.remove(arg0);
			return true;
		} else if (arg0.getClass() == ProductGroup.class) {
			NonEmptyString name = ((ProductGroup)arg0).getName();
			productGroups.remove(name);
			return true;
		}
		return false;
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
		return productGroups.values().size();
	}

	/**
	 * @see Collection
	 */
	@Override
	public Object[] toArray() {
		return productGroups.values().toArray();
	}

	/**
	 * @see Collection
	 */
	@Override
	public <T> T[] toArray(T[] arg0) {
		return productGroups.values().toArray(arg0);
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
	public boolean add(ProductGroup pg) {
		NonEmptyString pgName = pg.getName();
		productGroups.put(pgName, pg);
		return true;
	}

}
