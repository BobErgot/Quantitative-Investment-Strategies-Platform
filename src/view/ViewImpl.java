package view;

import static utility.Constants.FILE_SEPARATOR;
import static utility.Constants.LINE_BREAKER;

import java.io.PrintStream;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Formatter;
import java.util.List;
import model.Periodicity;

/**
 * The controller implementation that receives all its inputs from an InputStream object and
 * transmits all outputs to a PrintStream object. It also interacts with the model based on the
 * received input from the user.
 */
public class ViewImpl implements View {

  private PrintStream out;

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
    this.out.append(LINE_BREAKER + "* Home Menu:");
    this.out.append(LINE_BREAKER + "1. Create Portfolio");
    this.out.append(LINE_BREAKER + "2. Upload Portfolio from given path");
    this.out.append(LINE_BREAKER + "3. View Portfolio");
    this.out.append(LINE_BREAKER + "Type 'quit' or 'exit' to close the program");
    this.out.append(LINE_BREAKER + "Please select an option from 1-x from above: ");
  }

  @Override
  public void showCreatePortfolioMenu(boolean canCreateShare) {
    this.out.append(LINE_BREAKER + "* Create Portfolio Menu:");
    this.out.append(LINE_BREAKER + "1. Add Shares");
    if (canCreateShare) {
      this.out.append(LINE_BREAKER + "2. Create Portfolio (Finalize current Portfolio)");
    }
    this.out.append(LINE_BREAKER + "Type 'back' to return to Home Menu");
    this.out.append(LINE_BREAKER + "Please select an option from 1-x from above: ");
  }

  @Override
  public void showAddShareWithApiInputMenu(int parameterNumber) throws IllegalArgumentException {
    switch (parameterNumber) {
      case 0:
        this.out.append(LINE_BREAKER + "Enter Company Name:");
        break;
      case 1:
        this.out.append(LINE_BREAKER + "Enter Number of shares:");
        break;
      default:
        throw new IllegalArgumentException("Incorrect parameter Number");
    }
  }

  @Override
  public void showViewPortfolioMenu(List<String> portfolioList) {
    this.out.append(LINE_BREAKER + "Portfolios available:");
    this.out.append(LINE_BREAKER + "--------------------------------------------------------------"
        + "----------------------");
    String portfolioHeaderString = String.format("||%-18s||%-40s||%-18s||", "Serial Number",
        "Portfolio Name", "Creation Date");
    this.out.append(LINE_BREAKER + portfolioHeaderString);
    this.out.append(LINE_BREAKER + "||------------------||----------------------------------------"
        + "||------------------||");
    for (int i = 0; i < portfolioList.size() - 1; i++) {
      this.out.append(LINE_BREAKER + portfolioList.get(i));
      this.out.append(
          LINE_BREAKER + "||------------------||----------------------------------------"
              + "||------------------||");
    }
    this.out.append(LINE_BREAKER + portfolioList.get(portfolioList.size() - 1));
    this.out.append(LINE_BREAKER + "--------------------------------------------------------------"
        + "----------------------");
    this.out.append(LINE_BREAKER);
  }

  @Override
  public void alertNoPortfolioMessage() {
    this.out.append(
        LINE_BREAKER + "ALERT: There are no portfolios added yet. Please create portfolios to view."
            + LINE_BREAKER);
  }

  @Override
  public void showAdditionalPortfolioInformation() {
    this.out.append(LINE_BREAKER + "* View Portfolio Menu:");
    this.out.append(LINE_BREAKER + "1. View composition of a particular portfolio");
    this.out.append(LINE_BREAKER + "2. Valuation of Portfolio on a specific date");
    this.out.append(LINE_BREAKER + "3. Cost basis of a portfolio till a specific date");
    this.out.append(LINE_BREAKER + "4. Purchase a share and add to portfolio");
    this.out.append(LINE_BREAKER + "5. Sell a share from portfolio");
    this.out.append(LINE_BREAKER + "6. Performance of portfolio over time");
    this.out.append(LINE_BREAKER + "Type 'back' to return to Home Menu");
    this.out.append(LINE_BREAKER + "Please select an option from 1-x from above: ");
  }

  @Override
  public void showValuation(double valuation) {
    this.out.append(LINE_BREAKER + "Valuation of Portfolio is:\t$" + valuation + LINE_BREAKER);
  }

  @Override
  public void showCostBasis(double valuation) {
    this.out.append(LINE_BREAKER + "Cost Basis of Portfolio is:\t$" + valuation + LINE_BREAKER);
  }

  @Override
  public void selectPortfolio() {
    this.out.append(LINE_BREAKER + "Select a 'Portfolio Name' from above list");
  }

  @Override
  public void printInvalidInputMessage() {
    this.out.append(LINE_BREAKER + "Invalid Input!");
  }

  @Override
  public void showUploadPortfolioOptions() {
    this.out.append(LINE_BREAKER + "Write path to portfolio:");
  }

  @Override
  public void askForPortfolioName() {
    this.out.append(LINE_BREAKER + "Please write down the unique name of portfolio");
  }

  @Override
  public void notPresentError(String missing) {
    this.out.append(missing + " is not present, please enter again!");
  }

  @Override
  public void askForDate() {
    this.out.append(LINE_BREAKER + "Please enter date in yyyy-mm-dd format.");
  }

  @Override
  public void printCompanyStockUpdated() {
    this.out.append(
        LINE_BREAKER + "This company's number of stocks have been updated!" + LINE_BREAKER);
  }

  @Override
  public void developmentInProgress() {
    this.out.append(
        LINE_BREAKER + "This company's data has not been added yet, come back again soon!");
  }

  @Override
  public void uploadPath() {
    this.out.append(
        LINE_BREAKER + "How do you want to upload the data? (Write your path seperated by "
            + FILE_SEPARATOR);
    this.out.append(LINE_BREAKER + "1. Absolute Path");
    this.out.append(LINE_BREAKER + "2. Relative Path");
    this.out.append(LINE_BREAKER + "Type 'back' to return to Home Menu");
  }

  @Override
  public void enterPath() {
    this.out.append(LINE_BREAKER + "Enter path:");
  }

  @Override
  public void printInvalidDateError() {
    this.out.append(LINE_BREAKER + "Invalid date format!");
  }

  @Override
  public void printException(String message) {
    this.out.append(message);
  }

  @Override
  public void askPortfolioType() {
    this.out.append(LINE_BREAKER + "Which type of portfolio do you want to create?");
    this.out.append(LINE_BREAKER + "1. Fixed Portfolio");
    this.out.append(LINE_BREAKER + "2. Flexible Portfolio");
  }

  @Override
  public void alertStockInvalid() {
    this.out.append(LINE_BREAKER + "ALERT: Entered stock ticker doesn't exist in this portfolio.");
  }

  @Override
  public void alertShareNumberInvalid() {
    this.out.append(LINE_BREAKER + "ALERT: Entered share number is invalid.");
  }

  @Override
  public void showSoldValuation(double valuation) {
    this.out.append(LINE_BREAKER + "Earning from selling of Portfolio is:\t$" + valuation);
  }

  @Override
  public void showAmountPaid(double valuation) {
    this.out.append(LINE_BREAKER + "Paid amount from buying of Portfolio is:\t$" + valuation);
  }

  @Override
  public void printMessage(String message) {
    this.out.append(message);
  }

  public void askForEnum(Class<Periodicity> e) {
    this.out.append(LINE_BREAKER + "Please type any of the following values to group:");
    for (Enum enumValue : EnumSet.allOf(Periodicity.class)) {
      this.out.append(enumValue.toString());
    }
  }

  @Override
  public void printStars(LocalDate date, Periodicity periodicity, Double value, int scale) {
    int numStars = (int) Math.ceil(value / Math.pow(10, scale));
    if (periodicity == Periodicity.DAY) {
      this.out.append(LINE_BREAKER + date + ":\t");
    } else if (periodicity == Periodicity.MONTH) {
      String day = date.getMonth() + "-" + date.getYear();
      Formatter formatter = new Formatter();
      formatter.format("%-20s :", day);
      this.out.append(LINE_BREAKER + formatter);
    } else {
      String day = String.valueOf(date.getYear());
      Formatter formatter = new Formatter();
      formatter.format("%-20s :", day);
      this.out.append(LINE_BREAKER + formatter);
    }
    for (int i = 0; i <= numStars; i++) {
      this.out.append("*");
    }
    this.out.append(LINE_BREAKER);
  }

  @Override
  public void alertFixedPortfolio() {
    this.out.append(LINE_BREAKER + "This is a fixed portfolio. Cannot sell or add shares to this.");
  }
}