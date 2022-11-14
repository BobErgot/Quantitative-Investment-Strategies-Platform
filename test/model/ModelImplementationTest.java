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
  public void testEmptyCreatePortfolios() {
    ModelInterface model = new MockModel();
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
    ModelInterface model = new MockModel();
    String[] companies = {"AAPL", "MSFT", "GOOG", "AMZN", "NFLX", "META", "CTSH", "CRM", "TSLA"};
    for (String company : companies) {
      model.addShareToModel(company, LocalDate.now(), Math.abs(new Random().nextInt(10))+1, -1);
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
    ModelImplementation model = new MockModel();
    assertTrue(model.checkTicker("AAPL"));
  }

  // test non-existent ticker
  @Test
  public void testInvalidTickers() {
    ModelImplementation model = new MockModel();
    assertFalse(model.checkTicker("Apple"));
  }

  //Test get valid portfolio by id
  @Test
  public void testGetPortfolioByIds() {
    ModelInterface model = new MockModel();
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
    ModelInterface model = new MockModel();
    String[] companies = {"AAPL", "MSFT", "GOOG", "AMZN", "NFLX", "META", "CTSH", "CRM", "TSLA"};
    for (String company : companies) {
      model.addShareToModel(company, LocalDate.now(), Math.abs(new Random().nextInt(10))+1, -1);
    }
    model.createPortfolio("porttest", LocalDate.now());
    assertEquals(PORTFOLIO_NOT_FOUND, model.getPortfolioById("Yapple"));
  }

  // negative id or invalid string
  @Test
  public void testBlankIdPortfolioByIds() {
    ModelInterface model = new MockModel();
    String[] companies = {"ZD", "ZDGE", "ZDVSV", "ZEAL", "ZECP", "ZEN", "ZENV", "ZEPP"};
    for (String company : companies) {
      model.addShareToModel(company, LocalDate.now(), 30, -1);
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
    ModelInterface model = new MockModel();
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
    ModelInterface model = new MockModel();
    model.addShareToModel("YELP", LocalDate.now(), new Random().nextInt(10),
        20);
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
    ModelInterface model = new MockModel();
    model.addShareToModel("IBM", LocalDate.parse("2021-11-02"), 22,
        -1);


    model.createPortfolio("blipblop", LocalDate.parse("2021-11-02"));
    assertEquals(2772.88, model.getValuationGivenDate("blipblop",
            LocalDate.parse("2021-11-02")),
        1);
    assertEquals(0, model.getValuationGivenDate("blipblop",
            LocalDate.parse("2021-11-01")),
        0);
    assertEquals(2579.94, model.getValuationGivenDate("blipblop",
            LocalDate.parse("2021-12-02")),
        1);
  }

  // test blank ID present
  @Test
  public void testBlankIdIsPresents() {
    ModelInterface model = new MockModel();
    model.addShareToModel("TAGS", LocalDate.now(), new Random().nextInt(10),
        -1);
    try {
      model.idIsPresent("");
      fail("Test case passed even though blank Id was given");
    } catch (IllegalArgumentException e) {
      // Test passed
    }
  }

  // test invalid ID present
  @Test
  public void testInvalidIdIsPresents() {
    ModelInterface model = new MockModel();
    model.addShareToModel("AADR", LocalDate.now(), Math.abs(new Random().nextInt(10))+1,
        -1);
    assertFalse(model.idIsPresent("notPresent"));
  }

  // test valid ID present
  @Test
  public void testIdIsPresents() {
    ModelInterface model = new MockModel();
    model.addShareToModel("IBM", LocalDate.now(), 24, -1);
    model.createPortfolio("Porttest", LocalDate.now());
    assertTrue(model.idIsPresent("Porttest"));
  }

  // test can't create share
  @Test
  public void testCantCreateShares() {
    ModelInterface model = new MockModel();
    assertFalse(model.canCreateShare());
  }

  // test can create share
  @Test
  public void testCanCreateShares() {
    ModelInterface model = new MockModel();
    model.addShareToModel("IBM", LocalDate.now(), new Random().nextInt(10),
        -1);
    assertTrue(model.canCreateShare());
  }

  @Test
  public void testValidSellShares() {
    ModelInterface model = new MockModel();
    model.addShareToModel("IBM", LocalDate.now(), 20, 30);
    String portfolioName = "Porttest";
    model.createPortfolio(portfolioName, LocalDate.now());
    model.sellStocks(portfolioName, "IBM", 19);
    assertEquals("+id:Porttest\n"
            + "creationDate:2022-11-14\n"
            + "*shares:+companyName:IBM,purchaseDate:2022-11-14,price:600.0,numShares:1",
        model.getPortfolioById(portfolioName));
  }
  @Test
  public void testSellAllShares() {
    ModelInterface model = new MockModel();
    model.addShareToModel("IBM", LocalDate.now(), 20, 30);
    String portfolioName = "Porttest";
    model.createPortfolio(portfolioName, LocalDate.now());
    model.sellStocks(portfolioName, "IBM", 20);
  }
  // To sell share is greater than number of present shares
  @Test
  public void testInvalidSellShares() {
    ModelInterface model = new MockModel();
    model.addShareToModel("IBM", LocalDate.now(), 20, 30);
    String portfolioName = "Porttest";
    model.createPortfolio(portfolioName, LocalDate.now());

    try {
      model.sellStocks(portfolioName, "IBM", 30);
      fail("Test case passed even though blank Id was given");
    } catch (IllegalArgumentException e) {
      // Test passed
    }
  }
  // test valid get cost basis function
  @Test
  public void testValidGetCostBasis(){
    ModelInterface model = new MockModel();
    model.addShareToModel("IBM", LocalDate.parse("2021-11-01"), 20, 30);
    String portfolioName = "Porttest";
    model.createPortfolio(portfolioName, LocalDate.now());
    assertEquals(600.0,model.getCostBasis(portfolioName,LocalDate.parse("2021-11-02")),1);
  }

}