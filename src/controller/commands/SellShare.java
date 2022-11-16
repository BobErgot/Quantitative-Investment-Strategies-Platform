package controller.commands;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import controller.StockPortfolioCommand;
import model.FlexibleModelImplementation;
import model.ModelInterface;
import view.View;

import static utility.Constants.LINE_BREAKER;

public class SellShare implements StockPortfolioCommand {

  /**
   * Asks the user for input like if he wants to add shares, once a share is added the user get
   * the option to create the portfolio. On receiving confirmation from the user the function
   * interacts with the model interface object to transmit the portfolio information to be created.
   */
  @Override
  public void process(View view, Scanner scanner, ModelInterface model) {
    boolean flag;
    do {
      view.selectPortfolio();
      String selectedId = scanner.next().trim();
      flag = model.idIsPresent(selectedId);
      if (!flag) {
        view.printInvalidInputMessage();
      } else {
        callPortfolio(view, scanner, model, selectedId);
      }
    } while (!flag);
  }

  private void callPortfolio(View view, Scanner scanner, ModelInterface model, String portfolioId) {
    boolean isValidCompany;
    do {
      view.showAddShareWithApiInputMenu(0);
      String companyName = scanner.next().trim();

      isValidCompany = companyName.length() > 0 && companyName.length() <= 10
              && Character.isAlphabetic(companyName.charAt(0)) && model.checkTicker(companyName);

      if (isValidCompany) {
        view.showAddShareWithApiInputMenu(1);
        String numShares = scanner.next();
        try {
          int shares = Integer.parseInt(numShares);
          if (shares <= 0) {
            view.printInvalidInputMessage();
            isValidCompany = false;
          } else {
            try {
              double soldPrice = model.sellStocks(portfolioId, companyName, shares);
              view.showSoldValuation(soldPrice);
              view.printCompanyStockUpdated();
              view.showAdditionalPortfolioInformation();
            } catch (NoSuchElementException noSuchElementException) {
              isValidCompany = false;
              view.alertStockInvalid();
            } catch (IllegalArgumentException illegalArgumentException) {
              isValidCompany = false;
              view.alertShareNumberInvalid();
            }
          }
        } catch (NumberFormatException exception) {
          view.printInvalidInputMessage();
          isValidCompany = false;
        }
      } else {
        view.notPresentError("Company");
      }
    } while (!isValidCompany);
  }

  @Override
  public void undo(ModelInterface model) {
  }
}
