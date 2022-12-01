package model;

import static utility.Constants.LINE_BREAKER;

import java.time.LocalDate;

/**
 * This class represents a share. A share has a date of purchase, company symbol/ticker, price on
 * the date of purchase and number of stocks bought.
 */
public class Share implements Comparable<Object> {

  private final LocalDate purchaseDate;
  private final String companyName;
  private final double price;
  private final double numShares;

  /**
   * Construct a Share object that has the provided company ticker, purchase date, price on purchase
   * date and number of stocks bought.
   *
   * @param companyName  company ticker/symbol
   * @param purchaseDate date the share was bought
   * @param price        price of each stock on purchase date
   * @param numShares    number of stocks bought
   */
  public Share(String companyName, LocalDate purchaseDate, double price, double numShares)
          throws IllegalArgumentException {
    if (companyName.length() == 0) {
      throw new IllegalArgumentException("Company name cannot be blank!");
    }
    if (price <= 0) {
      throw new IllegalArgumentException("Stock price cannot be less than or equal to zero!");
    }
    if (numShares <= 0) {
      throw new IllegalArgumentException("Number of share cannot be less than or equal to zero!");
    }
    this.companyName = companyName;
    this.purchaseDate = purchaseDate;
    this.price = price;
    this.numShares = numShares;
  }

  public double getPrice() {
    return price;
  }

  /**
   * Return the ticker/symbol of this share's stock.
   *
   * @return the ticker/symbol as string
   */
  public String getCompanyName() {
    return this.companyName;
  }

  /**
   * Return the value of this share.
   *
   * @return the value of share as double
   */
  public double getShareValue() {
    return this.numShares * this.price;
  }

  /**
   * Return the number of the stocks in this share.
   *
   * @return number of stocks as integer
   */
  public double getNumShares() {
    return this.numShares;
  }

  public LocalDate getPurchaseDate() {
    return purchaseDate;
  }

  @Override
  public int compareTo(Object object) throws IllegalArgumentException {
    if (object instanceof Share) {
      throw new IllegalArgumentException("lksaj");
    }
    Share share = (Share) object;
    double comparison = this.price - share.price;
    return comparison == 0 ? 0 : comparison > 0 ? 1 : -1;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Share)) {
      return false;
    }
    Share other = (Share) object;
    return this.companyName.equals(other.companyName) && this.purchaseDate == other.purchaseDate
            && this.price == other.price && this.numShares == other.numShares;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + this.companyName.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "+companyName:" + this.companyName + LINE_BREAKER + "purchaseDate:"
            + this.purchaseDate + LINE_BREAKER + "price:" + this.price + LINE_BREAKER
            + "numShares:" + this.numShares + LINE_BREAKER;
  }
}