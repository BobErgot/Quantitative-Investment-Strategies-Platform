package controller.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.Function;

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
    List<String> portfolioData = model.getPortfolio();
    if (portfolioData != null && model.getPortfolio().size() != 0) {
      view.showViewPortfolioMenu(model.getPortfolio());

      Stack<StockPortfolioCommand> commands = new Stack<>();
      Map<String, Function<Scanner, StockPortfolioCommand>> knownCommands = new HashMap<>();
      knownCommands.put("1", s -> new Valuation());
      knownCommands.put("2", s -> new CostBasis());
      knownCommands.put("3", s -> new ViewPortfolio());
//      this.out.println("4. Performance of portfolio over time");
//      this.out.println("5. Purchase a share and add to portfolio");
//      this.out.println("6. Sell a share from portfolio");
//      this.out.println("7. Performance of portfolio over time");

      view.showAdditionalPortfolioInformation();
      while (scanner.hasNext()) {
        StockPortfolioCommand command;
        String input = scanner.next();
        if (input.equalsIgnoreCase("back")) {
          view.showMainMenu();
          return;
        }
        Function<Scanner, StockPortfolioCommand> cmd = knownCommands.getOrDefault(input, null);
        if (cmd == null) {
          view.printInvalidInputMessage();
        } else {
          command = cmd.apply(scanner);
          commands.add(command);
          command.process(view, scanner, model);
        }
        view.showViewPortfolioMenu(model.getPortfolio());
        view.showAdditionalPortfolioInformation();
      }
    } else {
      view.alertNoPortfolioMessage();
      view.showMainMenu();
    }
  }

  @Override
  public void undo(ModelInterface model) {
  }
}