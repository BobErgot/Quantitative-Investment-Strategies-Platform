package gui_controller;

public interface Features {
  // Create portfolio tab
  boolean purchaseShare(String shareName, int numShares);
  boolean createPortfolio(String portfolioName, PortfolioType pType);

  // Upload Tab
  void uploadPortfolio(String path, boolean isRelativePath);

  // View Tab
  void purchaseShare(String portfolioName, String shareName, int numShares);
  void sellShare();
  void generateCostBasis();
  void generateComposition();
  void generatePerformanceGraph();
  void getValuation();
  void viewPortfolio();
}
