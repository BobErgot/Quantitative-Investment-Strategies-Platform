package controller.MockModels;

import static controller.MockModels.MockModelUtil.merge;

import java.time.LocalDate;
import model.ModelImplementation;

public class MockModelCreatePortfolio extends ModelImplementation {
  private StringBuilder log;
  public MockModelCreatePortfolio(StringBuilder log){
    this.log = log;
  }
  @Override
  public void createPortfolio(String portfolioName, LocalDate date) {
    log.append(merge("createPortfolio",portfolioName,date.toString()));
  }
}