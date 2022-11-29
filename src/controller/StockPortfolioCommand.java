package controller;

import gui.HomeScreen;
import model.ModelInterface;

/**
 * The interface represents the command view at each step when the user enters an input.
 */
public interface StockPortfolioCommand {

  /**
   * Executes the required instructions or process flow when the command is invoked.
   *
   * @param model model object passed to command method if it needs data from storage applications
   * @param view  view object passed to command method if it needs access to user input
   */
  <T> boolean process(ModelInterface model, HomeScreen view);

  /**
   * Reverses the last change performed by the user.
   * @param model model object passed to command method if it needs data from storage applications
   */
  void undo(ModelInterface model);
}
