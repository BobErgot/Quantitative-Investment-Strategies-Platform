package controller.commands;

import static utility.Constants.LINE_BREAKER;

import controller.StockPortfolioCommand;
import java.util.Scanner;
import model.ModelInterface;
import view.View;

/**
 * Interacts with the model interface object to get composition of a specific portfolio and returns
 * to the user as string output.
 */
public class Composition implements StockPortfolioCommand {

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
        String portfolio = model.getPortfolioById(selectedId);
        String[] portfolioFields = portfolio.split(LINE_BREAKER);
        view.printMessage(portfolioFields[0]);
        view.printMessage(portfolioFields[1]);
        view.printMessage("Shares in this Portfolio: ");
        String[] shareRecords = portfolioFields[2].trim().substring(8).split("\\|");
        if (shareRecords.length == 1 && shareRecords[0].trim().length() == 0) {
          view.printMessage("No shares in this portfolio");
        }
        for (String share : shareRecords) {
          view.printMessage(share);
        }
        view.printMessage(LINE_BREAKER);
        break;
      }
    }
    while (!flag);
  }

  @Override
  public void undo(ModelInterface model) {
    // Undo functionality from future use
  }
}