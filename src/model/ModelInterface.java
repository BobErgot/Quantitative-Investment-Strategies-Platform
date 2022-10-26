package model;

import java.util.Date;
import java.util.List;

public interface ModelInterface {

  /**
   * Creates final portfolio after adding all the shares.
   */
  void createPortfolio();

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
   * @param date Date to check valuation of portfolio.
   * @return Valuation of protfolio.
   */
  double getValuationGivenDate(String id, Date date);

  /**
   * Adds share to local list of shares given company name, using WebAPI.
   * @param companyName Company  name to get share of.
   * @return If successfully  added or not.
   */
  boolean addShareToModel(String companyName);

  /**
   * Adds share to local list of shares given details of the share (manual entry).
   * @param companyName Company  name to get share of.
   * @param date Date of share purchased.
   * @param price Price of share on that date.
   * @param numShares Number of shares purchased.
   * @return If successfully  added or not.
   */
  boolean addShareToModel(String companyName, Date date, double price,int numShares);
}
