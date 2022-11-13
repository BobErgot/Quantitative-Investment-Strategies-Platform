package controller.commands;

import java.time.LocalDate;
import java.util.Scanner;

import controller.StockPortfolioCommand;
import model.ModelInterface;
import view.View;

public class CreatePortfolio implements StockPortfolioCommand {

  /**
   * Asks the user for input like if he wants to add shares, once a share is added the user get
   * the option to create the portfolio. On receiving confirmation from the user the function
   * interacts with the model interface object to transmit the portfolio information to be created.
   */
  @Override
  public void process(View view, Scanner scanner, ModelInterface model) {
    boolean invalidInput;
    boolean portfolioCompleted;
    do {
      invalidInput = false;
      portfolioCompleted = false;
      boolean canCreateShare = model.canCreateShare();
      view.showCreatePortfolioMenu(canCreateShare);
      int choice = scanner.nextInt();
      switch (choice) {
        case 1:
          this.addShareWithApiInput(view,scanner, model);
          break;
        case 2:
          if (canCreateShare) {
            boolean invalidPortfolioName = false;
            do {
              invalidPortfolioName = false;
              view.askForPortfolioName();
              String portfolioName = scanner.next().trim();
              if (portfolioName.length() > 0 && !model.idIsPresent(portfolioName)) {
                model.createPortfolio(portfolioName, LocalDate.now());
                portfolioCompleted = true;
              } else {
                invalidPortfolioName = true;
              }
              if (invalidPortfolioName) {
                view.printInvalidInputMessage();
              }
            }
            while (invalidPortfolioName);
          } else {
            invalidInput = true;
          }
          break;
        case 3:
          portfolioCompleted = true;
          break;
        default:
          invalidInput = true;
          break;
      }
      if (invalidInput) {
        view.printInvalidInputMessage();
      }
    }
    while (invalidInput || !portfolioCompleted);
  }

  @Override
  public void undo(ModelInterface model) {
    return;
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
              && Character.isAlphabetic(companyName.charAt(0))
              && model.checkTicker(companyName);

      if (isValidCompany) {
        view.showAddShareWithApiInputMenu(1);
        int numShares = scanner.nextInt();
        if (numShares <= 0) {
          view.printInvalidInputMessage();
          isValidCompany = false;
        } else {
          try {
            boolean companyAddedWithoutChange = model.addShareToModel(companyName,
                    LocalDate.now(), numShares, -1);
            if (!companyAddedWithoutChange) {
              view.printCompanyStockUpdated();
            }
          } catch (IllegalArgumentException illegalArgumentException) {
            isValidCompany = false;
            view.developmentInProgress();
          }
        }

      } else {
        view.notPresentError("Company");
      }
    }
    while (!isValidCompany);
  }
}
