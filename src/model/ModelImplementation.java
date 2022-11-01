package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;

import static utility.Constants.PORTFOLIO_DIRECTORY;

public class ModelImplementation implements ModelInterface {
  private int id=0;
  private Set<Share> shares;
  // maintains last portfolio used for faster inference
  private List<Portfolio> portfolios;
  public ModelImplementation(){
    this.shares = new HashSet<>();
    this.portfolios = new ArrayList<>();
    FileInterface fileDatabase = new CSVFile();
    System.out.println(this.getPortfolio().size());
////    String[] protfolioStrings = this.getPortfolio().split(",");
////    for(int i=2; i<protfolioStrings.length; i+=2)
////      System.out.println(protfolioStrings[i]);
////      fileDatabase.readFromFile(PORTFOLIO_DIRECTORY, portfolioStrings.get(0));
  }
  @Override
  public void createPortfolio() {
    // remember to delete share list after creating portfolio
    Portfolio portfolioObject = new Portfolio(id++,shares, LocalDate.now());
    //System.out.println(portfolioObject.toString());
    FileInterface fileDatabase = new CSVFile();
    String formattedString = fileDatabase.convertObjectListIntoString(new ArrayList<>(shares));
    String shareFileName = UUID.randomUUID().toString();
    List<String> referenceList = new ArrayList<>();
    referenceList.add(shareFileName);
    if (fileDatabase.writeToFile(PORTFOLIO_DIRECTORY, shareFileName ,formattedString.getBytes())){
      //System.out.println(formattedString);
      fileDatabase.writeToFile(PORTFOLIO_DIRECTORY, PORTFOLIO_DIRECTORY,
              fileDatabase.convertObjectIntoString(portfolioObject.toString(), referenceList).getBytes());
    }
    shares = new HashSet<>();
    portfolios.add(portfolioObject);
  }

  @Override
  public List<String> getPortfolio() {
    FileInterface fileDatabase = new CSVFile();
    return fileDatabase.readFromFile(PORTFOLIO_DIRECTORY, PORTFOLIO_DIRECTORY);
  }

  @Override
  public String getPortfolioById(String id) {
    FileAbstract fileDatabase = new CSVFile();
    return fileDatabase.getListOfPortfoliosById(id).toString();
  }

  @Override
  public <T> double getValuation(String id, Predicate<T> filter) {
    FileAbstract fileDatabase = new CSVFile();
    Portfolio portfolioObject = fileDatabase.getListOfPortfoliosById(id);
    return portfolioObject.getValuation((Predicate<Share>) filter);
  }

  @Override
  public boolean addShareToModel(String companyName, int numShares) {
    WebAPI apiObject = new WebAPI();
    try {
      // TODO get share by company id and number of shares
//      Share share = apiObject.getShare(companyName,numShares);
      Share share = apiObject.getShare(companyName);

      shares.add(share);
      return true;
    } catch (TimeoutException timeoutException) {
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
  public boolean idIsPresent(int selectedId) {
    for(Portfolio portfolio: portfolios){
      if(portfolio.getId()==selectedId)
        return true;
    }
    return false;
  }

  @Override
  public void addPortfolioByUpload(String path) {
    FileInterface fileObject = new CSVFile();
//    String portfolioString = fileObject.loadPortfolioByPath();
    // return parsePortfolioString(portfolioString);
  }

}

