package model;

import java.time.LocalDate;

class Share implements Comparable {
  private final LocalDate purchaseDate;
  private final String companyName;
  private final double price;
  private final int numShares;

  public Share(String companyName, LocalDate purchaseDate, double price, int numShares)
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

  public String getCompanyName() {
    return this.companyName;
  }


  public double getShareValue() {
    return this.numShares * this.price;
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

  public int getNumShares() {
    return this.numShares;
  }

  public String toString() {
    return "+companyName:" + this.companyName + "\n" + "purchaseDate:" + this.purchaseDate
            + "\n" + "price:" + this.price + "\n" + "numShares:" + this.numShares + "\n";
  }
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + this.companyName.hashCode();
    return result;
  }
}
