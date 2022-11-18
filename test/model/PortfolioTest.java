package model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Classes to test Portfolio Class in model.
 */
public class PortfolioTest {
  Set<Share> shareList;

  @Before
  public void createShareList() {
    shareList = new HashSet<>();
    String[] companies = {"APPL", "IBM", "GOOGL"};
    for (int i = 0; i < 3; i++) {
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

  // test get range of portfolio stocks
  @Test
  public void testGetDateRangeOfStockData() {
    shareList = new HashSet<>();
    shareList.add(new Share("APPL", LocalDate.of(2012, 12, 12),
            2, 3));
    shareList.add(new Share("IBM", LocalDate.of(2000, 12, 12),
            2, 3));
    shareList.add(new Share("CRM", LocalDate.of(1978, 12, 11),
            2, 3));
    shareList.add(new Share("MSFT", LocalDate.of(1978, 12, 12),
            2, 3));
    shareList.add(new Share("GOOGL", LocalDate.of(2022, 12, 12),
            2, 3));
    Portfolio p = new Portfolio("0", shareList, LocalDate.now());
    LocalDate[] answer = {LocalDate.of(1978, 12, 11),
            LocalDate.of(2022, 12, 12)};
    LocalDate[] actual = p.getDateRangeOfStockData();
    for (int i = 0; i < 2; i++) {
      assertEquals(answer[i], actual[i]);
    }
  }
}