package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.Test;

public class ModelImplementationTest {


  // create Empty portfolio
  @Test
  public void testEmptyCreatePortfolio(){
    ModelInterface model = new ModelImplementation();
    model.createPortfolio("joe1");
  }
  // invalid creation date (creation date earlier than any of the stock date purchases)
  @Test
  public void testInvalidCreationDate(){
    ModelInterface model = new ModelImplementation();
    model.addShareToModel("Abc", LocalDate.of(2023, 1, 8),22.0,22);
    model.createPortfolio("joe2");
  }
  // create valid portfolio
  @Test
  public void testCreateValidPortfolio() {
    ModelInterface model = new ModelImplementation();
    String[] companies = {"Apple", "Microsoft", "Google", "Amazon", "Netflix", "Meta", "Cognizant",
        "Salesforce", "Tesla", "Mathworks"};
    for (int i = 0; i < 10; i++) {
      model.addShareToModel(companies[i], LocalDate.now(), 2, 3);
    }
    model.createPortfolio("joe3");
  }
  // get correct list of portfolios
  @Test
  public void testGetPortfolio() {
    ModelInterface model = new ModelImplementation();
    System.out.println(model.getPortfolio());
//    model.addShareToModel("Abc");
//    assertEquals("abc" ,model.getPortfolio());
  }
  // negative id or invalid string
  @Test
  public void testBlankIdPortfolioById() {
    ModelInterface model = new ModelImplementation();
    for(int i=0; i<10; i++)
      model.addShareToModel("Abc"+i,2);
    try {
      model.getPortfolioById("");
      fail("Test case passed even though empty Id was given");
    }
    catch (IllegalArgumentException e){
      //
    }
  }
  // get valid portfolio by id
  @Test
  public void testInvalidPortfolioById() {
    ModelInterface model = new ModelImplementation();
    for(int i=0; i<10; i++)
      model.addShareToModel("Abc"+i,2);
    try {
      model.getPortfolioById("100");
      fail("Test case passed even though invalid Id was given");
    }
    catch (IllegalArgumentException e){
      //
    }
  }
  // invalid id/name of portfolio
  @Test
  public void testValidPortfolioById() {
    ModelInterface model = new ModelImplementation();
    for(int i=0; i<10; i++)
      model.addShareToModel("Abc"+i,2);
    model.getPortfolioById("1");
  }
  // Check if valuation is correct.
  @Test
  public void testBlankGetValuation() {
    ModelInterface model = new ModelImplementation();
    for(int i=0; i<10; i++)
      model.addShareToModel("Abc"+i,2);
    try {
      assertEquals(10.0, model.getValuation("", share -> true), 0);
      fail("Test case passed even though invalid Id was given");
    }
    catch (IllegalArgumentException e){
      //
    }
  }
  // invalid id/name
  @Test
  public void testInvalidGetValuation() {
    ModelInterface model = new ModelImplementation();
    for(int i=0; i<10; i++)
      model.addShareToModel("Abc"+i,2);
    try {
      assertEquals(10.0, model.getValuation("1000", share -> true), 0);
      fail("Test case passed even though invalid Id was given");
    }
    catch (IllegalArgumentException e){
      //
    }
  }
  // negative id
  @Test
  public void testValidGetValuation() {
    ModelInterface model = new ModelImplementation();
    for(int i=0; i<10; i++)
      model.addShareToModel("Abc"+i,2);
    assertEquals(10.0,model.getValuation("1",share->true),0);
  }
  @Test
  public void testInvalidgetValuation() {
    ModelInterface model = new ModelImplementation();
    for(int i=0; i<10; i++)
      model.addShareToModel("Abc"+i,2);
    model.getValuation("1",share->true);
  }
  // *test for both overloaded functions*
  // Incorrect parameters
  // valid parameters
  @Test
  public void addShareToModel() {

  }

}
