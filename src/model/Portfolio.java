package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class Portfolio {

  final String id;
  final Set<Share> shares;
  final LocalDate creationDate;

  public Portfolio(String id, Set<Share> shares, LocalDate creationDate) {
    if (shares.size() == 0) {
      throw new IllegalArgumentException("The size of list of shares must be greater than zero!");
    }
    this.id = id;
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
  public double getValuation(Function<Share,Double> converter) {
//    List<Share> eligibleShares = this.filter(new ArrayList<>(shares), predicate);
    List<Double> mappedShares = this.map(new ArrayList<>(shares), converter);
    return mappedShares.stream().reduce(0.0, Double::sum);
  }
  // filter-map-fold operation
  public double getValuationGivenFilter(Predicate<Share> predicate) {
    List<Share> eligibleShares = this.filter(new ArrayList<>(shares), predicate);
    List<Double> mappedShares = this.map(eligibleShares, Share::getShareValue);
    return mappedShares.stream().reduce(0.0, Double::sum);
  }

  public Set<Share> getListOfShares() {
    return this.shares;
  }

  public String toString() {
    StringBuilder toString = new StringBuilder("+id:" + this.id + "\ncreationDate:"
            + creationDate + "\n*shares:");
    List<Share> shareList = new ArrayList<>(shares);
    for (int i = 0; i < shares.size()-1; i++) {
      String record = shareList.get(i).toString().replace("\n", ",");
      record = record.substring(0, record.length() - 1);
      toString.append(record).append("|");
    }
    String record = shareList.get(shares.size()-1).toString().replace("\n", ",");
    record = record.substring(0, record.length() - 1);
    toString.append(record);
    return toString.toString();
  }

  public String getId() {
    return this.id;
  }
}
