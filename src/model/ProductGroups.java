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
	
	public ProductGroups(){
		productGroups = new HashMap<NonEmptyString, ProductGroup>();
	}
	
	
	public Map<NonEmptyString, ProductGroup> getProductGroups() {
		return productGroups;
	}

	public void setProductGroups(Map<NonEmptyString, ProductGroup> productGroups) {
		this.productGroups = productGroups;
	}

	@Override
	public boolean addAll(Collection<? extends ProductGroup> arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();		
	}

	@Override
	public boolean contains(Object arg0) {
		return productGroups.values().contains(arg0);
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		return productGroups.values().containsAll(arg0);
	}

	@Override
	public boolean isEmpty() {
		return productGroups.values().isEmpty();
	}

	@Override
	public Iterator<ProductGroup> iterator() {
		return productGroups.values().iterator();
	}

	@Override
	public boolean remove(Object arg0) {
		if (arg0.getClass() == NonEmptyString.class)
		{
			
		}
		else if (arg0.getClass() == ProductGroup.class)
		{
			
		}
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return productGroups.values().size();
	}

	@Override
	public Object[] toArray() {
		return productGroups.values().toArray();
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		return productGroups.values().toArray(arg0);
	}

	@Override
	public boolean add(ProductGroup pg) {
		NonEmptyString pgName = pg.getName();
		productGroups.put(pgName, pg);
		return true;
	}

}
