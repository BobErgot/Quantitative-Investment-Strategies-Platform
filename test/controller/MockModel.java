package controller;

import controller.MockFile;
import model.ModelImplementation;

class MockModel extends ModelImplementation {

  public MockModel() {
    super(new MockFile());
  }

}
