package controller.commands;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import model.ModelInterface;
import model.Periodicity;
import view.View;

/**
 * Interacts with the model interface object to get data of a specific portfolio between the two
 * dates specified by the user and generates chart with dynamic x-axis and y-axis scaling.
 */
public class GeneratePerformanceGraph implements controller.StockPortfolioCommand {

  @Override
  public void process(View view, Scanner scanner, ModelInterface model) {
    view.selectPortfolio();
    boolean flag;
    String selectedId;
    do {
      selectedId = scanner.next().trim();
      flag = model.idIsPresent(selectedId);
    }
    while (!flag);
    LocalDate to = null, from = null;
    flag = false;
    do {
      try {
        view.askForDate();
        from = LocalDate.parse(scanner.next().trim());
        view.askForDate();
        to = LocalDate.parse(scanner.next().trim());
        flag = true;
      } catch (DateTimeParseException invalidDate) {
        view.printInvalidDateError();
      }
    }
    while (!flag);

    Periodicity group = null;
    flag = false;
    do {
      try {
        view.askForEnum(Periodicity.class);
        String groupString = scanner.next().trim();
        if (groupString.equalsIgnoreCase("day")) {
          group = Periodicity.DAY;
        }
        if (groupString.equalsIgnoreCase("month")) {
          group = Periodicity.MONTH;
        }
        if (groupString.equalsIgnoreCase("year")) {
          group = Periodicity.YEAR;
        }
        flag = true;
      } catch (DateTimeParseException invalidDate) {
        view.printInvalidDateError();
      }
    }
    while (!flag);

    List<Double> portfolioPerformance = model.getPortfolioPerformance(selectedId, from, to, group);
    if(portfolioPerformance.size()==0) {
      view.printInvalidInputMessage(); //TODO
      return;
    }

    double max = Collections.min(portfolioPerformance);
    double min = Collections.max(portfolioPerformance);
    int scale = (int) Math.floor(Math.log10((max + min) / 2));

    LocalDate previousDate = to;
    int index = 0;
    for (LocalDate date = to; index<portfolioPerformance.size() && (date.isAfter(from) ||
            date.equals(from)); date = date.plusDays(-1)) {
      if (previousDate.getYear() != (date.getYear()) && group == Periodicity.YEAR) {
        view.printStars(date, group, portfolioPerformance.get(index++), scale);
      } else if (previousDate.getMonth().compareTo(date.getMonth()) != 0
          && group == Periodicity.MONTH) {
        view.printStars(date, group, portfolioPerformance.get(index++), scale);
      } else if (group == Periodicity.DAY) {
        view.printStars(date, group, portfolioPerformance.get(index++), scale);
      }
      previousDate = date;
    }
    System.out.println("SCALE:\t * ≈ " + (int) Math.pow(10, scale) + "$");
  }

  @Override
  public void undo(ModelInterface model) {
  }
}
