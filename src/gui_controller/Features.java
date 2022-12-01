package gui_controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Periodicity;

/**
 * Lists all the features & user operations possible from this application.
 */
public interface Features {
  // Create portfolio tab

  /**
   * Purchases shares for user while creating portfolio for the first time.
   *
   * @param shareName Ticker Symbol to purchase
   * @param numShares Number of shares to purchase.
   * @param date      Date to purchase a share.
   * @return If share was purchased successfully or not.
   */
  boolean purchaseShare(String shareName, int numShares, LocalDate date);

  /**
   * Asks the user for input like if he wants to add shares, once a share is added the user get the
   * option to create the portfolio. On receiving confirmation from the user the function interacts
   * with the model interface object to transmit the portfolio information to be created.
   *
   * @param portfolioName Portfolio ID to generate composition of.
   * @param pType         Specifies a Flexible or Fixed Portfolio.
   * @return If portfolio was created successfully or not.
   */
  boolean createPortfolio(String portfolioName, PortfolioType pType);

  /**
   * Sends the path from where the portfolio needs to be uploaded to the model interface object and
   * notifies the user if something goes wrong during upload.
   *
   * @param filePath File path to help facilitate uploading a portfolio.
   * @return If Portfolio was uploaded successfully it return 0 or other codes.
   */
  int uploadPortfolio(String filePath);

  /**
   * Asks the user for input of fields namely portfolio, share to be bought and the number of shares
   * to buy. Based on the information collected, it interacts with model interface to get the value
   * of the shares and store it in the specified portfolio.
   *
   * @param portfolioName Portfolio ID to generate composition of.
   * @param numShares     Number of shares to purchase.
   * @param date          Date to purchase a share.
   * @return Price share was purchased for.
   */
  double purchaseShare(String portfolioName, String shareName, int numShares, LocalDate date);

  /**
   * Asks the user for input of fields namely portfolio, share to be sold from that portfolio and
   * the number of shares to be sold. Based on the information collected, it interacts with model
   * interface to calculate the total selling value of shares based on present stock share price. *
   *
   * @param portfolioName Portfolio ID to generate composition of.
   * @param numShares     Number of shares to purchase.
   * @param date          Date to purchase a share.
   * @return Price share was sold for.
   */
  double sellShare(String portfolioName, String shareName, int numShares, LocalDate date);

  /**
   * Interacts with the model interface object to get cost basis of a specific portfolio and returns
   * to the user as string output.
   *
   * @param id   Portfolio ID to generate cost basis of.
   * @param date Date to generate cost basis on.
   * @return Cost basis of given portfolio on given date.
   */
  double generateCostBasis(String id, LocalDate date);

  /**
   * Interacts with the model interface object to get composition of a specific portfolio and
   * returns to the user as string output.
   *
   * @param id Portfolio ID to generate composition of.
   * @return Composition of given portfolio ID.
   */
  String generateComposition(String id);

  /**
   * Interacts with the model interface object to get data of a specific portfolio between the two
   * dates specified by the user and generates chart with dynamic x-axis and y-axis scaling.
   *
   * @param portfolioName Portfolio ID to generate graph of.
   * @param from
   * @param to
   * @param group
   * @return List of points to plot (Tentative).
   */
  List<Double> generatePerformanceGraph(String portfolioName, LocalDate from, LocalDate to,
      Periodicity group);

  /**
   * Interacts with the model interface object to get valuation of a specific portfolio on a
   * specific date entered by user and returns the valuation amount to the user as string output.
   *
   * @param id   Portfolio ID to generate valuation of.
   * @param date Date to generate cost basis on.
   * @return Valuation of given portfolio on given date.
   */
  double getValuation(String id, LocalDate date);

  boolean checkPortfolioNameExists(String portfolioName);

  boolean checkTickerExists(String ticker);

  List<String> getShareTickerInPortfolio (String portfolioName);

  boolean createStrategy (String portfolioName, String investmentAmount, LocalDate date,
                          LocalDate endDate, ArrayList<String> shares, ArrayList<Integer> weightage);
}
