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
   * Appends all stocks to an existing portfolio.
   * @param portfolioName Portfolio stock needs to be added to.
   */
  void appendPortfolio(String portfolioName);
}
