package model;

import java.time.LocalDate;

class MockModel extends FlexibleModelImplementation {

  public MockModel() {
    super(new MockFile());
  }
  @Override
  protected double getStockPrice(String companyName, LocalDate date){
    return 10;
  }
}