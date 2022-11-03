package controller.MockModels;

import static controller.MockModels.MockModelUtil.merge;

import model.ModelImplementation;

public class MockModelUpload extends ModelImplementation {

  StringBuilder log;

  public MockModelUpload(StringBuilder log) {
    this.log = log;
  }

  @Override
  public boolean addPortfolioByUpload(String path, String folderName, String fileName,
      String extension) {
    this.log.append(merge(path, folderName, fileName, extension));
    return true;
  }

}


