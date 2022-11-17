package model;

/**
 * This class behaves as a gateway between model objects and operations and the controller and
 * provides all model level functionalities to the controller interface from higher level.
 */
public class ModelImplementation extends ModelAbstract {
  /**
   * Construct a model implementation object and initialises the local set of shares and portfolios
   * and creates objects of file and api interface which it will be using to work with model on
   * lower level.
   */
  public ModelImplementation() {
    super();
  }
}
