package controller.MockModels;

import static controller.MockModels.MockModelUtil.merge;

import java.time.LocalDate;
import model.ModelImplementation;
import model.ModelInterface;

public class MockModelShowValuation extends ModelImplementation {
  StringBuilder log;
  public MockModelShowValuation(StringBuilder log) {
    this.log = log;
  }
  @Override
  public <T> double getValuationGivenDate(String id, LocalDate date){
    log.append(merge(id,date.toString()));
    return 0.0;
  }

}
