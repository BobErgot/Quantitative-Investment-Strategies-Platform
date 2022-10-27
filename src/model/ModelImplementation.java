package model;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

public class ModelImplementation implements ModelInterface {
  List<Share> shares;
  // maintains last portfolio used for faster inference
  Portfolio portfolioCache;
  @Override
  public void createPortfolio() {
    // remember to delete share list after creating portfolio
  }

  @Override
  public List<String> getPortfolio() {
    return null;
  }

  @Override
  public String getPortfolioById(String id) {
    return null;
  }

  @Override
  public <T> double getValuation(String id, Predicate<T> filter) {
    return 0;
  }

  @Override
  public boolean addShareToModel(String companyName) {
    return false;
  }

  @Override
  public boolean addShareToModel(String companyName, Date date, double price, int numShares) {
    return false;
  }

}
