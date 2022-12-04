package generalcontroller;

import static generalcontroller.MockModelUtil.merge;

import java.time.LocalDate;
import model.ModelImplementation;

class MockModel extends ModelImplementation {

  private final StringBuilder log;

  public MockModel(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void createPortfolio(String portfolioName, LocalDate date) {
    log.append(merge("createPortfolio", portfolioName, date.toString()));
  }

  @Override
  public boolean addPortfolioByUpload(String path, String folderName, String fileName,
      String extension) {
    log.append(merge(path, folderName, fileName, extension));
    return true;
  }

  @Override
  public boolean addShareToModel(String companyName, LocalDate date, int numShares,
      double stockPrice) {
    log.append(merge(companyName, date.toString(), "" + numShares, "" + stockPrice));
    return true;
  }

  @Override
  public double sellStocks(String portfolioName, String symbol, int numShares, LocalDate date) {
    log.append(merge(portfolioName, symbol, "" + numShares, date.toString()));
    return 1.0;
  }

  @Override
  public double getCostBasis(String id, LocalDate date) {
    log.append(merge(id, date.toString()));
    return 1.0;
  }

  @Override
  public double getValuationGivenDate(String id, LocalDate date) {
    log.append(merge(id, date.toString()));
    return 1.0;
  }
}
