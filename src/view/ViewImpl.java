package view;

import java.io.PrintStream;
import java.util.List;

public class ViewImpl implements View {

  PrintStream out;

  public ViewImpl(PrintStream out) {
    this.out = out;
  }

  @Override
  public void showMainMenu() {
    this.out.println("Please select an option from 1-x from the main menu");

    this.out.println("Main Menu:");
    this.out.println("1. Create Portfolio");
    this.out.println("2. Upload Portfolio from given path.");
    this.out.println("3. View Portfolio");
    this.out.println("4. Exit");

  }

  @Override
  public void showCreatePortfolioMenu(boolean canCreateShare) {
    this.out.println("Please select an option:");
    this.out.println("1. Add Shares");
    if(canCreateShare)
      this.out.println("2. Create Portfolio (Finalize current Portfolio)");
    this.out.println("3. Go back.");
  }

  @Override
  public void showAddShareWithApiInputMenu(int parameterNumber) throws IllegalArgumentException {
    switch (parameterNumber) {
      case 0:
        this.out.println("Enter Company Name:");
        break;
      case 1:
        this.out.println("Enter Number of shares:");
        break;
      default:
        throw new IllegalArgumentException("Incorrect parameter Number");
    }
  }

  @Override
  public void showViewPortfolioMenu(List<String> portfolioList) {
    this.out.println("Portfolios available:");
    for (String portfolio : portfolioList) {
      this.out.println(portfolio);
    }
  }

  @Override
  public void showAdditionalPortfolioInformation() {
    this.out.println("Do you want to see the valuation of this portfolio? (1 for yes)");
  }

  @Override
  public void showValuation(double valuation) {
    this.out.println("Valuation of Portfolio is:\t"+valuation);
  }

  @Override
  public void selectPortfolio() {
    this.out.println("Select an ID from above list");
  }

  @Override
  public void printInvalidInputMessage() {
    this.out.println("Invalid Input!");
  }

  @Override
  public void showUploadPortfolioOptions() {
    this.out.println("Write path to portfolio:");
  }

  @Override
  public void askIfUserContinues() {
    this.out.println("Do you want to continue?");
  }

  @Override
  public void askForPortfolioName() {
    this.out.println("Please write down the unique name of portfolio");
  }

  @Override
  public void notPresentError(String missing) {
    this.out.println(missing+" is not present, please enter again!");
  }

  @Override
  public void askForDate() {
    this.out.println("Please enter date in yyyy-mm-dd format.");
  }

}
