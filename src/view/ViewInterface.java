package view;

import java.util.List;

public interface ViewInterface {

  /**
   * Shows Main menu.
   */
  void showMainMenu();

  /**
   * Shows portfolio creation menu.
   */
  void showCreatePortfolioMenu();

  /**
   * Shows menu to add shares & list different options to add shares.
   */
  void showAddSharesMenu();

  /**
   * Show menu if user wants to add shares with API.
   * @param parameterNumber Which parameter needs to be displayed.
   */
  void showAddShareWithApiInputMenu(int parameterNumber);
  /**
   * Show menu if user wants to add shares manually.
   * @param parameterNumber Which parameter needs to be displayed.
   */
  void showAddManualShareInputMenu(int parameterNumber);

  /**
   * Show list of portfolios in a given format.
   * @param portfolioList list of portfolios to display.
   */
  void showViewPortfolioMenu(List<String> portfolioList);

  /**
   * Gives users options to perform various operations on given portfolio.
   */
  void showAdditionalPortfolioInformation();
}
