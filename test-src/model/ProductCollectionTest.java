/**
 * 
 */
package model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author dhaskin
 *
 */
public class ProductCollectionTest {
    ProductCollection pc;
    Product product1;
    Product product0;
    Item item1;
    Item item0;
    Item item2;
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        product0 = new Product(new Barcode("036000291452"),
                        new NonEmptyString("Soups"),
                        null,
                        new Integer(12), new Integer(24));
        product1 = new Product(new Barcode("001010101015"),
                new NonEmptyString("Sandwiches"),
                null,
                new Integer(13), new Integer(2));
        item0 = new Item(product0, null, null, null);
        item1 = new Item(product0, null, null, null);
        item2 = new Item(product1, null, null, null);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link model.ProductCollection#getInstance()}.
     */
    @Test
    public void testGetInstance() {
        pc = ProductCollection.getInstance();
        ProductCollection cp = ProductCollection.getInstance();
        assertTrue(pc == cp);
        assertFalse(pc == null);
    }

    /**
     * Test method for {@link model.ProductCollection#add(model.Product)}.
     */
    @Test
    public void testAddSizeGet() {
        pc = new ProductCollection();
        pc.add(product0);
        assertTrue(pc.getSize() == 1);
        pc.add(product1);
        assertTrue(pc.getSize() == 2);
        assertEquals(pc.getProduct(product0.getBarcode()),
                product0);
        assertEquals(pc.getProduct(product1.getBarcode()),
                product1);
    }

}
