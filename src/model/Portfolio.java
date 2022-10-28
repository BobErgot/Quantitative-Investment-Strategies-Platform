package model;

import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class Portfolio {

  final List<Share> shares;
  final Date creationDate;

  public Portfolio(List<Share> shares, Date creationDate) {
    if (shares.size() == 0) {
      throw new IllegalArgumentException("The size of list of shares must be greater than zero!");
    }
    this.shares = shares;
    this.creationDate = creationDate;
  }

  private <T> List<T> filter(List<T> shares, Predicate<T> predicate) {
    return shares.stream().filter(predicate).collect(Collectors.<T>toList());
  }

  private <T, R> List<R> map(List<T> shares, Function<T, R> converter) {
    return shares.stream().map(converter).collect(Collectors.<R>toList());
  }

  // filter-map-fold operation
  public double getValuation(Predicate<Share> predicate) {
    List<Share> eligibleShares = this.filter(shares, predicate);
    List<Double> mappedShares = this.map(eligibleShares, Share::getShareValue);
    return mappedShares.stream().reduce(0.0, Double::sum);
  }

  public List<Share> getListOfShares() {
    return this.shares;
  }

  public String toString() {
    String toString = "Portfolio created on " + creationDate + " has shares:\n";
    for (int i = 0; i < shares.size(); i++) {
      toString += (i + 1) + ". " + shares.get(i);
    }
    return toString;
  }


}
