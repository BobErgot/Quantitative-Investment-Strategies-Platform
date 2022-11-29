package controller.commands;

import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.YEARS;

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
    LocalDate to = null;
    LocalDate from = null;
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
    if (MONTHS.between(from, to) < 5) {
      group = Periodicity.DAY;
    } else if (YEARS.between(from, to) < 5) {
      group = Periodicity.MONTH;
    } else {
      group = Periodicity.YEAR;
    }
    flag = false;
    List<Double> portfolioPerformance;
    do {
      portfolioPerformance = model.getPortfolioPerformance(selectedId, from, to, group);
      if (portfolioPerformance.size() < 5) {
        if (group == Periodicity.DAY) {
          flag = true;
        } else if (group == Periodicity.MONTH) {
          group = Periodicity.DAY;
        } else {
          group = Periodicity.MONTH;
        }
      } else {
        flag = true;
      }
    }
    while (!flag);
    if (portfolioPerformance.size() == 0) {
      view.printInvalidInputMessage();
      return;
    }

    double max = Collections.max(portfolioPerformance);
    double min = Collections.min(portfolioPerformance);
    int scale = (int) Math.floor(Math.log10(min));

    view.printMessage("Graph From:" + from + "\tTo:" + to);

    LocalDate previousDate = to;
    int index = 0;

    int numReqSkip = portfolioPerformance.size() - 30;

    for (LocalDate date = to;
         index < portfolioPerformance.size() && (date.isAfter(from) || date.equals(from));
         date = date.plusDays(-1)) {

      if ((previousDate.getYear() != (date.getYear()) && group == Periodicity.YEAR) || (
              previousDate.getMonth().compareTo(date.getMonth()) != 0 && group == Periodicity.MONTH)
              || (group == Periodicity.DAY)) {
        previousDate = date;
        numReqSkip--;
        if ((numReqSkip % 2 == 0 || numReqSkip % 3 == 0 || numReqSkip % 5 == 0) && numReqSkip > 0) {
          continue;
        }
        view.printStars(date, group, portfolioPerformance.get(index++), scale);
      }
      previousDate = date;


    }
    view.printMessage("SCALE:\t * ≈ " + (int) Math.pow(10, scale) + "$");
  }

  @Override
  public void undo(ModelInterface model) {
    // Undo functionality from future use
  }
}
