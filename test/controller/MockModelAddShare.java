package controller;

import java.time.LocalDate;

import model.ModelImplementation;

import static controller.MockModelUtil.merge;

class MockModelAddShare extends ModelImplementation {
  private final StringBuilder log;

  public MockModelAddShare(StringBuilder log) {
    this.log = log;
  }

  @Override
  public boolean addShareToModel(String companyName, LocalDate date, int numShares,
                                 double stockPrice) {
    log.append(merge(companyName, date.toString(), Integer.toString(numShares),
            Double.toString(stockPrice)));
    return true;
  }
}