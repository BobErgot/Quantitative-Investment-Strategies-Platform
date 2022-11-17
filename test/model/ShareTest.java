package model;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests to check functionality of Share Model.
 */
public class ShareTest {

  // Constructor tests
  @Test
  public void testValidConstructor() {
    try {
      Share s = new Share("Apple", LocalDate.now(), 2.2, 4);
    } catch (Exception e) {
      fail("Share was not created successfully");
    }
  }

  @Test
  public void testInvalidCompany() {
    try {
      Share s = new Share("", LocalDate.now(), 2.2, 4);
      fail("Company object created even though company name is blank");
    } catch (IllegalArgumentException e) {
      // Test passed
    }
  }

  // Shares test
  @Test
  public void testInvalidNumShares() {
    try {
      Share s = new Share("", LocalDate.now(), 2.2, -2);
      fail("Company object created even though number of stocks are negative");
    } catch (IllegalArgumentException e) {
      // Test passed
    }
  }

  @Test
  public void testInvalidNumShares2() {
    try {
      Share s = new Share("", LocalDate.now(), 2.2, 0);
      fail("Company object created even though number of stocks are zero");
    } catch (IllegalArgumentException e) {
      // Test passed
    }
  }

  // Get Share num test
  @Test
  public void getShareNumTest() {
    Share s = new Share("Apple", LocalDate.now(), 2.2, 4);
    assertEquals(4, s.getNumShares());
  }

  @Test
  public void testInvalidPrice() {
    try {
      Share s = new Share("", LocalDate.now(), -2.2, 4);
      fail("Company object created even though price of stock is negative");
    } catch (IllegalArgumentException e) {
      // Test passed
    }
  }

  @Test
  public void testInvalidPrice2() {
    try {
      Share s = new Share("", LocalDate.now(), 0.0, 4);
      fail("Company object created even though price of stock is zero");
    } catch (IllegalArgumentException e) {
      // Test passed
    }
  }

  // Get company test
  @Test
  public void testGetCompany() {
    Share s = new Share("Apple", LocalDate.now(), 2.2, 4);
    assertEquals("Apple", s.getCompanyName());
  }
}