package controller;

import java.util.Scanner;

import model.ModelInterface;
import view.View;

/**
 * The interface represents the command view at each step when the user enters an input.
 */
public interface StockPortfolioCommand {

  /**
   * Executes the required instructions or process flow when the command is invoked.
   * @param view view object passed to command method if it needs access to user input
   * @param scanner scanner object passed to command method if it needs any input from the user
   * @param model model object passed to command method if it needs data from storage applications
   */
  void process(View view, Scanner scanner, ModelInterface model);

  /**
   * Reverses the last change performed by the user.
   * @param model model object passed to command method if it needs data from storage applications
   */
  void undo(ModelInterface model);
}
