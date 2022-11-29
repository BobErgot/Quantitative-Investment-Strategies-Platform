package gui;

import gui_controller.Features;

public interface GUIView {

  void addFeatures(Features features);

  void clearText(String id);

  void showView();
}
