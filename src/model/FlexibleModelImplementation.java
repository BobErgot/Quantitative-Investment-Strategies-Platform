package model;

import static utility.Constants.BROKER_FEES;
import static utility.Constants.MUTABLE;
import static utility.Constants.PORTFOLIO_DIRECTORY;
import static utility.Constants.PORTFOLIO_FILENAME;
import static utility.Constants.RELATIVE_PATH;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

public class FlexibleModelImplementation extends ModelAbstract {

  public FlexibleModelImplementation() {
    super();
  }

  protected FlexibleModelImplementation(FileInterface fileInterface) {
    super(fileInterface);
  }

  @Override
  public double sellStocks(String id, String symbol, int numShares) {
    // Checking if numShares is less than the currently less than bought shares
    Portfolio portfolioToModify = this.getPortfolioObjectById(id);
    Set<Share> newShares = new HashSet<>(portfolioToModify.getListOfShares());
    if (!checkValidNumStocks(symbol, numShares, newShares)) {
      throw new IllegalArgumentException("Number of shares is less than shares bought in this portfolio!");
    }
    double stockSellingPrice = 0.0;

    for (Share share : newShares) {
      if (share.getCompanyName().equals(symbol)) {
        double currentShareSellingPrice = this.getStockPrice(share.getCompanyName(), LocalDate.now());
        newShares.remove(share);
        if (share.getNumShares() > numShares) {
          stockSellingPrice += numShares * currentShareSellingPrice;
          newShares.add(new Share(share.getCompanyName(), share.getPurchaseDate(), share.getPrice(), share.getNumShares() - numShares));
          break;
        } else {
          numShares -= share.getNumShares();
          stockSellingPrice += share.getNumShares() * currentShareSellingPrice;
        }
      }
    }
    this.modifyPortfolio(portfolioToModify.getId(), newShares, portfolioToModify.getCreationDate());
    return stockSellingPrice - BROKER_FEES;
  }

  protected boolean modifyPortfolio(String id, Set<Share> sharesToModify, LocalDate creationDate) {
    Set<Share> processedShare = new HashSet<>();
    String stockFileName = null;
    for (Share share : sharesToModify) {
      Share finalShare = new Share(share.getCompanyName(), share.getPurchaseDate(),
              share.getPrice(), share.getNumShares());
      processedShare.add(finalShare);
    }
    portfolios.remove(new Portfolio(id, sharesToModify, creationDate));
    portfolios.add(new Portfolio(id, processedShare, creationDate));
    List<String> fileContent = fileInterface.readFromFile(RELATIVE_PATH, PORTFOLIO_DIRECTORY,
            PORTFOLIO_FILENAME);
    for (String portfolio : fileContent) {
      String[] portfolioRecord = portfolio.split(",");
      if (portfolioRecord.length == 4) {
        if (id.equals(portfolioRecord[0])) {
          stockFileName = portfolioRecord[2].substring(3);
        }
      }
    }
    if (null != stockFileName && !stockFileName.isEmpty()) {
      fileInterface.clearFile(RELATIVE_PATH, PORTFOLIO_DIRECTORY, stockFileName, "csv");
      String formattedString = fileInterface
              .convertObjectListIntoString(new ArrayList<>(processedShare));
      return fileInterface.writeToFile(RELATIVE_PATH, PORTFOLIO_DIRECTORY, stockFileName,
              formattedString.getBytes());
    }
    return false;
  }

  @Override
  public void appendPortfolio(String portfolioName) throws NoSuchElementException {
    if (this.shares.size() == 0) {
      throw new NoSuchElementException("No new shares to append!");
    }
    Portfolio portfolioToModify = this.getPortfolioObjectById(portfolioName);
    Set<Share> sharesToModify = new HashSet<>(portfolioToModify.getListOfShares());
    this.modifyPortfolio(portfolioName, sharesToModify, portfolioToModify.getCreationDate());
  }

  @Override
  public void createPortfolio(String portfolioName, LocalDate date) {
    Set<Share> sharesSet = new HashSet<>(shares.values());
    if (this.idIsPresent(portfolioName)) {
      throw new IllegalArgumentException("Portfolio is already present!");
    }
    Portfolio portfolioObject = new Portfolio(portfolioName, sharesSet, date);
    String formattedString = fileInterface.convertObjectListIntoString(new ArrayList<>(sharesSet));
    String shareFileName = UUID.randomUUID().toString();
    List<String> referenceList = new ArrayList<>();
    referenceList.add(shareFileName);
    if (fileInterface.writeToFile(RELATIVE_PATH, PORTFOLIO_DIRECTORY, shareFileName, formattedString.getBytes())) {
      fileInterface.writeToFile(RELATIVE_PATH, PORTFOLIO_DIRECTORY, PORTFOLIO_FILENAME, (fileInterface.convertObjectIntoString(portfolioObject.toString(), referenceList) + MUTABLE).getBytes());
    }
    shares = new HashMap<>();
    portfolios.add(portfolioObject);
  }

  @Override
  public List<String> getPortfolio() {
    List<String> portfolioOutput = new ArrayList<>();
    List<String> fileContent = fileInterface.readFromFile(RELATIVE_PATH, PORTFOLIO_DIRECTORY, PORTFOLIO_FILENAME);
    for (String portfolio : fileContent) {
      String[] portfolioFields = portfolio.trim().split(",");
      List<String> stockFileContent = fileInterface.readFromFile(RELATIVE_PATH, PORTFOLIO_DIRECTORY, portfolioFields[2].substring(3));
      Set<Share> shareList = new HashSet<>();
      for (String stock : stockFileContent) {
        String[] stockFields = stock.trim().split(",");
        Share shareObject = new Share(stockFields[0], LocalDate.parse(stockFields[1]), Double.parseDouble(stockFields[2]), Integer.parseInt(stockFields[3]));
        shareList.add(shareObject);
      }
      Portfolio portfolioObject = new Portfolio(portfolioFields[0], shareList, LocalDate.parse(portfolioFields[1]));
      String serialNumber = portfolioOutput.size() + ".";
      String portfolioHeaderString;
      if (portfolioFields.length == 4) {
        portfolioHeaderString = String.format("||%-18s||%-40s||%-18s||%-40s||", serialNumber,
            portfolioFields[0], LocalDate.parse(portfolioFields[1]), portfolioFields[3]);
      } else {
        portfolioHeaderString = String.format("||%-18s||%-40s||%-18s||", serialNumber, portfolioFields[0], LocalDate.parse(portfolioFields[1]));
      }
      portfolioOutput.add(portfolioHeaderString);
      portfolios.add(portfolioObject);
    }
    return portfolioOutput;
  }
}