package gui;

import gui_controller.Features;
import java.util.List;

public interface GUIView {

  void addFeatures(Features features);

  void clearText(String id);

  void showView();

  void listAllPortfolios(List<String> portfolios);

  void listAllMutablePortfolios(List<String> portfolios);
}
