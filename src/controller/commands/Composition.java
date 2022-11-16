package controller.commands;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Pattern;

import controller.StockPortfolioCommand;
import model.ModelInterface;
import view.View;

import static utility.Constants.LINE_BREAKER;

public class Composition implements StockPortfolioCommand {

  /**
   * Interacts with the model interface object to get valuation of a specific portfolio and returns
   * to the user as string output.
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
        String portfolio = model.getPortfolioById(selectedId);
        String[] portfolioFields = portfolio.split(LINE_BREAKER);
        view.printMessage(portfolioFields[0]);
        view.printMessage(portfolioFields[1]);
        view.printMessage(LINE_BREAKER + "Shares in this Portfolio: ");
        String[] shareRecords = portfolioFields[2].trim().substring(8).split("\\|");
        for (String share : shareRecords)
          view.printMessage(share);
        view.printMessage(LINE_BREAKER);
        break;
      }
    }
    while (!flag);
  }

  @Override
  public void undo(ModelInterface model) {
  }
}