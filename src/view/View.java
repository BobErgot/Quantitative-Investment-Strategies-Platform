package view;

import java.util.List;

/**
 * This interface represents all the operations to be supported by a view implementation.
 */
public interface View {

  /**
   * Shows Main menu.
   */
  void showMainMenu();

  /**
   * Shows portfolio creation menu.
   */
  void showCreatePortfolioMenu(boolean canCreateShare);

  /**
   * Show menu if user wants to add shares with API.
   *
   * @param parameterNumber Which parameter needs to be displayed.
   */
  void showAddShareWithApiInputMenu(int parameterNumber);

  /**
   * Show list of portfolios in a given format.
   *
   * @param portfolioList list of portfolios to display.
   */
  void showViewPortfolioMenu(List<String> portfolioList);

  /**
   * Gives users options to perform various operations on given portfolio.
   */
  void showAdditionalPortfolioInformation();

  void showValuation(double valuation);

  void selectPortfolio();

  void printInvalidInputMessage();

  void showUploadPortfolioOptions();

  void askIfUserContinues();

  void askForPortfolioName();

  void notPresentError(String missing);

  void askForDate();

  void printCompanyStockUpdated();

  void developmentInProgress();

  void uploadPath();

  void enterPath();

  void printInvalidDateError();

  void printException(String message);
}