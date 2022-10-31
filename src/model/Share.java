package model;

import java.util.Date;

class Share implements Comparable {

  private final int numShares;
  private final String companyName;
  private final Date purchaseDate;
  private final double price;

  public Share(String companyName, Date purchaseDate, double price, int numShares)
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

  public double getSharePrice(Date date) {
    FileAbstract fileAPIObject = new CSVFile();
    return fileAPIObject.getShareValueByGivenDate(companyName, date);
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
    return this.companyName + "\t" + this.purchaseDate + "\t" + this.price + "\t" + this.numShares;
  }
}
