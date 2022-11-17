package controller;

import java.time.LocalDate;

import model.ModelImplementation;

import static controller.MockModelUtil.merge;

class MockModelShowValuation extends ModelImplementation {
  StringBuilder log;

  public MockModelShowValuation(StringBuilder log) {
    this.log = log;
  }

  @Override
  public double getValuationGivenDate(String id, LocalDate date) {
    log.append(merge(id, date.toString()));
    return 0.0;
  }
}