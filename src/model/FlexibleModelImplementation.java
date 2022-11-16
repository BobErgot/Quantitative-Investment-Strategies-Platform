package model;

import static utility.Constants.BROKER_FEES;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

public class FlexibleModelImplementation extends ModelAbstract{

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
          stockSellingPrice += numShares * currentShareSellingPrice;
          newShares.add(new Share(share.getCompanyName(), share.getPurchaseDate(), share.getPrice(),
              share.getNumShares() - numShares));
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

  protected void modifyPortfolio(String id, Set<Share> sharesToModify, LocalDate creationDate) {
    for (Share share : sharesToModify) {
      Share finalShare = new Share(share.getCompanyName(), share.getPurchaseDate(),
          share.getPrice() - BROKER_FEES / share.getNumShares(), share.getNumShares());
      this.addShareToModel(finalShare);
    }
    portfolios.remove(new Portfolio(id, sharesToModify, creationDate));
    this.createPortfolio(id, creationDate);
  }

  @Override
  public void appendPortfolio(String portfolioName) throws NoSuchElementException{
    if(this.shares.size()==0)
      throw new NoSuchElementException("No new shares to append!");
    Portfolio portfolioToModify = this.getPortfolioObjectById(portfolioName);
    Set<Share> sharesToModify = new HashSet<>(portfolioToModify.getListOfShares());
    this.modifyPortfolio(portfolioName, sharesToModify, portfolioToModify.getCreationDate());
  }

}
