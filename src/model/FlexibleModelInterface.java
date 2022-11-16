package model;

import java.time.LocalDate;

public interface FlexibleModelInterface extends ModelInterface{
  /**
   * Sell stocks given stock symbol
   * @param id Portfolio ID to sell stocks from
   * @param symbol Ticker symbol of stock that is to be sold
   * @param numShares Number of shares to be sold
   * @return Amount of money sold for.
   */
  double sellStocks(String id, String symbol, int numShares);

  /**
   * Adds a given stock to an existing portfolio.
   * @param portfolioId Portfolio stock needs to be added to.
   * @param companyName company ticker/symbol of the share.
   * @param date purchase date of share.
   * @param numShares number of shares purchased.
   * @param stockPrice -1, if price is not known or value of each stock.
   * @return true if successfully added or false if not.
   */
  boolean addStockToExistingPortfolio(String portfolioId, String companyName, LocalDate date, int numShares,
      double stockPrice);
}
