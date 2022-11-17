package controller.commands;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import controller.StockPortfolioCommand;
import model.FlexibleModelImplementation;
import model.ModelInterface;
import view.View;

/**
 * Asks the user for input of fields namely portfolio, share to be bought and the number of
 * shares to buy. Based on the information collected, it interacts with model interface to get
 * the value of the shares and store it in the specified portfolio.
 */
public class PurchaseShare implements StockPortfolioCommand {

  @Override
  public void process(View view, Scanner scanner, ModelInterface model) {
    boolean flag;
    boolean fixed = false;
    do {
      view.selectPortfolio();
      String selectedId = scanner.next().trim();
      flag = model.idIsPresent(selectedId);
      if (!flag) {
        view.printInvalidInputMessage();
        continue;
      }
      if (flag) {
        fixed = checkIfPortfolioMutable(selectedId, model);
      }
      if (!fixed) {
        flag = false;
        view.alertFixedPortfolio();
      } else {
        callPortfolio(view, scanner, model, selectedId);
      }
    }
    while (!flag);
  }

  private void callPortfolio(View view, Scanner scanner, ModelInterface model, String portfolioId) {
    model = new FlexibleModelImplementation();
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
              double soldPrice = model.appendPortfolio(portfolioId, companyName, shares);
              view.showAmountPaid(soldPrice);
              view.printCompanyStockUpdated();
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
    }
    while (!isValidCompany);
  }

  private boolean checkIfPortfolioMutable(String portfolioId, ModelInterface model) {
    model = new FlexibleModelImplementation();
    List<String> portfolioList = model.getPortfolio();
    if (portfolioList.size() == 0) {
      return false;
    } else {
      for (String portfolio : portfolioList) {
        String[] portfolioRecords = portfolio.split("\\|\\|");
        if (portfolioId.equals(portfolioRecords[2].trim())) {
          if (portfolioRecords.length == 5) {
            return true;
          }
        }
      }
    }
    return false;
  }

  @Override
  public void undo(ModelInterface model) {
  }
}
