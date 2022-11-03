package controller;


public interface Controller {
  void createPortfolio();

  boolean showValuationOfPortfolio(String selectedId);

  void viewPortfolio();

  void addShareWithApiInput();

  void go();

  void uploadPortfolio();
}
