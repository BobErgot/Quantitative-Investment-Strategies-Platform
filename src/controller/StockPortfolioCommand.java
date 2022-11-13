package controller;

import java.util.Scanner;

import model.ModelInterface;
import view.View;

public interface StockPortfolioCommand {
  void process(View view, Scanner scanner, ModelInterface model);

  void undo(ModelInterface model);
}
