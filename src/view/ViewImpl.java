package view;

import static utility.Constants.FILE_SEPARATOR;

import java.io.PrintStream;
import java.util.List;

/**
 * The controller implementation that receives all its inputs from an InputStream object and
 * transmits all outputs to a PrintStream object. It also interacts with the model based on the
 * received input from the user.
 */
public class ViewImpl implements View {

  PrintStream out;

  /**
   * Construct a view implementation object that has the provided PrintStream object.
   *
   * @param out PrintStream object to transmit all the outputs to
   */
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
    if (canCreateShare) {
      this.out.println("2. Create Portfolio (Finalize current Portfolio)");
    }
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
    this.out.println("Valuation of Portfolio is:\t" + valuation);
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
  public void askForPortfolioName() {
    this.out.println("Please write down the unique name of portfolio");
  }

  @Override
  public void notPresentError(String missing) {
    this.out.println(missing + " is not present, please enter again!");
  }

  @Override
  public void askForDate() {
    this.out.println("Please enter date in yyyy-mm-dd format.");
  }

  @Override
  public void printCompanyStockUpdated() {
    this.out.println("This company's number of stocks have been updated!");
  }

  @Override
  public void developmentInProgress() {
    this.out.println("This company's data has not been added yet, come back again soon!");
  }

  @Override
  public void uploadPath() {
    this.out.println(
        "How do you want to upload this data? (Write your path sperated by " + FILE_SEPARATOR);
    this.out.println("1. Absolute Path");
    this.out.println("2. Relative Path");
    this.out.println("3. Go back");

  }

  @Override
  public void enterPath() {
    this.out.println("Enter path:");
  }

  @Override
  public void printInvalidDateError() {
    this.out.println("Invalid date format!");
  }

  @Override
  public void printException(String message) {
    this.out.println(message);
  }
}
