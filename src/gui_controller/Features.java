package gui_controller;

import java.time.LocalDate;

public interface Features {
  // Create portfolio tab
  boolean purchaseShare(String shareName, int numShares, LocalDate date);
  boolean createPortfolio(String portfolioName, PortfolioType pType);

  // Upload Tab
  boolean uploadPortfolio(String filePath);

  // View Tab
  boolean purchaseShare(String portfolioName, String shareName, int numShares, LocalDate date);
  void sellShare();
  double generateCostBasis(String id, LocalDate date);
  String generateComposition(String id);
  void generatePerformanceGraph();
  double getValuation(String id, LocalDate date);
  void viewPortfolio();
}
