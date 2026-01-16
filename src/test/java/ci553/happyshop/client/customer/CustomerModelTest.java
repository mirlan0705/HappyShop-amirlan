package ci553.happyshop.client.customer;

import ci553.happyshop.catalogue.Product;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerModelTest {

    @Test
    void testSetUsername() {
        CustomerModel model = new CustomerModel();
        model.setUsername("test@email.com");

        assertEquals("test@email.com", model.getUsername());
    }
    @Test
    void testDefaultUsernameIsGuest() {
        // create a new instance
        CustomerModel model = new CustomerModel();
        // check that the default username is Guest
        assertEquals("Guest", model.getUsername());
    }
    @Test
    void testUsernameChangesAfterSet() {

        CustomerModel model = new CustomerModel();
        // set initial username
        model.setUsername("first@email.com");
        // change username again
        model.setUsername("second@email.com");
        // verify username was updated
        assertEquals("second@email.com", model.getUsername());
    }
    @Test
    void testAddToTrolleyWithoutSearchDoesNothing() {

        // create model with no searched product. theProduct is null by default
        CustomerModel model = new CustomerModel();

        // before: trolley should be empty
        assertTrue(model.getTrolley().isEmpty());

        // try adding without searching
        model.addToTrolley();

        // trolley should still be empty
        assertTrue(model.getTrolley().isEmpty());
    }
    @Test
    void testGroupProductsByIdMergesSameProduct() {

        // create model
        CustomerModel model = new CustomerModel();

        // build a trolley with duplicates
        ArrayList<Product> list = new ArrayList<>();
        list.add(new Product("0001", "TV", "0001.jpg", 10.0, 10)); // orderedQuantity default maybe 1
        list.add(new Product("0001", "TV", "0001.jpg", 10.0, 10));
        //group items
        ArrayList<Product> grouped = model.groupProductsById(list);
        // duplicates merged into one item
        assertEquals(1, grouped.size());
    }




}