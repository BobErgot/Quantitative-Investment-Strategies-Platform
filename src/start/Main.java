package start;

import controller.Controller;
import controller.ControllerImpl;
import generalcontroller.GeneralController;
import gui.GUIView;
import gui.HomeScreen;
import model.ModelImplementation;

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
    if (args.length >= 1 && args[0].trim().equalsIgnoreCase("text_based")) {
      Controller c = new ControllerImpl(System.in, System.out, new ModelImplementation());
      c.start();
    } else {
      GUIView view = new HomeScreen();
      GeneralController generalController = new GeneralController(new ModelImplementation());
      generalController.setView(view);
    }
  }
}
