package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static utility.Constants.PORTFOLIO_NOT_FOUND;

import java.time.LocalDate;
import java.util.Random;
import org.junit.Test;

/**
 * Classes to test model in MVC.
 */
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
    return a.replaceAll("\\s+", "").equalsIgnoreCase(b.replaceAll("\\s+", ""));
  }

  // get correct list of portfolios
  @Test
  public void testGetPortfolio() {
    ModelInterface model = new ModelImplementation();
    model.addShareToModel("IBM", LocalDate.now(), 3, -1);
    assertTrue(compareStringContents(
        "[-------------------------------------------------------, +id:testing\n"
            + "creationDate:2022-11-03\n"
            + "*shares:+companyName:IBM,purchaseDate:2022-11-03,price:138.485,numShares:20|+companyName:ZZZ,purchaseDate:2022-11-03,price:0.01,numShares:1000, -------------------------------------------------------\n"
            + ", -------------------------------------------------------, +id:port2\n"
            + "creationDate:2022-11-03\n"
            + "*shares:+companyName:META,purchaseDate:2022-11-03,price:92.84,numShares:9|+companyName:AMZN,purchaseDate:2022-11-03,price:94.875,numShares:7|+companyName:TSLA,purchaseDate:2022-11-03,price:221.34494999999998,numShares:5|+companyName:AAPL,purchaseDate:2022-11-03,price:148.58499999999998,numShares:1|+companyName:MSFT,purchaseDate:2022-11-03,price:225.67000000000002,numShares:4|+companyName:NFLX,purchaseDate:2022-11-03,price:280.085,numShares:4|+companyName:CTSH,purchaseDate:2022-11-03,price:61.525000000000006,numShares:5|+companyName:CRM,purchaseDate:2022-11-03,price:154.865,numShares:3|+companyName:GOOG,purchaseDate:2022-11-03,price:89.155,numShares:3, -------------------------------------------------------\n"
            + ", -------------------------------------------------------, +id:port3\n"
            + "creationDate:2022-11-03\n"
            + "*shares:+companyName:META,purchaseDate:2022-11-03,price:92.84,numShares:2|+companyName:AMZN,purchaseDate:2022-11-03,price:94.875,numShares:2|+companyName:TSLA,purchaseDate:2022-11-03,price:221.34494999999998,numShares:2|+companyName:AAPL,purchaseDate:2022-11-03,price:148.58499999999998,numShares:2|+companyName:MSFT,purchaseDate:2022-11-03,price:225.67000000000002,numShares:2|+companyName:NFLX,purchaseDate:2022-11-03,price:280.085,numShares:2|+companyName:CTSH,purchaseDate:2022-11-03,price:61.525000000000006,numShares:2|+companyName:CRM,purchaseDate:2022-11-03,price:154.865,numShares:2|+companyName:GOOG,purchaseDate:2022-11-03,price:89.155,numShares:2, -------------------------------------------------------\n"
            + ", -------------------------------------------------------, +id:Port\n"
            + "creationDate:2022-11-03\n"
            + "*shares:+companyName:AADR,purchaseDate:2022-11-03,price:47.0111,numShares:4, -------------------------------------------------------\n"
            + ", -------------------------------------------------------, +id:testing2022-11-03\n"
            + "creationDate:2022-11-03\n"
            + "*shares:+companyName:VALN,purchaseDate:2022-11-03,price:14.16,numShares:5, -------------------------------------------------------\n"
            + ", -------------------------------------------------------, +id:testing22022-11-03\n"
            + "creationDate:2022-11-03\n"
            + "*shares:+companyName:VALN,purchaseDate:2022-11-03,price:14.16,numShares:8, -------------------------------------------------------\n"
            + ", -------------------------------------------------------, +id:testin2022-11-03\n"
            + "creationDate:2022-11-03\n"
            + "*shares:+companyName:VALN,purchaseDate:2022-11-03,price:14.16,numShares:6, -------------------------------------------------------\n"
            + "]", model.getPortfolio().toString()));
  }

  // test correct ticker
  @Test
  public void testCheckTicker() {
    ModelImplementation model = new ModelImplementation();
    assertTrue(model.checkTicker("AAPL"));
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
  public void testInvalidGetPortfolioById() {
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
  public void testBlankIdPortfolioById() {
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
  public void testBlankGetValuation() {
    ModelInterface model = new ModelImplementation();
    model.addShareToModel("WAVC", LocalDate.now(), new Random().nextInt(10), -1);
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
    model.addShareToModel("YELP", LocalDate.now(), new Random().nextInt(10), -1);
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
    model.addShareToModel("VALN", LocalDate.now(), new Random().nextInt(10), -1);
    model.createPortfolio("testin" + LocalDate.now(), LocalDate.now());
    assertEquals(2530.8, model.getValuationGivenDate("testing", LocalDate.parse("2021-11-02")),
        0.1);
  }

  // test blank ID present
  @Test
  public void testBlankIdIsPresent() {
    ModelInterface model = new ModelImplementation();
    model.addShareToModel("TAGS", LocalDate.now(), new Random().nextInt(10), -1);
    assertFalse(model.idIsPresent(""));
  }

  // test invalid ID present
  @Test
  public void testInvalidIdIsPresent() {
    ModelInterface model = new ModelImplementation();
    model.addShareToModel("AADR", LocalDate.now(), new Random().nextInt(10), -1);
    assertFalse(model.idIsPresent("notPresent"));
  }

  // test valid ID present
  @Test
  public void testIdIsPresent() {
    ModelInterface model = new ModelImplementation();
    model.addShareToModel("IBM", LocalDate.now(), 24, -1);
    model.createPortfolio("Port" + LocalDate.now(), LocalDate.now());
    assertTrue(model.idIsPresent("Port"));
  }

  // test can't create share
  @Test
  public void testCantCreateShare() {
    ModelInterface model = new ModelImplementation();
    assertFalse(model.canCreateShare());
  }

  // test can create share
  @Test
  public void testCanCreateShare() {
    ModelInterface model = new ModelImplementation();
    model.addShareToModel("IBM", LocalDate.now(), new Random().nextInt(10), -1);
    assertTrue(model.canCreateShare());
  }
}