package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;

import static utility.Constants.PORTFOLIO_DIRECTORY;

public class ModelImplementation implements ModelInterface {
  private int id=0;
  private List<Share> shares;
  // maintains last portfolio used for faster inference
  private Portfolio portfolioCache;
  public ModelImplementation(){
    this.shares = new ArrayList<>();

  }
  @Override
  public void createPortfolio() {
    // remember to delete share list after creating portfolio
    Portfolio portfolioObject = new Portfolio(id++,shares, LocalDate.now());
    //System.out.println(portfolioObject.toString());
    FileAbstract fileDatabase = new CSVFile();
    String formattedString = fileDatabase.convertObjectListIntoString(shares);
    String shareFileName = UUID.randomUUID().toString();
    List<String> referenceList = new ArrayList<>();
    referenceList.add(shareFileName);
    if (fileDatabase.writeToFile(PORTFOLIO_DIRECTORY, shareFileName ,formattedString.getBytes())){
      //System.out.println(formattedString);
      fileDatabase.writeToFile(PORTFOLIO_DIRECTORY, PORTFOLIO_DIRECTORY,
              fileDatabase.convertObjectIntoString(portfolioObject.toString(), referenceList).getBytes());
    }
    shares = new ArrayList<>();
    portfolioCache = portfolioObject;
  }

  @Override
  public List<String> getPortfolio() {
    FileAbstract fileDatabase = new CSVFile();
    return fileDatabase.readFromFile(PORTFOLIO_DIRECTORY, PORTFOLIO_DIRECTORY);
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
  public boolean addShareToModel(String companyName, LocalDate date, double price, int numShares)
      throws IllegalArgumentException {
    Share shareObject = new Share(companyName, date, price, numShares);
    this.shares.add(shareObject);
    return true;
  }

}

