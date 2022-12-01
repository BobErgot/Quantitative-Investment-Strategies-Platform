package gui_controller;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

import gui.GUIView;
import model.FlexibleModelImplementation;
import model.ModelInterface;
import model.Periodicity;

import static utility.Constants.FILE_SEPARATOR;

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
    this.view.listAllPortfolios(getPortfolios(PortfolioType.ALL));
    this.view.listAllMutablePortfolios(getPortfolios(PortfolioType.FLEXIBLE));
  }

  @Override
  public boolean checkPortfolioNameExists(String portfolioName) {
    return model.idIsPresent(portfolioName);
  }

  @Override
  public boolean checkTickerExists(String ticker) {
    return this.model.checkTicker(ticker);
  }

  @Override
  public List<String> getShareTickerInPortfolio (String portfolioName){
    return this.model.getShareTickerInPortfolio(portfolioName);
  }

  private List<String> getPortfolios(PortfolioType pType) {
    List<String> portfolios = new FlexibleModelImplementation().getPortfolio();
    if(pType == PortfolioType.ALL){
      return portfolios.stream()
              .map((inp) -> inp.split("\\|\\|")[2].trim())
              .collect(Collectors.toList());
    } else if (pType == PortfolioType.FIXED) {
      // Add logic for fixed if required
    } else if (pType == PortfolioType.FLEXIBLE){
      return portfolios.stream()
              .filter(inp -> inp.split("\\|\\|").length >= 5)
              .map((inp) -> inp.split("\\|\\|")[2].trim())
              .collect(Collectors.toList());
    }
    return null;
  }

  @Override
  public boolean createPortfolio(String portfolioName, PortfolioType pType) {
    if (!checkPortfolioNameExists(portfolioName)) {
      if (pType == PortfolioType.FLEXIBLE) {
        this.model = new FlexibleModelImplementation();
      }
      this.model.createPortfolio(portfolioName, LocalDate.now());
      this.updatePortfolioList();
      return true;
    }
    return false;
  }

  @Override
  public int uploadPortfolio(String filePath) {
    boolean validPath = false;
    int errorCode = 0;
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
    } catch (DataFormatException dataFormatException) {
      errorCode = 1;
    } catch (FileNotFoundException fileNotFoundException) {
      errorCode = 2;
    }
    return validPath ? 0 : errorCode;
  }

  @Override
  public boolean purchaseShare(String shareName, int numShares, LocalDate date) {
    if (numShares <= 0) return false;
    try {
      return model.addShareToModel(shareName, date, numShares, -1);
    } catch (IllegalArgumentException invalidTicker) {
      return false;
    }
  }

  @Override
  public double purchaseShare(String portfolioName, String shareName, int numShares,
                              LocalDate date) {
    if (!checkTickerExists(shareName)) {
      return -1.0;
    }
    try {
      return new FlexibleModelImplementation().appendPortfolio(portfolioName, shareName,
              numShares, date);
    } catch (IllegalArgumentException invalidTicker) {
      return -1.0;
    }
  }

  @Override
  public double sellShare(String portfolioName, String shareName, int numShares, LocalDate date) {
    if (!checkTickerExists(shareName) || numShares <= 0.0) {
      return -1.0;
    }
    return new FlexibleModelImplementation().sellStocks(portfolioName, shareName, numShares, date);
  }

  @Override
  public double generateCostBasis(String id, LocalDate date) {
    return this.model.getCostBasis(id, date);
  }

  @Override
  public String generateComposition(String id) {
    return this.model.getPortfolioById(id);
  }

  @Override
  public List<Double> generatePerformanceGraph(String portfolioName, LocalDate from,
                                               LocalDate to, Periodicity group) {
    return this.model.getPortfolioPerformance(portfolioName, from, to, group);
  }

  @Override
  public double getValuation(String id, LocalDate date) {
    return this.model.getValuationGivenDate(id, date);
  }

  @Override
  public boolean createStrategy (String portfolioName, String investmentAmount, LocalDate date,
                                 ArrayList<String> shares, ArrayList<Integer> weightage) {
    this.model = new FlexibleModelImplementation();
    return this.model.createStrategy(portfolioName, investmentAmount, date, shares, weightage, 0);
  }
}
