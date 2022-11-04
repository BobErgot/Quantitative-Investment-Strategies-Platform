package controller;

/**
 * This interface represents all the operations to be supported by a controller implementation.
 */
public interface Controller {
  /**
   * Asks the user for input like if he wants to add shares, once a share is added the user get
   * the option to create the portfolio. On receiving confirmation from the user the function
   * interacts with the model interface object to transmit the portfolio information to be created.
   */
  void createPortfolio();

  /**
   * Returns the valuation of the portfolio which the user has entered and returns false if the
   * portfolio with the given id does not exist.
   * @param selectedId unique id of the portfolio for which the valuation is required on a
   *                   particular date
   * @return "true" if valuation exists else return "false"
   */
  boolean showValuationOfPortfolio(String selectedId);

  /**
   * Interacts with the model interface object to get all portfolio information and then returns
   * the same
   * to the user as string output.
   */
  void viewPortfolio();

  /**
   * Receives share information from the user and passes it to the model interface object.
   */
  void addShareWithApiInput();

  /**
   * Behaves as the main menu for the program and gives the option to view, create and upload
   * portfolio and navigate to appropriate state flow based on user interaction and model
   * interface output.
   */
  void start();

  /**
   * Sends the path from where the portfolio needs to be uploaded to the model interface object
   * and notifies the user if something goes wrong during upload.
   */
  void uploadPortfolio();
}
