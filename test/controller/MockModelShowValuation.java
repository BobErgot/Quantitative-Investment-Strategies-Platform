package controller;

import static controller.MockModelUtil.merge;

import java.time.LocalDate;
import model.ModelImplementation;

class MockModelShowValuation extends ModelImplementation {

  StringBuilder log;

  public MockModelShowValuation(StringBuilder log) {
    this.log = log;
  }

  @Override
  public <T> double getValuationGivenDate(String id, LocalDate date) {
    log.append(merge(id, date.toString()));
    return 0.0;
  }

}
