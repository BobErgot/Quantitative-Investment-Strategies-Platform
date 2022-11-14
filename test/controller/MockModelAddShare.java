package controller;

import static controller.MockModelUtil.merge;

import java.time.LocalDate;

import model.ModelImplementation;
class MockModelAddShare extends MockModel {
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