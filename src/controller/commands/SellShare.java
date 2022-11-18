package controller.commands;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

import controller.StockPortfolioCommand;
import model.FlexibleModelImplementation;
import model.ModelInterface;
import view.View;

/**
 * Asks the user for input of fields namely portfolio, share to be sold from that portfolio and
 * the number of shares to be sold. Based on the information collected, it interacts with model
 * interface to calculate the total selling value of shares based on the present stock share price.
 */
public class SellShare implements StockPortfolioCommand {

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
              if (date.isAfter(LocalDate.of(1949, 12, 31))
                      && (date.isBefore(LocalDate.now()) || date.isEqual(LocalDate.now()))) {
                invalidDate = false;
              } else {
                invalidDate = true;
              }
              if (invalidDate) {
                view.printInvalidInputMessage();
              }
            }
            while (invalidDate);
            try {
              double soldPrice = model.sellStocks(portfolioId, companyName, shares, date);
              view.showSoldValuation(soldPrice);
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
    // Undo functionality from future use
  }
}
