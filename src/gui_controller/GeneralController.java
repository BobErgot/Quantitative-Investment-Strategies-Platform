package gui_controller;

import gui.HomeScreen;
import java.time.LocalDate;
import model.FlexibleModelImplementation;
import model.ModelInterface;

public class GeneralController implements Features{
  private ModelInterface model;
  private HomeScreen view;
  public GeneralController(ModelInterface model){
    this.model = model;

  }
  public void setView(HomeScreen view) {
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
  public void uploadPortfolio(String path, boolean isRelativePath) {

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
