package model;

import java.time.LocalDate;

/**
 * Mock class to mimic the behaviour of FlexibleModelImplementation.
 */
class MockModel extends FlexibleModelImplementation {

  /**
   * Constructor to create the object of mock model.
   */
  public MockModel() {
    super(new MockFile());
  }

  @Override
  protected double getStockPrice(String companyName, LocalDate date) {
    return 10;
  }
}