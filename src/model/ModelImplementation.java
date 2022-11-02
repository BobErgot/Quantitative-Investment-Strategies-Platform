package model;

import static utility.Constants.PORTFOLIO_DIRECTORY;
import static utility.Constants.PORTFOLIO_NOT_FOUND;
import static utility.Constants.RELATIVE_PATH;
import static utility.Constants.STOCK_DIRECTORY;
import static utility.Constants.TICKER_DIRECTORY;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

public class ModelImplementation implements ModelInterface {

  private final Set<Portfolio> portfolios;
  private Set<Share> shares;

  public ModelImplementation() {
    this.shares = new HashSet<>();
    this.portfolios = new HashSet<>();
    List<String> portfolioStrings = this.getPortfolio();
    FileInterface fileDatabase = new CSVFile();
    for (String portfolioString : portfolioStrings) {
      String[] output = portfolioString.split(",");
      Set<Share> portfolioShare = new HashSet<>();
      for (String share : fileDatabase.readFromFile(RELATIVE_PATH, PORTFOLIO_DIRECTORY,
              output[2].substring(3))) {
        String[] splitShare = share.split(",");
        portfolioShare.add(new Share(splitShare[0], LocalDate.parse(splitShare[1]),
            Double.parseDouble(splitShare[2]), Integer.parseInt(splitShare[3])));
      }
      portfolios.add(new Portfolio(output[0], portfolioShare, LocalDate.parse(output[1])));
    }
  }

  @Override
  public void createPortfolio(String portfolioName) {
    Portfolio portfolioObject = new Portfolio(portfolioName, shares, LocalDate.now());
    FileInterface fileDatabase = new CSVFile();
    String formattedString = fileDatabase.convertObjectListIntoString(new ArrayList<>(shares));
    String shareFileName = UUID.randomUUID().toString();
    List<String> referenceList = new ArrayList<>();
    referenceList.add(shareFileName);
    if (fileDatabase.writeToFile(RELATIVE_PATH, PORTFOLIO_DIRECTORY, shareFileName,
            formattedString.getBytes())) {
      fileDatabase.writeToFile(RELATIVE_PATH, PORTFOLIO_DIRECTORY, PORTFOLIO_DIRECTORY,
          fileDatabase.convertObjectIntoString(portfolioObject.toString(),
              referenceList).getBytes());
    }
    shares = new HashSet<>();
    portfolios.add(portfolioObject);
  }

  @Override
  public List<String> getPortfolio() {
    FileInterface fileDatabase = new CSVFile();
    return fileDatabase.readFromFile(RELATIVE_PATH, PORTFOLIO_DIRECTORY, PORTFOLIO_DIRECTORY);
  }

  private Portfolio getPortfolioObjectById(String id) {

    if (id.length() > 0) {

      for (Portfolio portfolio : portfolios) {

        if (portfolio.getId().equals(id)) {

          return portfolio;
        }
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
  public <T> double getValuationGivenDate(String id, LocalDate date) {
    FileAbstract fileDatabase = new CSVFile();
    Portfolio portfolioObject = this.getPortfolioObjectById(id);
    if (id.length() == 0 || portfolioObject == null) {
      throw new IllegalArgumentException("Invalid ID Passed");
    }
    return portfolioObject.getValuation((share)->this.mapShareGivenDate(share,date));
  }
  private double mapShareGivenDate(Share share, LocalDate date){
    return this.getStockPrice(share.getCompanyName(),date,
            share.getNumShares()) * share.getNumShares();
  }

  //  @Override
  private <T> double getValuationGivenFilter(String id, Predicate<T> filter) {
    FileAbstract fileDatabase = new CSVFile();
    Portfolio portfolioObject = this.getPortfolioObjectById(id);
    if (id.length() == 0 || portfolioObject == null) {
      throw new IllegalArgumentException("Invalid ID Passed");
    }
    return portfolioObject.getValuationGivenFilter((Predicate<Share>) filter);
  }

  public double calculateAveragePrice(int position, List<String> stockData) {
    double high = Double.parseDouble(stockData.get(position).split(",")[2]);
    double low = Double.parseDouble(stockData.get(position).split(",")[3]);
    return low + (high - low) / 2;
  }

  public double searchStockDataList(LocalDate date, List<String> stockData) {
    double stockPrice = -1.00;
    int header = 1;
    int footer = stockData.size() - 1;
    LocalDateTime dateTime = date.atStartOfDay();
    LocalDateTime headerDate = LocalDate.parse(stockData.get(header)
        .split(",", 2)[0]).atStartOfDay();
    LocalDateTime footerDate = LocalDate.parse(stockData.get(footer)
        .split(",", 2)[0]).atStartOfDay();
    if (date.isAfter(ChronoLocalDate.from(headerDate))) {
      return calculateAveragePrice(header, stockData);
    }
    if (date.isBefore(ChronoLocalDate.from(footerDate))) {
      return stockPrice;
    }
    while (header < footer - 1) {
      long footerDistance = footerDate.until(dateTime, ChronoUnit.DAYS);
      long headerDistance = dateTime.until(headerDate, ChronoUnit.DAYS);
      if (headerDistance == 0) {
        return calculateAveragePrice(header, stockData);
      }
      if (footerDistance == 0) {
        return calculateAveragePrice(footer, stockData);
      }
      if (headerDistance > footerDistance) {
        if (header < footer - footerDistance) {
          header = (int) (footer - footerDistance);
          headerDate = LocalDate.parse(stockData.get(header)
              .split(",", 2)[0]).atStartOfDay();
        } else {
          int mid = header + (footer - header) / 2;
          LocalDateTime midDate = LocalDate.parse(stockData.get(mid)
              .split(",", 2)[0]).atStartOfDay();
          if (midDate.isAfter(dateTime)) {
            header = mid;
          } else {
            header++;
          }
        }
      } else {
        if (footer > header + headerDistance) {
          footer = (int) (header + headerDistance);
          footerDate = LocalDate.parse(stockData.get(footer)
              .split(",", 2)[0]).atStartOfDay();
        } else {
          int mid = footer - (footer - header) / 2;
          LocalDateTime midDate = LocalDate.parse(stockData.get(mid)
              .split(",", 2)[0]).atStartOfDay();
          if (midDate.isBefore(dateTime)) {
            footer = mid;
          } else {
            footer--;
          }
        }
      }
    }
    if (header == footer - 1) {
      return calculateAveragePrice(footer, stockData);
    }
    return stockPrice;
  }

  private double getStockPrice(String companyName, LocalDate date, int numShares) {
    if (date.isAfter(LocalDate.now())) {
      return -1;
    }
    FileAbstract fileDatabase = new CSVFile();
    double stockPrice = -1;
    List<String> stockData = fileDatabase.readFromFile(RELATIVE_PATH, STOCK_DIRECTORY, companyName);
    if (stockData.size() != 0) {
      stockPrice = searchStockDataList(date, stockData);
      if (stockPrice > -1) {
        return stockPrice;
      }
    }
    if (stockPrice == -1) {
      APIInterface webAPi = new WebAPI();
      stockData = webAPi.getData(companyName, date);
      if (stockData.size() != 0) {
        stockPrice = searchStockDataList(date, stockData);
        if (stockPrice > -1) {
          return stockPrice;
        }
      }
    }
    return -1;
  }

  @Override
  public boolean addShareToModel(String companyName, LocalDate date, int numShares)
      throws IllegalArgumentException {
    Share shareObject = new Share(companyName, date, getStockPrice(companyName, date, numShares),
        numShares);
    this.shares.add(shareObject);
    return true;
  }

  @Override
  public boolean idIsPresent(String selectedId) {
    return !(this.getPortfolioById(selectedId).equals(PORTFOLIO_NOT_FOUND));
  }

  @Override
  public boolean canCreateShare() {
    return this.shares.size() > 0;
  }

  @Override
  public boolean addPortfolioByUpload(String path) {
    // TODO
    FileAbstract fileAbstractObject = new CSVFile();
//    return fileAbstractObject.addPortfolioFromPath(path);
    return true;
  }

  @Override
  public boolean checkTicker(String symbol) {
    FileAbstract fileDatabase = new CSVFile();
    List<String> stockData = fileDatabase.readFromFile(RELATIVE_PATH, TICKER_DIRECTORY,
            String.valueOf(Character.toUpperCase(symbol.charAt(0))));
    return stockData.contains(symbol);
  }
}