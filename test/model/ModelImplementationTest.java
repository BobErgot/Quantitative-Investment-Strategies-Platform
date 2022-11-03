package model;

import org.junit.Test;

import java.time.LocalDate;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static utility.Constants.PORTFOLIO_NOT_FOUND;

public class ModelImplementationTest {

  // create Empty portfolio
  @Test
  public void testEmptyCreatePortfolio() {
    ModelInterface model = new ModelImplementation();
    try {
      model.createPortfolio("port1", LocalDate.now());
      fail("On creating empty portfolio model should fail");
    } catch (IllegalArgumentException e) {
      // Test passed
    }
  }

  // create valid portfolio
  @Test
  public void testCreateValidPortfolio() {
    ModelInterface model = new ModelImplementation();
    String[] companies = {"AAPL", "MSFT", "GOOG", "AMZN", "NFLX", "META", "CTSH", "CRM", "TSLA"};
    for (String company : companies) {
      model.addShareToModel(company, LocalDate.now(), new Random().nextInt(10),
              -1);
    }
    model.createPortfolio("port2", LocalDate.now());
  }

  // get correct list of portfolios
  @Test
  public void testGetPortfolio() {
    ModelInterface model = new ModelImplementation();
    model.addShareToModel("TSCO.LON", LocalDate.now(), new Random().nextInt(10)
            , -1);
    assertEquals("abc", model.getPortfolio());
  }

  // test correct ticker
  @Test
  public void testCheckTicker() {
    ModelImplementation model = new ModelImplementation();
    assertTrue(model.checkTicker("APPL"));
  }

  // test non-existent ticker
  @Test
  public void testInvalidTicker() {
    ModelImplementation model = new ModelImplementation();
    assertFalse(model.checkTicker("Apple"));
  }

  //Test get valid portfolio by id
  @Test
  public void testGetPortfolioById() {
    ModelInterface model = new ModelImplementation();
    String[] companies = {"AAPL", "MSFT", "GOOG", "AMZN", "NFLX", "META", "CTSH", "CRM", "TSLA"};
    for (String company : companies) {
      model.addShareToModel(company, LocalDate.now(), new Random().nextInt(10),
              -1);
    }
    //TODO
    model.createPortfolio("port3", LocalDate.now());
    assertEquals("//TODO", model.getPortfolioById("port3"));
  }

  //Test get invalid portfolio by id
  @Test
  public void testInvalidGetPortfolioById() {
    ModelInterface model = new ModelImplementation();
    String[] companies = {"AAPL", "MSFT", "GOOG", "AMZN", "NFLX", "META", "CTSH", "CRM", "TSLA"};
    for (String company : companies) {
      model.addShareToModel(company, LocalDate.now(), new Random().nextInt(10),
              -1);
    }
    model.createPortfolio("port4", LocalDate.now());
    assertEquals(PORTFOLIO_NOT_FOUND, model.getPortfolioById("Yapple"));
    assertEquals(PORTFOLIO_NOT_FOUND, model.getPortfolioById(""));
  }

  // negative id or invalid string
  @Test
  public void testBlankIdPortfolioById() {
    ModelInterface model = new ModelImplementation();
    String[] companies = {"ZD", "ZDGE", "ZDVSV", "ZEAL", "ZECP", "ZEN", "ZENV", "ZEPP"};
    for (String company : companies) {
      model.addShareToModel(company, LocalDate.now(), new Random().nextInt(10),
              -1);
    }
    try {
      model.getPortfolioById("");
      fail("Test case passed even though empty Id was given");
    } catch (IllegalArgumentException e) {
      // Test passed
    }
  }

  // Check if valuation is correct.
  @Test
  public void testBlankGetValuation() {
    ModelInterface model = new ModelImplementation();
    model.addShareToModel("WAVC", LocalDate.now(), new Random().nextInt(10),
            -1);
    try {
      model.getValuationGivenDate("", LocalDate.parse("2021-11-02"));
      fail("Test case passed even though invalid Id was given");
    } catch (IllegalArgumentException e) {
      // Test passed
    }
  }

  // invalid id/name
  @Test
  public void testInvalidGetValuation() {
    ModelInterface model = new ModelImplementation();
    model.addShareToModel("YELP", LocalDate.now(), new Random().nextInt(10),
            -1);
    try {
      model.getValuationGivenDate("NOTYELP", LocalDate.parse("2021-11-02"));
      fail("Test case passed even though invalid Id was given");
    } catch (IllegalArgumentException e) {
      // Test passed
    }
  }

  // valid get valuation
  @Test
  public void testValidGetValuation() {
    ModelInterface model = new ModelImplementation();
    model.addShareToModel("VALN", LocalDate.now(), new Random().nextInt(10),
            -1);
    assertEquals(200000000, model.getValuationGivenDate("VALN",
            LocalDate.parse("2021-11-02")), 0);
  }

  // test blank ID present
  @Test
  public void testBlankIdIsPresent() {
    ModelInterface model = new ModelImplementation();
    model.addShareToModel("TAGS", LocalDate.now(), new Random().nextInt(10),
            -1);
    assertFalse(model.idIsPresent(""));
  }

  // test invalid ID present
  @Test
  public void testInvalidIdIsPresent() {
    ModelInterface model = new ModelImplementation();
    model.addShareToModel("Apple", LocalDate.now(), new Random().nextInt(10),
            -1);
    assertFalse(model.idIsPresent("notPresent"));
  }

  // test valid ID present
  @Test
  public void testIdIsPresent() {
    ModelInterface model = new ModelImplementation();
    model.addShareToModel("AADR", LocalDate.now(), new Random().nextInt(10),
            -1);
    assertTrue(model.idIsPresent("Apple"));
  }

  // test can't create share
  @Test
  public void testCantCreateShare() {
    ModelInterface model = new ModelImplementation();
    model.addShareToModel("AAL", LocalDate.now(), new Random().nextInt(10),
            -1);
    assertFalse(model.canCreateShare());
  }

  // test can create share
  @Test
  public void testCanCreateShare() {
    ModelInterface model = new ModelImplementation();
    model.addShareToModel("EAOR", LocalDate.now(), new Random().nextInt(10),
            -1);
    assertTrue(model.canCreateShare());
  }
}