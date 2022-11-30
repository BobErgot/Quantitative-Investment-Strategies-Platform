package gui_controller;

import java.time.LocalDate;

public interface Features {
  // Create portfolio tab
  boolean purchaseShare(String shareName, int numShares);
  boolean createPortfolio(String portfolioName, PortfolioType pType);

  // Upload Tab
  boolean uploadPortfolio(String filePath);

  // View Tab
  void purchaseShare(String portfolioName, String shareName, int numShares);
  void sellShare();
  void generateCostBasis();
  String generateComposition(String id);
  void generatePerformanceGraph();
  double getValuation(String id, LocalDate date);
  void viewPortfolio();
}
