package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import org.junit.Test;

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
      //
    }
  }

  //  @Test
//  public void testInvalidCompany2(){
//    try {
//      Share s = new Share("This company doesnt exist in any database/api", new Date(), 2.2);
//      fail("Company object created even though company name is does not exist");
//    }
//    catch (IllegalArgumentException e){
//      //
//    }
//  }
// Shares test
  @Test
  public void testInvalidNumShares() {
    try {
      Share s = new Share("", LocalDate.now(), 2.2, -2);
      fail("Company object created even though number of stocks are negative");
    } catch (IllegalArgumentException e) {
      //
    }
  }

  @Test
  public void testInvalidNumShares2() {
    try {
      Share s = new Share("", LocalDate.now(), 2.2, 0);
      fail("Company object created even though number of stocks are zero");
    } catch (IllegalArgumentException e) {
      //
    }
  }

  // Get Share num test
  @Test
  public void getShareNumTest() {
    Share s = new Share("Apple", LocalDate.now(), 2.2,4);
    assertEquals(4, s.getNumShares());
  }

  @Test
  public void testInvalidPrice() {
    try {
      Share s = new Share("", LocalDate.now(), -2.2, 4);
      fail("Company object created even though price of stock is negative");
    } catch (IllegalArgumentException e) {
      //
    }
  }

  @Test
  public void testInvalidPrice2() {
    try {
      Share s = new Share("", LocalDate.now(), 0.0, 4);
      fail("Company object created even though price of stock is zero");
    } catch (IllegalArgumentException e) {
      //
    }
  }

  // Get company test
  @Test
  public void testGetCompany() {
    Share s = new Share("Apple", LocalDate.now(), 2.2, 4);
    assertEquals("Apple", s.getCompanyName());
  }



}