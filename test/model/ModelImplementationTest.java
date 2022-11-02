package model;

import org.junit.Test;

import java.time.LocalDate;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ModelImplementationTest {

  // create Empty portfolio
  @Test
  public void testEmptyCreatePortfolio() {
    ModelInterface model = new ModelImplementation();
    model.createPortfolio("hello");
  }

  // invalid creation date (creation date earlier than any of the stock date purchases)
  @Test
  public void testInvalidCreationDate() {
    ModelInterface model = new ModelImplementation();
    model.addShareToModel("IBM", LocalDate.of(2023, 1, 8), 5);
    model.createPortfolio("portfolio1");
  }

  // create valid portfolio
  @Test
  public void testCreateValidPortfolio() {
    ModelInterface model = new ModelImplementation();
    String[] companies = {"Apple", "Microsoft", "Google", "Amazon", "Netflix", "Meta", "Cognizant",
            "Salesforce", "Tesla", "Mathworks"};
    for (int i = 0; i < 10; i++) {
      model.addShareToModel(companies[i], LocalDate.now(), new Random().nextInt(10));
    }
    model.createPortfolio("portfolio2");
  }

  // get correct list of portfolios
  @Test
  public void testGetPortfolio() {
    ModelInterface model = new ModelImplementation();
    model.addShareToModel("TSCO.LON", LocalDate.now(), new Random().nextInt(10));
    assertEquals("abc", model.getPortfolio());
  }

  // test ticker check
  @Test
  public void checkTicker() {
    ModelImplementation model = new ModelImplementation();
    assertTrue(model.checkTicker("AAIC-P-B"));
  }


  // negative id or invalid string
  @Test
  public void testBlankIdPortfolioById() {
    ModelInterface model = new ModelImplementation();
    for (int i = 0; i < 10; i++)
      model.addShareToModel("Abc" + i, LocalDate.now(), new Random().nextInt(10));
    try {
      model.getPortfolioById("");
      fail("Test case passed even though empty Id was given");
    } catch (IllegalArgumentException e) {
      //
    }
  }

  // get valid portfolio by id
  @Test
  public void testInvalidPortfolioById() {
    ModelInterface model = new ModelImplementation();
    for (int i = 0; i < 10; i++)
      model.addShareToModel("Abc" + i, LocalDate.now(), new Random().nextInt(10));
    try {
      model.getPortfolioById("100");
      fail("Test case passed even though invalid Id was given");
    } catch (IllegalArgumentException e) {
      //
    }
  }

  // invalid id/name of portfolio
  @Test
  public void testValidPortfolioById() {
    ModelInterface model = new ModelImplementation();
    for (int i = 0; i < 10; i++)
      model.addShareToModel("Abc" + i, LocalDate.now(), new Random().nextInt(10));
    model.getPortfolioById("1");
  }

  // Check if valuation is correct.
  @Test
  public void testBlankGetValuation() {
    ModelInterface model = new ModelImplementation();
    for (int i = 0; i < 10; i++)
      model.addShareToModel("Abc" + i, LocalDate.now(), new Random().nextInt(10));
    try {
      assertEquals(10.0, model.getValuation("", share -> true), 0);
      fail("Test case passed even though invalid Id was given");
    } catch (IllegalArgumentException e) {
      //
    }
  }

  // invalid id/name
  @Test
  public void testInvalidGetValuation() {
    ModelInterface model = new ModelImplementation();
    for (int i = 0; i < 10; i++)
      model.addShareToModel("Abc" + i, LocalDate.now(), new Random().nextInt(10));
    try {
      assertEquals(10.0, model.getValuation("1000", share -> true), 0);
      fail("Test case passed even though invalid Id was given");
    } catch (IllegalArgumentException e) {
      //
    }
  }

  // negative id
  @Test
  public void testValidGetValuation() {
    ModelInterface model = new ModelImplementation();
    for (int i = 0; i < 10; i++)
      model.addShareToModel("Abc" + i, LocalDate.now(), new Random().nextInt(10));
    assertEquals(10.0, model.getValuation("1", share -> true), 0);
  }

  @Test
  public void testInvalidgetValuation() {
    ModelInterface model = new ModelImplementation();
    for (int i = 0; i < 10; i++)
      model.addShareToModel("Abc" + i, LocalDate.now(), new Random().nextInt(10));
    model.getValuation("1", share -> true);
  }

  // *test for both overloaded functions*
  // Incorrect parameters
  // valid parameters
  @Test
  public void addShareToModel() {

  }
}