package controller.commands;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Pattern;

import controller.StockPortfolioCommand;
import model.ModelInterface;
import view.View;

/**
 * Interacts with the model interface object to get valuation of a specific portfolio on a
 * specific date entered by the user and returns the valuation amount to the user as string output.
 */
public class Valuation implements StockPortfolioCommand {

  @Override
  public void process(View view, Scanner scanner, ModelInterface model) {
    boolean flag;
    do {
      view.selectPortfolio();
      String selectedId = scanner.next().trim();
      flag = showValuationOfPortfolio(selectedId, view, scanner, model);
      if (!flag) {
        view.printInvalidInputMessage();
      } else {
        break;
      }
    }
    while (!flag);
  }

  @Override
  public void undo(ModelInterface model) {
    // Undo functionality from future use
  }

  /**
   * Returns the valuation of the portfolio which the user has entered and returns false if the
   * portfolio with the given id does not exist.
   *
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