package view;

import java.util.List;

public class ViewImpl implements View {

  @Override
  public void showMainMenu() {
    System.out.println("Please select an option from 1-x from the main menu");

    System.out.println("Main Menu:");
    System.out.println("1. Create Portfolio");
    System.out.println("2. Upload Portfolio from given path.");
    System.out.println("3. View Portfolio");
  }

  @Override
  public void showCreatePortfolioMenu() {

  }

  @Override
  public void showAddSharesMenu() {

  }

  @Override
  public void showAddShareWithApiInputMenu(int parameterNumber) {

  }

  @Override
  public void showAddManualShareInputMenu(int parameterNumber) {

  }

  @Override
  public void showViewPortfolioMenu(List<String> portfolioList) {
    System.out.println("Portfolios available:");
    for(String portfolio: portfolioList){
      System.out.println(portfolio);
    }
  }

  @Override
  public void showAdditionalPortfolioInformation() {
    System.out.println("Do you want to see the valuation of this portfolio?");
  }
}
