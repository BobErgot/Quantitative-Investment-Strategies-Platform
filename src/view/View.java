package view;

import java.util.List;

/**
 * This interface represents all the operations to be supported by a view implementation.
 */
public interface View {

  /**
   * Shows Main.Main menu.
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

  /**
   * Shows valuation to the user.
   *
   * @param valuation given valuation to show.
   */
  void showValuation(double valuation);

  /**
   * Asks user for id to select portfolio.
   */
  void selectPortfolio();

  /**
   * Prints a general invalid input message.
   */
  void printInvalidInputMessage();

  /**
   * Asks for path to portfolio from the user.
   */
  void showUploadPortfolioOptions();

  /**
   * Ask for portfolio name from the user.
   */
  void askForPortfolioName();

  /**
   * Generic message asking user to re-enter something missing.
   *
   * @param missing missing element.
   */
  void notPresentError(String missing);

  /**
   * Asks user for date.
   */
  void askForDate();

  /**
   * Notifies if stock has been updated or not.
   */
  void printCompanyStockUpdated();

  /**
   * This function will let users know about Future developments given constraints in api.
   */
  void developmentInProgress();

  /**
   * Show user option to select portfolio by absolute path or relative path.
   */
  void uploadPath();

  /**
   * Asks user to enter path.
   */
  void enterPath();

  /**
   * Lets user know about invalid date.
   */
  void printInvalidDateError();

  /**
   * Prints a given exception to the user.
   *
   * @param message exception to print.
   */
  void printException(String message);

  /**
   * Prints no portfolio alert to the user.
   *
   */
  void alertNoPortfolioMessage();

  /**
   * Shows cost basis of a portfolio to the user.
   *
   * @param costBasis given cost basis valuation to show.
   */
  void showCostBasis(double costBasis);

  /**
   * Asks the user about their choice of portfolio.
   *
   */
  void askPortfolioType();
}