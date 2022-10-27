package model;

import java.util.Date;

class Share {
  String companyName;
  Date purchaseDate;
  double price;
  int numShares;

  public Share(String companyName, Date purchaseDate, double price, int numShares) {
    this.companyName = companyName;
    this.purchaseDate = purchaseDate;
    this.price = price;
    this.numShares = numShares;
  }

  public String getCompanyName() {
    return this.companyName;
  }

  public int getNumShares() {
    return this.numShares;
  }

  public double getSharePrice(Date date){
    FileDatabase fileAPIObject = new FileDatabase();
    return fileAPIObject.getShareValueByGivenDate(companyName,date);
  }
}
