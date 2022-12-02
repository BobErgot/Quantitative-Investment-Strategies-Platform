package model;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

/**
 * This interface represents all the Model operations to be supported by the concrete
 * implementation and hides low level operations from whoever is trying to access information
 * from file system or web api.
 */
public interface ModelInterface {

  /**
   * Creates final portfolio after adding all the shares.
   *
   * @param portfolioName unique id name of the portfolio to be created
   * @param date          creation date of the portfolio to be added
   */
  void createPortfolio(String portfolioName, LocalDate date);

  /**
   * Uses file interface to get a list of portfolios in string format.
   *
   * @return list of portfolios as a string.
   */
  List<String> getPortfolio();

  /**
   * Uses file interface to get a portfolios in string format given the id.
   *
   * @param id unique id of portfolio to get
   * @return portfolio as a string
   */
  String getPortfolioById(String id) throws IllegalArgumentException;

  /**
   * Return valuation of entire portfolio on a given date.
   *
   * @param id   id of portfolio to get valuation of
   * @param date get stock valuation given date
   * @return valuation of portfolio
   */
  double getValuationGivenDate(String id, LocalDate date);

  /**
   * Get amount of money invested in portfolio on given date.
   *
   * @param id   id of portfolio to get cost basis of.
   * @param date date to calculate cost basis of portfolio on and before.
   * @return cost basis of portfolio.
   */
  double getCostBasis(String id, LocalDate date);

  /**
   * Adds share to local set of shares given details of the share.
   *
   * @param companyName company ticker/symbol of the share
   * @param date        purchase date of share
   * @param numShares   number of shares purchased
   * @param stockPrice  -1, if price is not known or value of each stock
   * @return true if successfully added or false is not
   */
  boolean addShareToModel(String companyName, LocalDate date, int numShares, double stockPrice);

  /**
   * Read and persist portfolio data to local software database from the path specified.
   *
   * @param path       "~", if file to be read is relative to directory else pass absolute path
   * @param folderName directory where the file is
   * @param fileName   name of the file
   * @param extension  file extension
   * @return "true" if uploading portfolio to local storage is successful else "false"
   * @throws DataFormatException   if content of the file is not in proper format
   * @throws FileNotFoundException if file is not found at given destination
   */
  boolean addPortfolioByUpload(String path, String folderName, String fileName, String extension)
          throws DataFormatException, FileNotFoundException;

  /**
   * Check if portfolio with particular id is already saved in the local file.
   *
   * @param selectedId unique id of the portfolio
   * @return "true" if found else "false"
   */
  boolean idIsPresent(String selectedId);

  /**
   * Check if size of share is larger than zero.
   *
   * @return "true" if it is, else "false"
   */
  boolean canCreateShare();

  /**
   * Check if the company ticker/symbol is valid and exists.
   *
   * @param symbol company listed stock ticker or symbol
   * @return true if symbol is a valid and exists
   */
  boolean checkTicker(String symbol);

  /**
   * Takes portfolio name/id, start date and end date, and time filter category as a parameter
   * and generates data points to be plotted.
   * @param id portfolio name
   * @param from the start date
   * @param to the end date
   * @param group time filter category
   * @re  turn a list of data points that can be used by the view to plot the bar graph
   */
  List<Double> getPortfolioPerformance(String id, LocalDate from, LocalDate to, Periodicity group);

  /**
   * Sell stocks given stock symbol.
   *
   * @param portfolioName Portfolio ID to sell stocks from
   * @param symbol Ticker symbol of stock that is to be sold
   * @param numShares Number of shares to be sold
   * @param date date when the share is to be sold
   * @return Amount of money sold for.
   */
  double sellStocks(String portfolioName, String symbol, int numShares, LocalDate date);

  /**
   * Appends all stocks to an existing portfolio.
   *
   * @param portfolioName Portfolio ID to sell stocks from
   * @param symbol Ticker symbol of stock that is to be sold
   * @param numShares Number of shares to be sold
   * @param date date when the share was bought
   * @return Amount of money paid to buy the shares
   */
  double appendPortfolio(String portfolioName, String symbol, int numShares, LocalDate date);

  List<String> getShareTickerInPortfolio(String portfolioName);

  boolean createStrategy(String portfolioName, String investmentAmount, LocalDate date,
                         LocalDate endDate, ArrayList<String> shares, ArrayList<Integer> weightage,
                         int type);

  boolean createPortfolioStrategy(String portfolioName, String investmentAmount, LocalDate date,
                         LocalDate endDate, ArrayList<String> shares, ArrayList<Integer> weightage,
                         int type);
}