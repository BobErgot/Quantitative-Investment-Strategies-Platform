package gui_controller;

import gui.GUIView;
import gui.HomeScreen;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.zip.DataFormatException;

import model.FlexibleModelImplementation;
import model.ModelInterface;

import static utility.Constants.FILE_SEPARATOR;

public class GeneralController implements Features{
  private ModelInterface model;
  private GUIView view;
  public GeneralController(ModelInterface model){
    this.model = model;

  }
  public void setView(GUIView view) {
    this.view = view;
    this.view.addFeatures(this);
    this.view.showView();
  }

  @Override
  public boolean purchaseShare(String shareName, int numShares) {
    try {
      return model.addShareToModel(shareName, LocalDate.now(), numShares, -1);
    }
    catch (IllegalArgumentException invalidTicker){
      return false;
    }
  }

  @Override
  public boolean createPortfolio(String portfolioName, PortfolioType pType) {
    if (!model.idIsPresent(portfolioName)) {
      if (pType == PortfolioType.Flexible)
        this.model = new FlexibleModelImplementation();
      this.model.createPortfolio(portfolioName, LocalDate.now());
      return true;
    }
    return false;
  }

  @Override
  public boolean uploadPortfolio(String filePath) {
    boolean validPath = false;
    int idx = filePath.lastIndexOf(FILE_SEPARATOR);
    String folderName = filePath.substring(0, idx);
    String[] file = filePath.substring(idx + 1).split("\\.");
    idx = folderName.lastIndexOf(FILE_SEPARATOR);
    String root = filePath.substring(0, idx);
    String folder = filePath.substring(idx + 1);
    idx = folder.lastIndexOf(FILE_SEPARATOR);
    folder = folder.substring(0, idx);
    try {
      validPath = model.addPortfolioByUpload(root, folder, file[0], file[1]);
    } catch (DataFormatException | FileNotFoundException e) {
      validPath = false;
    }
    return validPath;
  }

  @Override
  public void purchaseShare(String portfolioName, String shareName, int numShares) {

  }

  @Override
  public void sellShare() {

  }

  @Override
  public void generateCostBasis() {

  }

  @Override
  public void generateComposition() {

  }

  @Override
  public void generatePerformanceGraph() {

  }

  @Override
  public void getValuation() {

  }

  @Override
  public void viewPortfolio() {

  }
}
