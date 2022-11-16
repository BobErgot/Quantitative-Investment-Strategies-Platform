package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * This class represents a portfolio. A portfolio has a unique id for one user, creation date of
 * portfolio and list of share objects associated with this portfolio.
 */
class Portfolio {

  final String id;
  final LocalDate creationDate;
  final Set<Share> shares;

  /**
   * Construct a Portfolio object that has the provided unique id for one user, creation date of
   * portfolio and set of share objects associated with it.
   *
   * @param id           unique string as portfolio name
   * @param shares       set of shares associated with this portfolio
   * @param creationDate date when this portfolio was created
   */
  public Portfolio(String id, Set<Share> shares, LocalDate creationDate) {
    if (shares.size() == 0) {
      throw new IllegalArgumentException("The size of list of shares must be greater than zero!");
    }
    this.id = id;
    this.shares = shares;
    this.creationDate = creationDate;
  }

  /**
   * Return the unique id of this portfolio.
   *
   * @return the id as string
   */
  public String getId() {
    return this.id;
  }

  /**
   * Return the set of shares associated with this portfolio.
   *
   * @return the id as string
   */
  public Set<Share> getListOfShares() {
    return this.shares;
  }

  private <T> List<T> filter(List<T> shares, Predicate<T> predicate) {
    return shares.stream().filter(predicate).collect(Collectors.<T>toList());
  }

  private <T> T reduce(List<T> shares, BinaryOperator<T> fold) {
    return (T) shares.stream().reduce(fold);
  }

  private <T, R> List<R> map(List<T> shares, Function<T, R> converter) {
    return shares.stream().map(converter).collect(Collectors.<R>toList());
  }

  /**
   * It returns the valuation for the portfolio based on the double value mapped to each share in
   * the portfolio.
   *
   * @param converter it is a mapper between share object and double value
   * @return total valuation of all shares in portfolio based on double value associated with it
   */
  public double getValuation(Function<Share, Double> converter) {
    List<Double> mappedShares = this.map(new ArrayList<>(shares), converter);
    return mappedShares.stream().reduce(0.0, Double::sum);
  }

  protected double getValuationGivenFilter(Predicate<Share> predicate) {
    List<Share> eligibleShares = this.filter(new ArrayList<>(shares), predicate);
    List<Double> mappedShares = this.map(eligibleShares, Share::getShareValue);
    return mappedShares.stream().reduce(0.0, Double::sum);
  }

  protected double getValuationGivenFilterAndMap(Predicate<Share> predicate,
      Function<Share, Double> converter) {
    List<Share> eligibleShares = this.filter(new ArrayList<>(shares), predicate);
    List<Double> mappedShares = this.map(eligibleShares, converter);
    return mappedShares.stream().reduce(0.0, Double::sum);
  }

  @Override
  public String toString() {
    StringBuilder toString = new StringBuilder(
        "+id:" + this.id + "\ncreationDate:" + creationDate + "\n*shares:");
    List<Share> shareList = new ArrayList<>(shares);
    for (int i = 0; i < shares.size() - 1; i++) {
      String record = shareList.get(i).toString().replace("\n", ",");
      record = record.substring(0, record.length() - 1);
      toString.append(record).append("|");
    }
    String record = shareList.get(shares.size() - 1).toString().replace("\n", ",");
    record = record.substring(0, record.length() - 1);
    toString.append(record);
    return toString.toString();
  }

  public LocalDate getCreationDate() {
    return creationDate;
  }

  public LocalDate[] getDateRangeOfStockData() {
    LocalDate[] range = {LocalDate.MAX, LocalDate.MIN};
    for(Share share : shares){
      if(share.getPurchaseDate().compareTo(range[0])<0)
        range[0] =  share.getPurchaseDate();
      else  if(share.getPurchaseDate().compareTo(range[1])>=0)
        range[1] =  share.getPurchaseDate();
    }
    return range;
  }
}
