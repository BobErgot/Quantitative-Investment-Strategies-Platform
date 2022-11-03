package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

/**
 * Classes to test Portfolio Class in model.
 */
public class PortfolioTest {

  Set<Share> shareList;

  @Before
  public void createShareList() {
    shareList = new HashSet<>();
    String[] companies = {"Apple", "Microsoft", "Google", "Amazon", "Netflix", "Meta", "Cognizant",
        "Salesforce", "Tesla", "Mathworks"};
    for (int i = 0; i < 10; i++) {
      Share s = new Share(companies[i], LocalDate.now(), 2, 3);
      shareList.add(s);
    }
  }

  // constructor tests
  @Test
  public void validConstructorTest() {
    try {
      Portfolio p = new Portfolio("0", shareList, LocalDate.now());
      FileAbstract fileDatabase = new CSVFile();
    } catch (Exception e) {
      fail("Throws error even though portfolio was created correctly.");
    }
  }

  // Invalid array list entry
  @Test
  public void invalidListConstructorTest() {
    try {
      Portfolio p = new Portfolio("0", new HashSet<>(), LocalDate.now());
      fail("Does not throw error even though portfolio was created incorrectly.");
    } catch (IllegalArgumentException e) {
      // Test passed
    }
  }

  // get list of shares test
  @Test
  public void getListOfShares() {
    Portfolio p = new Portfolio("0", shareList, LocalDate.now());
    assertEquals(shareList, p.getListOfShares());
  }
}