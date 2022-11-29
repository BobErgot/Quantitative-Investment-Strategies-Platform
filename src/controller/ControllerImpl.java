package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import gui.HomeScreen;
import model.ModelInterface;
import view.View;
import view.ViewImpl;

import static java.lang.System.in;
import static java.lang.System.out;

/**
 * The controller implementation that receives all its inputs from an InputStream object and
 * transmits all outputs to a PrintStream object. It also interacts with the model based on the
 * received input from the user.
 */
public class ControllerImpl implements Controller, ActionListener {

  private final View viewObject;
  private final ModelInterface modelObject;
  private final Scanner scanner;
  private final HomeScreen view;

  /**
   * Construct a controller implementation object that has the provided InputStream, PrintStream and
   * ModelInterface object.
   *
   * @param model ModelInterface object to communicate and receive data from model
   *              implementation
   * @param view ModelInterface object to communicate and send data to view implementation
   */
  public ControllerImpl(ModelInterface model, HomeScreen view) {
    this.scanner = new Scanner(in);
    this.viewObject = new ViewImpl(out);
    this.view = view;
    this.modelObject = model;
  }

  @Override
  public void start() {
    this.view.showView(this);
//    Stack<StockPortfolioCommand> commands = new Stack<>();
//    Map<String, Function<Scanner, StockPortfolioCommand>> knownCommands = new HashMap<>();
//    knownCommands.put("1", s -> new CreatePortfolio());
//    knownCommands.put("2", s -> new UploadPortfolio());
//    knownCommands.put("3", s -> new ViewPortfolio());
//
//    viewObject.showMainMenu();
//    while (this.scanner.hasNext()) {
//      StockPortfolioCommand command;
//      String input = this.scanner.next();
//      if (input.equalsIgnoreCase("quit")
//              || input.equalsIgnoreCase("exit")) {
//        return;
//      }
//      Function<Scanner, StockPortfolioCommand> cmd = knownCommands.getOrDefault(input,
//              null);
//      if (cmd == null) {
//        viewObject.printInvalidInputMessage();
//      } else {
//        command = cmd.apply(this.scanner);
//        commands.add(command);
//        command.process(this.modelObject, this.view);
//      }
//    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("Upload")){
      String filePath = view.getFilePath();
      StockPortfolioCommand command;
      command = new UploadPortfolio(filePath);
      boolean status = command.process(this.modelObject, this.view);
      System.out.println(status);
      if (status){
        view.clearPathSelectedLabel();
      } else {
        view.errorPathSelectedLabel();
      }
    }
  }
}