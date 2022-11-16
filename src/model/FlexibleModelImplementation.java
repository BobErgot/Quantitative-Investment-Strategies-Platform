package model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class FlexibleModelImplementation extends ModelImplementation implements
    FlexibleModelInterface {

  public FlexibleModelImplementation(){
    super();
  }
  protected FlexibleModelImplementation(FileInterface fileInterface){
    super(fileInterface);
  }
  @Override
  public double sellStocks(String id, String symbol, int numShares) {
    // Checking if numShares is less than the currently less than bought shares
    Portfolio portfolioToModify = this.getPortfolioObjectById(id);
    Set<Share> newShares = new HashSet<>(portfolioToModify.getListOfShares());
    if (!checkValidNumStocks(symbol, numShares, newShares)) {
      throw new IllegalArgumentException(
          "Ticker is incorrect or number of shares is less than shares bought in this portfolio!");
    }
    double stockSellingPrice = 0.0;

    for (Share share : newShares) {
      if (share.getCompanyName().equals(symbol)) {
        double currentShareSellingPrice = this.getStockPrice(share.getCompanyName(),
            LocalDate.now());
        newShares.remove(share);
        if (share.getNumShares() > numShares) {
          stockSellingPrice += (share.getNumShares() - numShares) * currentShareSellingPrice;
          newShares.add(
              new Share(share.getCompanyName(), share.getPurchaseDate(), share.getShareValue(),
                  share.getNumShares() - numShares));
          break;
        } else {
          numShares -= share.getNumShares();
          stockSellingPrice += share.getNumShares() * currentShareSellingPrice;
        }
      }
    }
    for (Share share : newShares) {
      this.addShareToModel(share);
    }
    portfolios.remove(portfolioToModify);
    this.createPortfolio(portfolioToModify.getId(), portfolioToModify.getCreationDate());
    return stockSellingPrice;
  }

  @Override
  public boolean addStockToExistingPortfolio(String portfolioId, String companyName, LocalDate date,
      int numShares, double stockPrice) {
    // Use existing methods to finish this function.
    return false;
  }

}
