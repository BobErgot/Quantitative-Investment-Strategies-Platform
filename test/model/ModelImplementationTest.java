package model;

import org.junit.Test;

import java.time.LocalDate;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static utility.Constants.PORTFOLIO_NOT_FOUND;

/**
 * Classes to test model in MVC.
 */
public class ModelImplementationTest {

  // create Empty portfolio
  @Test
  public void testEmptyCreatePortfolios() {
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
  public void testCreateValidPortfolios() {
    ModelInterface model = new ModelImplementation();
    String[] companies = {"AAPL", "MSFT", "GOOG", "AMZN", "NFLX", "META", "CTSH", "CRM", "TSLA"};
    for (String company : companies) {
      model.addShareToModel(company, LocalDate.now(), new Random().nextInt(10), -1);
    }
    try {
      model.createPortfolio("port2", LocalDate.now());
      fail("Test case passed even though empty Id was given");
    } catch (IllegalArgumentException e) {
      // Test passed
    }
  }

  private boolean compareStringContents(String a, String b) {
    return a.replaceAll("\\s+", "").equalsIgnoreCase(b.replaceAll("\\s+",
            ""));
  }

  // test correct ticker
  @Test
  public void testCheckTickers() {
    ModelImplementation model = new ModelImplementation();
    assertTrue(model.checkTicker("AAPL"));
  }

  // test non-existent ticker
  @Test
  public void testInvalidTickers() {
    ModelImplementation model = new ModelImplementation();
    assertFalse(model.checkTicker("Apple"));
  }

  //Test get valid portfolio by id
  @Test
  public void testGetPortfolioByIds() {
    ModelInterface model = new ModelImplementation();
    String[] companies = {"AAPL", "MSFT", "GOOG", "AMZN", "NFLX", "META", "CTSH", "CRM", "TSLA"};
    for (String company : companies) {
      model.addShareToModel(company, LocalDate.now(), 2, -1);
    }
    try {
      model.createPortfolio("port3", LocalDate.now());
      model.getPortfolioById("port3");
      fail("Test case passed even though empty Id was given");
    } catch (IllegalArgumentException e) {
      // Test passed
    }
  }

  //Test get invalid portfolio by id
  @Test
  public void testInvalidGetPortfolioByIds() {
    ModelInterface model = new ModelImplementation();
    String[] companies = {"AAPL", "MSFT", "GOOG", "AMZN", "NFLX", "META", "CTSH", "CRM", "TSLA"};
    for (String company : companies) {
      model.addShareToModel(company, LocalDate.now(), new Random().nextInt(10), -1);
    }
    model.createPortfolio("port4", LocalDate.now());
    assertEquals(PORTFOLIO_NOT_FOUND, model.getPortfolioById("Yapple"));
    assertEquals(PORTFOLIO_NOT_FOUND, model.getPortfolioById(""));
  }

  // negative id or invalid string
  @Test
  public void testBlankIdPortfolioByIds() {
    ModelInterface model = new ModelImplementation();
    String[] companies = {"ZD", "ZDGE", "ZDVSV", "ZEAL", "ZECP", "ZEN", "ZENV", "ZEPP"};
    for (String company : companies) {
      model.addShareToModel(company, LocalDate.now(), new Random().nextInt(10), -1);
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
  public void testBlankGetValuations() {
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
  public void testInvalidGetValuations() {
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
  public void testValidGetValuations() {
    ModelInterface model = new ModelImplementation();
    model.addShareToModel("VALN", LocalDate.now(), new Random().nextInt(10),
            -1);
    model.createPortfolio("testin" + LocalDate.now(), LocalDate.now());
    assertEquals(2530.8, model.getValuationGivenDate("testing",
                    LocalDate.parse("2021-11-02")),
            0.1);
  }

  // test blank ID present
  @Test
  public void testBlankIdIsPresents() {
    ModelInterface model = new ModelImplementation();
    model.addShareToModel("TAGS", LocalDate.now(), new Random().nextInt(10),
            -1);
    assertFalse(model.idIsPresent(""));
  }

  // test invalid ID present
  @Test
  public void testInvalidIdIsPresents() {
    ModelInterface model = new ModelImplementation();
    model.addShareToModel("AADR", LocalDate.now(), new Random().nextInt(10),
            -1);
    assertFalse(model.idIsPresent("notPresent"));
  }

  // test valid ID present
  @Test
  public void testIdIsPresents() {
    ModelInterface model = new ModelImplementation();
    model.addShareToModel("IBM", LocalDate.now(), 24, -1);
    model.createPortfolio("Port" + LocalDate.now(), LocalDate.now());
    assertTrue(model.idIsPresent("Port"));
  }

  // test can't create share
  @Test
  public void testCantCreateShares() {
    ModelInterface model = new ModelImplementation();
    assertFalse(model.canCreateShare());
  }

  // test can create share
  @Test
  public void testCanCreateShares() {
    ModelInterface model = new ModelImplementation();
    model.addShareToModel("IBM", LocalDate.now(), new Random().nextInt(10),
            -1);
    assertTrue(model.canCreateShare());
  }
}