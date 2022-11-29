package start;

import controller.Controller;
import controller.ControllerImpl;
import gui.HomeScreen;
import model.ModelImplementation;
import model.ModelInterface;

/**
 * This class serves as an entryway for the portfolio management program.
 */
public class Main {
  /**
   * Called when the program jar is ran and creates object of the controller which takes over.
   *
   * @param args command line arguments passed when the jar is executed
   */
  public static void main(String[] args) {
    ModelInterface model = new ModelImplementation();
    HomeScreen view = new HomeScreen();
    Controller controller = new ControllerImpl(model, view);
    controller.start();
  }
}
