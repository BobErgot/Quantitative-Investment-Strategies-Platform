package controller;

import controller.Controller;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Scanner;
import model.ModelImplementation;
import model.ModelInterface;
import view.View;
import view.ViewImpl;

public class ControllerImpl implements Controller {

  View viewObject;
  ModelInterface modelObject;
  Scanner scanner;

  public ControllerImpl(InputStream in, PrintStream out) {
    this.scanner = new Scanner(in);
    this.viewObject = new ViewImpl(out);
    this.modelObject = new ModelImplementation();
  }

  @Override
  public void createPortfolio() {
    boolean invalidInput;
    boolean portfolioCompleted;
    do {
      invalidInput = false;
      portfolioCompleted = false;
      viewObject.showCreatePortfolioMenu();
      int choice = scanner.nextInt();
      switch (choice) {
        case 1:
          this.addShareWithApiInput();
          break;
        case 2:
          boolean invalidPortfolioName = false;
          do {
            invalidPortfolioName = false;
            viewObject.askForPortfolioName();
            String portfolioName = scanner.next();
            if (portfolioName.length() > 0) {
              modelObject.createPortfolio(portfolioName);
              portfolioCompleted = true;
            } else {
              invalidPortfolioName = true;
            }
            if (invalidPortfolioName) {
              viewObject.printInvalidInputMessage();
            }
          } while (invalidPortfolioName);
          break;
        case 3:
//          modelObject.clearPortfolioList();
          invalidInput=false;
          portfolioCompleted=true;
          break;
        default:
          invalidInput = true;
          break;
      }
      if (invalidInput) {
        viewObject.printInvalidInputMessage();
      }
    } while (invalidInput || !portfolioCompleted);

  }

  @Override
  public void addShareWithApiInput() {
    viewObject.showAddShareWithApiInputMenu(0);
    String companyName = scanner.next();
    viewObject.showAddShareWithApiInputMenu(1);
    int numShares = scanner.nextInt();
    modelObject.addShareToModel(companyName, LocalDate.now(), numShares);
  }

  @Override
  public void viewPortfolio() {
    viewObject.showViewPortfolioMenu(modelObject.getPortfolio());
    viewObject.showAdditionalPortfolioInformation();
    int choice = scanner.nextInt();
    if (choice == 1) {
      boolean flag = false;
      do {
        viewObject.selectPortfolio();
        String selectedId = scanner.next();
        flag = modelObject.idIsPresent(selectedId);
        if (flag) {
          viewObject.showValuation(modelObject.getValuation(selectedId, (a) -> true));
        }
      } while (!flag);
    }
  }

  @Override
  public void uploadPortfolio() {
    boolean validPath = false;
    do {
      viewObject.showUploadPortfolioOptions();
      String path = scanner.next();
      if (path.length() > 0) {
        validPath = true;
        modelObject.addPortfolioByUpload(path);
      }
      if (!validPath) {
        viewObject.printInvalidInputMessage();
      }
    } while (!validPath);
  }

  @Override
  public void singleRunThrough() {
    boolean invalidInput;
    do {
      invalidInput = false;
      viewObject.showMainMenu();
      int choice = scanner.nextInt();
      switch (choice) {
        case 1:
          this.createPortfolio();
          break;
        case 2:
          this.uploadPortfolio();
          break;
        case 3:
          this.viewPortfolio();
          break;
        case 4:
          break;
        default:
          invalidInput = true;
          break;
      }
      if (invalidInput) {
        viewObject.printInvalidInputMessage();
      }
    } while (invalidInput);
  }

  @Override
  public void go() {
    boolean userContinue;
    do {
      userContinue = false;
      this.singleRunThrough();
      viewObject.askIfUserContinues();
      int choice = scanner.nextInt();
      if (choice == 1) {
        userContinue = true;
      }
    } while (userContinue);
  }

}
