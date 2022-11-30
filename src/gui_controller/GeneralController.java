package gui_controller;

import static utility.Constants.FILE_SEPARATOR;

import gui.GUIView;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import model.FlexibleModelImplementation;
import model.ModelInterface;

public class GeneralController implements Features {

  private ModelInterface model;
  private GUIView view;

  public GeneralController(ModelInterface model) {
    this.model = model;

  }

  public void setView(GUIView view) {
    this.view = view;
    this.view.addFeatures(this);
    this.updatePortfolioList();
    this.view.showView();
  }

  private void updatePortfolioList() {
    List<String> portfolios = new FlexibleModelImplementation().getPortfolio();
    this.view.listAllPortfolios(portfolios.stream().map((inp) -> inp.split("\\|\\|")[2].trim())
        .collect(Collectors.toList()));
    this.view.listAllMutablePortfolios(
        portfolios.stream().filter(inp -> inp.split("\\|\\|").length >= 5)
            .map((inp) -> inp.split("\\|\\|")[2].trim()).collect(Collectors.toList()));
  }

  @Override
  public boolean createPortfolio(String portfolioName, PortfolioType pType) {
    if (!model.idIsPresent(portfolioName)) {
      if (pType == PortfolioType.Flexible) {
        this.model = new FlexibleModelImplementation();
      }
      this.model.createPortfolio(portfolioName, LocalDate.now());
      this.updatePortfolioList();
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
  public boolean purchaseShare(String shareName, int numShares, LocalDate date) {
    try {
      return model.addShareToModel(shareName, date, numShares, -1);
    } catch (IllegalArgumentException invalidTicker) {
      return false;
    }
  }

  @Override
  public double purchaseShare(String portfolioName, String shareName, int numShares,
      LocalDate date) {
    if (!this.model.checkTicker(shareName)) {
      return -1.0;
    }
    try {
      double boughtPrice = new FlexibleModelImplementation().appendPortfolio(portfolioName,
          shareName, numShares, date);
      return boughtPrice;
    } catch (IllegalArgumentException invalidTicker) {
      return -1.0;
    }
  }

  @Override
  public double sellShare(String portfolioName, String shareName, int numShares, LocalDate date) {
    if (!this.model.checkTicker(shareName)) {
      return -1.0;
    }
    double sellingPrice = new FlexibleModelImplementation().sellStocks(portfolioName, shareName,
        numShares, date);
    return sellingPrice;

  }

  @Override
  public double generateCostBasis(String id, LocalDate date) {
    return this.model.getCostBasis(id, date);
  }

  @Override
  public String generateComposition(String id) {
    System.out.println(id + this.model.getPortfolioById(id));
    return this.model.getPortfolioById(id);
  }

  @Override
  public void generatePerformanceGraph() {

  }

  @Override
  public double getValuation(String id, LocalDate date) {

    return this.model.getValuationGivenDate(id, date);
  }

  @Override
  public void viewPortfolio() {

  }
}
