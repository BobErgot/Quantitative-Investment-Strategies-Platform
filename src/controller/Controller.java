package controller;

/**
 * This interface represents all the operations to be supported by a controller implementation.
 */
public interface Controller {

  /**
   * Behaves as the main menu for the program and gives the option to view, create and upload
   * portfolio and navigate to appropriate state flow based on user interaction and model
   * interface output.
   */
  void start();
}
