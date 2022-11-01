package model;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

public interface ModelInterface {

  /**
   * Creates final portfolio after adding all the shares.
   * @param portfolioName Name of the portfolio to be created
   */
  void createPortfolio(String portfolioName);

  /**
   * Uses FileAPI to get a list of portfolios in string format
   * @return List of portfolios as a string.
   */
  List<String> getPortfolio();
  /**
   * Uses FileAPI to get a portfolios in string format given the id
   * @param id Id of portfolio to get
   * @return Portfolios as a string.
   */
  String getPortfolioById(String id);

  /**
   * Given the data get valuation of entire portfolio.
   * @param id ID of portfolio to get valuation of.
   * @param filter Filter to sort through stocks in portfolio.
   * @return Valuation of portfolio.
   */
  <T> double getValuation(String id, Predicate<T> filter);

  /**
   * Adds share to local list of shares given company name, using WebAPI.
   * @param companyName Company  name to get share of.
   * @param numShares Number of shares purchased.
   * @return If successfully  added or not.
   */
  boolean addShareToModel(String companyName, int numShares);

  /**
   * Adds share to local list of shares given details of the share (manual entry).
   * @param companyName Company  name to get share of.
   * @param date Date of share purchased.
   * @param price Price of share on that date.
   * @param numShares Number of shares purchased.
   * @return If successfully  added or not.
   */
  boolean addShareToModel(String companyName, LocalDate date, double price, int numShares);

  boolean idIsPresent(String selectedId);

  void addPortfolioByUpload(String path);
  boolean idIsPresent(String selectedId);
}
