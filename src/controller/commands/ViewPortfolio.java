package controller.commands;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Pattern;

import controller.StockPortfolioCommand;
import model.ModelInterface;
import view.View;

public class ViewPortfolio implements StockPortfolioCommand {

  /**
   * Interacts with the model interface object to get all portfolio information and then returns
   * the same
   * to the user as string output.
   */
  @Override
  public void process(View view, Scanner scanner, ModelInterface model) {
    view.showViewPortfolioMenu(model.getPortfolio());
    view.showAdditionalPortfolioInformation();
    String choice = scanner.next();
    showViewPortfolioMenu(view, scanner, model, choice);
    view.showMainMenu();
  }

  @Override
  public void undo(ModelInterface model) {
  }

  private void showViewPortfolioMenu(View view, Scanner scanner,
                                     ModelInterface model, String choice){
    if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")) {
      boolean flag = false;
      do {
        view.selectPortfolio();
        String selectedId = scanner.next().trim();
        flag = showValuationOfPortfolio(selectedId, view, scanner, model);
        if(!flag){
          view.printInvalidInputMessage();
        }
      }
      while (!flag);
    } else if (choice.equalsIgnoreCase("n") || choice.equalsIgnoreCase("no")){
      view.showMainMenu();
      return;
    } else {
      view.printInvalidInputMessage();
      showViewPortfolioMenu(view, scanner, model, choice);
    }
  }

  /**
   * Returns the valuation of the portfolio which the user has entered and returns false if the
   * portfolio with the given id does not exist.
   * @param selectedId unique id of the portfolio for which the valuation is required on a
   *                   particular date
   * @return "true" if valuation exists else return "false"
   */
  private boolean showValuationOfPortfolio(String selectedId, View view, Scanner scanner,
                                          ModelInterface model) {
    if (!model.idIsPresent(selectedId)) {
      return false;
    }
    boolean invalidDate;
    LocalDate date;
    String stockDate;
    do {
      while (true) {
        try {
          view.askForDate();
          stockDate = scanner.next();
          invalidDate = !(Pattern.matches("\\d{4}-\\d{2}-\\d{2}", stockDate));
          date = LocalDate.parse(stockDate);
          break;
        } catch (DateTimeParseException dtp) {
          view.printInvalidDateError();
        }

      }
      invalidDate = invalidDate && date.isAfter(LocalDate.of(1949, 12, 31))
              && date.isBefore(LocalDate.now());
      if (invalidDate) {
        view.printInvalidInputMessage();
      }
    }
    while (invalidDate);
    view.showValuation(model.getValuationGivenDate(selectedId, date));
    return true;
  }
}