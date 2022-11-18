package controller;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.Function;

import controller.commands.CreatePortfolio;
import controller.commands.UploadPortfolio;
import controller.commands.ViewPortfolio;
import model.ModelInterface;
import view.View;
import view.ViewImpl;

/**
 * The controller implementation that receives all its inputs from an InputStream object and
 * transmits all outputs to a PrintStream object. It also interacts with the model based on the
 * received input from the user.
 */
public class ControllerImpl implements Controller {

  private final View viewObject;
  private final ModelInterface modelObject;
  private final Scanner scanner;

  /**
   * Construct a controller implementation object that has the provided InputStream, PrintStream and
   * ModelInterface object.
   *
   * @param in    InputStream object to receive all the inputs
   * @param out   PrintStream object to transmit all the outputs to
   * @param model ModelInterface object to communicate and receive data from model implementation
   */
  public ControllerImpl(InputStream in, PrintStream out, ModelInterface model) {
    this.scanner = new Scanner(in);
    this.viewObject = new ViewImpl(out);
    this.modelObject = model;
  }

  @Override
  public void start() {
    Stack<StockPortfolioCommand> commands = new Stack<>();
    Map<String, Function<Scanner, StockPortfolioCommand>> knownCommands = new HashMap<>();
    knownCommands.put("1", s -> new CreatePortfolio());
    knownCommands.put("2", s -> new UploadPortfolio());
    knownCommands.put("3", s -> new ViewPortfolio());

    viewObject.showMainMenu();
    while (this.scanner.hasNext()) {
      StockPortfolioCommand command;
      String input = this.scanner.next();
      if (input.equalsIgnoreCase("quit")
              || input.equalsIgnoreCase("exit")) {
        return;
      }
      Function<Scanner, StockPortfolioCommand> cmd = knownCommands.getOrDefault(input,
              null);
      if (cmd == null) {
        viewObject.printInvalidInputMessage();
      } else {
        command = cmd.apply(this.scanner);
        commands.add(command);
        command.process(this.viewObject, this.scanner, this.modelObject);
      }
    }
  }
}