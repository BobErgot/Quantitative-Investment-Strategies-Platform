package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static utility.Constants.PORTFOLIO_NOT_FOUND;

import java.time.LocalDate;
import java.util.Random;
import org.junit.Test;

public class ModelImplementationTest {

  // create Empty portfolio
  @Test
  public void testEmptyCreatePortfolio() {
    ModelInterface model = new ModelImplementation();
    try {
      model.createPortfolio("hello");
      fail("On creating empty portfolio model should fail");
    } catch (IllegalArgumentException e) {
      //
    }
  }
  /*
  // invalid creation date (creation date earlier than any of the stock date purchases)
  @Test
  public void testInvalidCreationDate() {
    ModelInterface model = new ModelImplementation();
    model.addShareToModel("IBM", LocalDate.of(2023, 1, 8), 5);
    model.createPortfolio("portfolio1");
  }*/

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

  // test correct ticker
  @Test
  public void testCheckTicker() {
    ModelImplementation model = new ModelImplementation();
    assertTrue(model.checkTicker("Apple"));
  }

  // test non-existent ticker
  @Test
  public void testInvalidTicker() {
    ModelImplementation model = new ModelImplementation();
    assertFalse(model.checkTicker("Yapple"));
  }

  //Test get valid portfolio by id
  @Test
  public void testGetPortfolioById() {
    ModelInterface model = new ModelImplementation();
    String[] companies = {"Apple", "Microsoft", "Google", "Amazon", "Netflix", "Meta", "Cognizant",
        "Salesforce", "Tesla", "Mathworks"};
    for (int i = 0; i < 10; i++) {
      model.addShareToModel(companies[i], LocalDate.now(), new Random().nextInt(10));
    }
    model.createPortfolio("portfolio2");
    //TODO
    assertEquals("TODO", model.getPortfolioById("Apple"));
  }

  //Test get invalid portfolio by id
  @Test
  public void testInvalidGetPortfolioById() {
    ModelInterface model = new ModelImplementation();
    String[] companies = {"Apple", "Microsoft", "Google", "Amazon", "Netflix", "Meta", "Cognizant",
        "Salesforce", "Tesla", "Mathworks"};
    for (int i = 0; i < 10; i++) {
      model.addShareToModel(companies[i], LocalDate.now(), new Random().nextInt(10));
    }
    model.createPortfolio("portfolio2");
    assertEquals(PORTFOLIO_NOT_FOUND, model.getPortfolioById("Yapple"));
    assertEquals(PORTFOLIO_NOT_FOUND, model.getPortfolioById(""));
  }

  // negative id or invalid string
  @Test
  public void testBlankIdPortfolioById() {
    ModelInterface model = new ModelImplementation();
    for (int i = 0; i < 10; i++) {
      model.addShareToModel("Abc" + i, LocalDate.now(), new Random().nextInt(10));
    }
    try {
      model.getPortfolioById("");
      fail("Test case passed even though empty Id was given");
    } catch (IllegalArgumentException e) {
      //
    }
  }

  // Check if valuation is correct.
  @Test
  public void testBlankGetValuation() {
    ModelInterface model = new ModelImplementation();
    model.addShareToModel("Apple", LocalDate.now(), new Random().nextInt(10));
    try {
      model.getValuation("", share -> true);
      fail("Test case passed even though invalid Id was given");
    } catch (IllegalArgumentException e) {
      //
    }
  }

  // invalid id/name
  @Test
  public void testInvalidGetValuation() {
    ModelInterface model = new ModelImplementation();
    model.addShareToModel("Apple", LocalDate.now(), new Random().nextInt(10));
    try {
      model.getValuation("NotApple", share -> true);
      fail("Test case passed even though invalid Id was given");
    } catch (IllegalArgumentException e) {
      //
    }
  }

  // valid get valuation
  @Test
  public void testValidGetValuation() {
    ModelInterface model = new ModelImplementation();
    model.addShareToModel("Apple", LocalDate.now(), new Random().nextInt(10));
    assertEquals(200000000, model.getValuation("Apple", share -> true));
  }

  // test blank ID present
  @Test
  public void testBlankIdIsPresent() {
    ModelInterface model = new ModelImplementation();
    model.addShareToModel("Apple", LocalDate.now(), new Random().nextInt(10));
    assertFalse(model.idIsPresent(""));
  }

  // test invalid ID present
  @Test
  public void testInvalidIdIsPresent() {
    ModelInterface model = new ModelImplementation();
    model.addShareToModel("Apple", LocalDate.now(), new Random().nextInt(10));
    assertFalse(model.idIsPresent("notPresent"));
  }

  // test valid ID present
  @Test
  public void testIdIsPresent() {
    ModelInterface model = new ModelImplementation();
    model.addShareToModel("Apple", LocalDate.now(), new Random().nextInt(10));
    assertTrue(model.idIsPresent("Apple"));
  }

  // test can't create share
  @Test
  public void testCantCreateShare() {
    ModelInterface model = new ModelImplementation();
    model.addShareToModel("Apple", LocalDate.now(), new Random().nextInt(10));
    assertFalse(model.canCreateShare());
  }

  // test can create share
  @Test
  public void testCanCreateShare() {
    ModelInterface model = new ModelImplementation();
    model.addShareToModel("Apple", LocalDate.now(), new Random().nextInt(10));
    assertTrue(model.canCreateShare());
  }

}