package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;

import static utility.Constants.PORTFOLIO_DIRECTORY;

public class ModelImplementation implements ModelInterface {
  int id=0;
  List<Share> shares;
  // maintains last portfolio used for faster inference
  Portfolio portfolioCache;

  @Override
  public void createPortfolio() {
    // remember to delete share list after creating portfolio
    Portfolio portfolioObject = new Portfolio(id++,shares, new Date());
    FileAbstract fileDatabase = new CSVFile();
    fileDatabase.writeToFile(PORTFOLIO_DIRECTORY, String.valueOf(id), portfolioObject.toString().getBytes());
    shares = new ArrayList<>();
    portfolioCache = portfolioObject;
  }

  @Override
  public List<String> getPortfolio() {
    FileAbstract fileDatabase = new CSVFile();
    return fileDatabase.getListOfPortfolios();
  }

  @Override
  public String getPortfolioById(String id) {
    FileAbstract fileDatabase = new CSVFile();
    return fileDatabase.getListOfPortfoliosById(id).toString();
  }

  @Override
  public double getValuation(String id, Predicate<Share> filter) {
    FileAbstract fileDatabase = new CSVFile();
    Portfolio portfolioObject = fileDatabase.getListOfPortfoliosById(id);
    return portfolioObject.getValuation(filter);
  }

  @Override
  public boolean addShareToModel(String companyName) {
    WebAPI apiObject = new WebAPI();
    try {
      Share share = apiObject.getShare(companyName);
      shares.add(share);
      return true;
    } catch (TimeoutException timeoutException) {
      return false;
    }
  }

  @Override
  public boolean addShareToModel(String companyName, Date date, double price, int numShares)
      throws IllegalArgumentException {
    Share shareObject = new Share(companyName, date, price, numShares);
    this.shares.add(shareObject);
    return true;
  }

}
