package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

import static utility.Constants.PORTFOLIO_DIRECTORY;
import static utility.Constants.PORTFOLIO_NOT_FOUND;
import static utility.Constants.STOCK_DIRECTORY;

public class ModelImplementation implements ModelInterface {

  private final Set<Portfolio> portfolios;
  private Set<Share> shares;

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
      fileDatabase.writeToFile(PORTFOLIO_DIRECTORY, PORTFOLIO_DIRECTORY, fileDatabase.convertObjectIntoString(portfolioObject.toString(), referenceList).getBytes());
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
  public double getStockPrice(String companyName, int numShares) {
    FileAbstract fileDatabase = new CSVFile();
    double stockPrice = -1.00;
    List<String> stockData = fileDatabase.readFromFile(STOCK_DIRECTORY, companyName);
    if (stockData.size() != 0) {
      for (String stockRecord : stockData) {
        if (stockRecord.substring(0, 10).equals(LocalDate.now().toString())) {
          String[] inputLineData = stockRecord.split(",");
          double high = Double.parseDouble(inputLineData[2]);
          double low = Double.parseDouble(inputLineData[3]);
          stockPrice = new Random().nextDouble(low, high);
        }
      }
    } else {
      APIInterface webAPi = new WebAPI();
      stockPrice = webAPi.getShareValueByGivenDate(companyName, LocalDate.now());
    }
    return (stockPrice >= 0)? numShares*stockPrice : -1.00;
  }

  @Override
  @Deprecated
  public boolean addShareToModel(String companyName, LocalDate date, double price, int numShares) throws IllegalArgumentException {
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
    // TODO
  }

}

