package model;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.DuplicateFormatFlagsException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.zip.DataFormatException;

import static utility.Constants.LINE_BREAKER;
import static utility.Constants.PORTFOLIO_DIRECTORY;
import static utility.Constants.PORTFOLIO_FILENAME;
import static utility.Constants.PORTFOLIO_NOT_FOUND;
import static utility.Constants.RELATIVE_PATH;
import static utility.Constants.STOCK_DIRECTORY;
import static utility.Constants.TICKER_DIRECTORY;

/**
 * This class behaves as a gateway between model objects and operations and the controller and
 * provides all model level functionalities to the controller interface from higher level.
 */
public class ModelImplementation implements ModelInterface {

  private final Set<Portfolio> portfolios;
  private HashMap<String, Share> shares;
  private final FileInterface fileInterface;
  private final APIInterface webAPi;

  /**
   * Construct a model implementation object and initialises the local set of shares and
   * portfolios and creates objects of file and api interface which it will be using to work with
   * model on lower level.
   */
  public ModelImplementation() {
    this.shares = new HashMap<>();
    this.portfolios = new HashSet<>();
    this.fileInterface = new CSVFile();
    this.webAPi = new WebAPI();
    this.getPortfolio();
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
    if (fileInterface.writeToFile(RELATIVE_PATH, PORTFOLIO_DIRECTORY, shareFileName,
            formattedString.getBytes())) {
      fileInterface.writeToFile(RELATIVE_PATH, PORTFOLIO_DIRECTORY, PORTFOLIO_FILENAME,
              fileInterface.convertObjectIntoString(portfolioObject.toString(),
                      referenceList).getBytes());
    }
    shares = new HashMap<>();
    portfolios.add(portfolioObject);
  }

  @Override
  public List<String> getPortfolio() {
    List<String> portfolioOutput = new ArrayList<>();
    List<String> fileContent = fileInterface.readFromFile(RELATIVE_PATH, PORTFOLIO_DIRECTORY,
            PORTFOLIO_FILENAME);
    for (String portfolio : fileContent) {
      String[] portfolioFields = portfolio.trim().split(",");
      List<String> stockFileContent = fileInterface.readFromFile(RELATIVE_PATH, PORTFOLIO_DIRECTORY,
              portfolioFields[2].substring(3));
      Set<Share> shareList = new HashSet<>();
      for (String stock : stockFileContent) {
        String[] stockFields = stock.trim().split(",");
        Share shareObject = new Share(stockFields[0], LocalDate.parse(stockFields[1]),
                Double.parseDouble(stockFields[2]), Integer.parseInt(stockFields[3]));
        shareList.add(shareObject);
      }
      Portfolio portfolioObject = new Portfolio(portfolioFields[0], shareList,
              LocalDate.parse(portfolioFields[1]));
      portfolioOutput.add("-------------------------------------------------------");
      portfolioOutput.add(portfolioObject.toString());
      portfolioOutput.add("-------------------------------------------------------" + LINE_BREAKER);
      portfolios.add(portfolioObject);
    }
    return portfolioOutput;
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
    Portfolio portfolioObject = this.getPortfolioObjectById(id);
    if (id.length() == 0 || portfolioObject == null) {
      throw new IllegalArgumentException("Invalid ID Passed");
    }
    return portfolioObject.getValuation((share) -> this.mapShareGivenDate(share, date));
  }

  private double mapShareGivenDate(Share share, LocalDate date) {
    double stockPrice = this.getStockPrice(share.getCompanyName(), date);
    if (stockPrice == -1) {
      throw new IllegalArgumentException("Data not available for this date. Try again after some "
              + "time");
    }
    return this.getStockPrice(share.getCompanyName(), date) * share.getNumShares();
  }

  private <T> double getValuationGivenFilter(String id, Predicate<T> filter) {
    Portfolio portfolioObject = this.getPortfolioObjectById(id);
    if (id.length() == 0 || portfolioObject == null) {
      throw new IllegalArgumentException("Invalid ID Passed");
    }
    return portfolioObject.getValuationGivenFilter((Predicate<Share>) filter);
  }

  private double calculateAveragePrice(int position, List<String> stockData) {
    double high = Double.parseDouble(stockData.get(position).split(",")[2]);
    double low = Double.parseDouble(stockData.get(position).split(",")[3]);
    return low + (high - low) / 2;
  }

  private double searchStockDataList(LocalDate date, List<String> stockData) {
    double stockPrice = -1.00;
    int header = 1;
    int footer = stockData.size() - 1;
    LocalDateTime dateTime = date.atStartOfDay();
    LocalDateTime headerDate = LocalDate.parse(stockData.get(header).split(",",
            2)[0]).atStartOfDay();
    LocalDateTime footerDate = LocalDate.parse(stockData.get(footer).split(",",
            2)[0]).atStartOfDay();
    if (date.isAfter(ChronoLocalDate.from(headerDate))) {
      return calculateAveragePrice(header, stockData);
    }
    if (date.isBefore(ChronoLocalDate.from(footerDate))) {
      return 0;
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
          headerDate = LocalDate.parse(stockData.get(header).split(",",
                  2)[0]).atStartOfDay();
        } else {
          int mid = header + (footer - header) / 2;
          LocalDateTime midDate = LocalDate.parse(stockData.get(mid).split(",",
                  2)[0]).atStartOfDay();
          if (midDate.isAfter(dateTime)) {
            header = mid;
          } else {
            header++;
          }
        }
      } else {
        if (footer > header + headerDistance) {
          footer = (int) (header + headerDistance);
          footerDate = LocalDate.parse(stockData.get(footer).split(",",
                  2)[0]).atStartOfDay();
        } else {
          int mid = footer - (footer - header) / 2;
          LocalDateTime midDate = LocalDate.parse(stockData.get(mid).split(",",
                  2)[0]).atStartOfDay();
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

  private double getStockPrice(String companyName, LocalDate date) {
    double stockPrice = -1;
    List<String> stockData = fileInterface.readFromFile(RELATIVE_PATH, STOCK_DIRECTORY,
            companyName);
    if (stockData.size() != 0) {
      stockPrice = searchStockDataList(date, stockData);
      if (stockPrice > -1) {
        return stockPrice;
      }
    }
    if (stockPrice == -1) {
      String webAPIData = webAPi.getData(companyName, date);
      stockData = fileInterface.validateFormat(webAPIData);
      if (stockData != null && stockData.size() != 0) {
        StringBuilder fileData = new StringBuilder();
        for (String stock : stockData) {
          fileData.append(stock).append(LINE_BREAKER);
        }
        fileInterface.writeToFile(RELATIVE_PATH, STOCK_DIRECTORY, companyName,
                fileData.toString().getBytes());
        stockPrice = searchStockDataList(date, stockData);
        if (stockPrice > -1) {
          return stockPrice;
        }
      }
    }
    return -1;
  }

  @Override
  public boolean addShareToModel(String companyName, LocalDate date, int numShares,
                                 double stockPrice) throws IllegalArgumentException {
    if (!this.checkTicker(companyName)) {
      throw new IllegalArgumentException("Invalid Ticker");
    }

    if (stockPrice == -1) {
      stockPrice = getStockPrice(companyName, date);
    }
    if (stockPrice == -1) {
      throw new IllegalArgumentException("Data not available");
    }
    Share shareObject = new Share(companyName, date, stockPrice, numShares);
    if (this.shares.get(companyName) == null) {
      this.shares.put(companyName, shareObject);
      return true;
    } else {
      this.shares.put(companyName, shareObject);
      return false;
    }
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
  public boolean addPortfolioByUpload(String path, String folderName, String fileName,
                                      String extension) throws DataFormatException,
          FileNotFoundException, DuplicateFormatFlagsException {
    List<String> uploadFileData;
    if (fileInterface.exists(path, folderName, fileName, extension)) {
      uploadFileData = new ArrayList<>(fileInterface.readFromFile(path, folderName, fileName));
    } else {
      throw new FileNotFoundException("Error: Invalid path to file");
    }
    List<String> failedShares = new ArrayList<>();
    List<String> failedPortfolios = new ArrayList<>();
    for (String data : uploadFileData) {
      if (null != data && !data.trim().equals("")) {
        String[] portfolioFields = data.split(",");
        if (portfolioFields.length == 3) {
          String file = portfolioFields[2].substring(3);
          String[] fileMetadata = file.split("\\.");
          List<String> stockFileData = new ArrayList<>();
          if (fileInterface.exists(path, folderName, fileMetadata[0], fileMetadata[1])) {
            stockFileData.addAll(fileInterface.readFromFile(path, folderName, file));
          } else {
            throw new FileNotFoundException("Error: Invalid path to stock list file");
          }
          for (String stockData : stockFileData) {
            String[] stockDataField = stockData.split(",");
            try {
              addShareToModel(stockDataField[0], LocalDate.parse(stockDataField[1]),
                      Integer.parseInt(stockDataField[3]), Double.parseDouble(stockDataField[2]));
            } catch (IllegalArgumentException ie) {
              failedShares.add(stockDataField[0]);
            }
          }
          try {
            createPortfolio(portfolioFields[0].trim(), LocalDate.parse(portfolioFields[1].trim()));
          } catch (IllegalArgumentException illegalArgumentException) {
            failedPortfolios.add(portfolioFields[0].trim());
          }
        } else {
          throw new DataFormatException("Error: File content not in proper format.");
        }
      }
    }
    if (failedPortfolios.size() > 0 || failedShares.size() > 0) {
      String failedShare = failedShares.toString();
      String failedPortfolio = failedPortfolios.toString();
      String failedMessage = "";
      if (failedPortfolio.length() > 0) {
        failedMessage += "The following portfolios already exist: " + failedPortfolio + "\n";
      }
      if (failedShare.length() > 0) {
        failedMessage += "The following shares are invalid: " + failedShare;
      }
      throw new DuplicateFormatFlagsException(failedMessage);
    }
    return (uploadFileData.size() > 0);
  }

  @Override
  public boolean checkTicker(String symbol) {
    List<String> stockData = fileInterface.readFromFile(RELATIVE_PATH, TICKER_DIRECTORY,
            String.valueOf(Character.toUpperCase(symbol.charAt(0))));
    return stockData.contains(symbol);
  }
}