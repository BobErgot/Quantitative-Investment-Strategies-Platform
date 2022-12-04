package gui;

import generalcontroller.Features;

import java.util.List;

/**
 * This interface represents all the operations to be supported by a view implementation. This is
 * agnostic of text-base or gui-based implementations.
 */
public interface GUIView {

  /**
   * Adds action listeners to all buttons and moving parts of the GUI.
   *
   * @param features Features to use on
   */
  void addFeatures(Features features);

  void showView();

  void listAllPortfolios(List<String> portfolios);

  void listAllMutablePortfolios(List<String> portfolios);
}
