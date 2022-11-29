package controller.commands;

import controller.StockPortfolioCommand;

import gui.HomeScreen;
import model.ModelInterface;

/**
 * Interacts with the model interface object to get composition of a specific portfolio and returns
 * to the user as string output.
 */
public class Composition implements StockPortfolioCommand {

  @Override
  public boolean process(ModelInterface model, HomeScreen view) {
    boolean flag = false;
//    do {
//      view.selectPortfolio();
//      String selectedId = scanner.next().trim();
//      flag = model.idIsPresent(selectedId);
//      if (!flag) {
//        view.printInvalidInputMessage();
//      } else {
//        String portfolio = model.getPortfolioById(selectedId);
//        String[] portfolioFields = portfolio.split(LINE_BREAKER);
//        view.printMessage(portfolioFields[0]);
//        view.printMessage(portfolioFields[1]);
//        view.printMessage("Shares in this Portfolio: ");
//        String[] shareRecords = portfolioFields[2].trim().substring(8).split("\\|");
//        if (shareRecords.length == 1 && shareRecords[0].trim().length() == 0) {
//          view.printMessage("No shares in this portfolio");
//        }
//        for (String share : shareRecords) {
//          view.printMessage(share);
//        }
//        view.printMessage(LINE_BREAKER);
//        break;
//      }
//    }
//    while (!flag);
    return flag;
  }

  @Override
  public void undo(ModelInterface model) {
    // Undo functionality from future use
  }
}