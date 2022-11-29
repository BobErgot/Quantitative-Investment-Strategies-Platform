package start;

import gui.HomeScreen;
import gui_controller.GeneralController;
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
//    Controller c = new ControllerImpl(System.in, System.out, new ModelImplementation());
//    c.start();
    HomeScreen view = new HomeScreen();
    GeneralController generalController = new GeneralController( new ModelImplementation());
    generalController.setView(view);
  }
}
