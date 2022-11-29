package controller.commands;

import java.time.LocalDate;
import java.util.Scanner;

import controller.StockPortfolioCommand;
import gui.HomeScreen;
import model.ModelInterface;
import view.View;

/**
 * Asks the user for input like if he wants to add shares, once a share is added the user get the
 * option to create the portfolio. On receiving confirmation from the user the function interacts
 * with the model interface object to transmit the portfolio information to be created.
 */
public class CreatePortfolio implements StockPortfolioCommand {

  @Override
  public boolean process(ModelInterface model, HomeScreen view) {
//    boolean invalidInput;
//    boolean portfolioCompleted;
//    invalidInput = false;
//    do {
//      view.askPortfolioType();
//      String choice = scanner.next();
//      if (choice.equals("1")) {
//        invalidInput = true;
//        break;
//      } else if (choice.equals("2")) {
//        model = new FlexibleModelImplementation();
//        invalidInput = true;
//        break;
//      } else {
//        view.printInvalidInputMessage();
//      }
//    }
//    while (!invalidInput);
//    do {
//      invalidInput = false;
//      portfolioCompleted = false;
//      boolean canCreateShare = model.canCreateShare();
//      view.showCreatePortfolioMenu(canCreateShare);
//      String choice = scanner.next();
//      switch (choice) {
//        case "1":
//          this.addShareWithApiInput(view, scanner, model);
//          break;
//        case "2":
//          if (canCreateShare) {
//            boolean invalidPortfolioName = false;
//            do {
//              invalidPortfolioName = false;
//              view.askForPortfolioName();
//              String portfolioName = scanner.next().trim();
//              if (portfolioName.length() > 0 && !model.idIsPresent(portfolioName)) {
//                model.createPortfolio(portfolioName, LocalDate.now());
//                portfolioCompleted = true;
//                view.showMainMenu();
//                break;
//              } else {
//                invalidPortfolioName = true;
//              }
//              if (invalidPortfolioName) {
//                view.printInvalidInputMessage();
//              }
//            }
//            while (invalidPortfolioName);
//          } else {
//            invalidInput = true;
//          }
//          break;
//        case "back":
//          portfolioCompleted = true;
//          view.showMainMenu();
//          break;
//        default:
//          invalidInput = true;
//          break;
//      }
//      if (invalidInput) {
//        view.printInvalidInputMessage();
//      }
//    }
//    while (invalidInput || !portfolioCompleted);
    return false;
  }

  @Override
  public void undo(ModelInterface model) {
    // Undo functionality from future use
  }

  /**
   * Receives share information from the user and passes it to the model interface object.
   */
  private void addShareWithApiInput(View view, Scanner scanner, ModelInterface model) {
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
              boolean companyAddedWithoutChange = model.addShareToModel(companyName,
                      LocalDate.now(), shares, -1);
              if (!companyAddedWithoutChange) {
                view.printCompanyStockUpdated();
              }
            } catch (IllegalArgumentException illegalArgumentException) {
              isValidCompany = false;
              view.developmentInProgress();
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
}
