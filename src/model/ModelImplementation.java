package model;

import static utility.Constants.PORTFOLIO_DIRECTORY;
import static utility.Constants.PORTFOLIO_NOT_FOUND;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;

import static utility.Constants.PORTFOLIO_DIRECTORY;
import static utility.Constants.STOCK_DIRECTORY;

public class ModelImplementation implements ModelInterface {

  private Set<Share> shares;
  private final Set<Portfolio> portfolios;

  public ModelImplementation() {
    this.shares = new HashSet<>();
    this.portfolios = new HashSet<>();
  }

  @Override
  public void createPortfolio(String portfolioName) {
    Portfolio portfolioObject = new Portfolio(portfolioName, shares, LocalDate.now());
    FileInterface fileDatabase = new CSVFile();
    String formattedString = fileDatabase.convertObjectListIntoString(new ArrayList<>(shares));
    String shareFileName = UUID.randomUUID().toString();
    List<String> referenceList = new ArrayList<>();
    referenceList.add(shareFileName);
    if (fileDatabase.writeToFile(PORTFOLIO_DIRECTORY, shareFileName, formattedString.getBytes())) {
      fileDatabase.writeToFile(PORTFOLIO_DIRECTORY, PORTFOLIO_DIRECTORY,
          fileDatabase.convertObjectIntoString(portfolioObject.toString(), referenceList)
              .getBytes());
    }
    shares = new HashSet<>();
    portfolios.add(portfolioObject);
  }

  @Override
  public List<String> getPortfolio() {
    FileInterface fileDatabase = new CSVFile();
    return fileDatabase.readFromFile(PORTFOLIO_DIRECTORY, PORTFOLIO_DIRECTORY);
  }

  private Portfolio getPortfolioObjectById(String id) {
    for (Portfolio portfolio : portfolios) {
      if (portfolio.getId().equals(id)) {
        return portfolio;
      }
    }
    return null;
  }

  @Override
  public String getPortfolioById(String id) {
    Portfolio portfolio = this.getPortfolioObjectById(id);
    return portfolio == null ? PORTFOLIO_NOT_FOUND : portfolio.toString();
  }

  @Override
  public <T> double getValuation(String id, Predicate<T> filter) {
    FileAbstract fileDatabase = new CSVFile();
    Portfolio portfolioObject = this.getPortfolioObjectById(id);
    return portfolioObject.getValuation((Predicate<Share>) filter);
  }

  @Override
  public boolean addShareToModel(String companyName, int numShares) {
    FileAbstract fileDatabase = new CSVFile();
    try {
      List<String> stockData = fileDatabase.readFromFile(STOCK_DIRECTORY, companyName);

      // TODO get share by company id and number of shares

      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  @Deprecated
  public boolean addShareToModel(String companyName, LocalDate date, double price, int numShares)
      throws IllegalArgumentException {
    Share shareObject = new Share(companyName, date, price, numShares);
    this.shares.add(shareObject);
    return true;
  }

  @Override
  public boolean idIsPresent(String selectedId) {
    return !(this.getPortfolioById(selectedId).equals(PORTFOLIO_NOT_FOUND));
  }

  @Override
  public void addPortfolioByUpload(String path) {
    FileInterface fileObject = new CSVFile();
//    String portfolioString = fileObject.loadPortfolioByPath();
    // return parsePortfolioString(portfolioString);
  }

}

