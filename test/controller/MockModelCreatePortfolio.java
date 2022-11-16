package controller;

import static controller.MockModelUtil.merge;

import java.time.LocalDate;
import model.ModelImplementation;

class MockModelCreatePortfolio extends ModelImplementation {
  private final StringBuilder log;

  public MockModelCreatePortfolio(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void createPortfolio(String portfolioName, LocalDate date) {
    log.append(merge("createPortfolio", portfolioName, date.toString()));
  }
}