import controller.Controller;
import controller.ControllerImpl;
import model.ModelImplementation;

/**
 * This class serves as an entryway for the portfolio management program.
 */
public class Main {
  /**
   * Main function which is called when the program jar is ran and creates object of the
   * controller which takes over.
   * @param args command line arguments passed when the jar is executed
   */
  public static void main(String[] args) {
    Controller c = new ControllerImpl(System.in, System.out, new ModelImplementation());
    c.start();
  }
}
