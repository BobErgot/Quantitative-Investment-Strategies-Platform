package controller;

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
      boolean canCreateShare = modelObject.canCreateShare();
      viewObject.showCreatePortfolioMenu(canCreateShare);
      int choice = scanner.nextInt();
      switch (choice) {
        case 1:
          this.addShareWithApiInput();
          break;
        case 2:
          if (canCreateShare) {
            boolean invalidPortfolioName = false;
            do {
              invalidPortfolioName = false;
              viewObject.askForPortfolioName();
              String portfolioName = scanner.next().trim();
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
          } else {
            invalidInput = true;
          }
          break;
        case 3:
//          modelObject.clearPortfolioList();
          invalidInput = false;
          portfolioCompleted = true;
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
    boolean isValidCompany;
    do {
      viewObject.showAddShareWithApiInputMenu(0);
      String companyName = scanner.next().trim();
      isValidCompany =
          companyName.length() > 0 && companyName.length() <= 10 && Character.isAlphabetic(
              companyName.charAt(0)) && modelObject.checkTicker(companyName);
      if(isValidCompany) {
        viewObject.showAddShareWithApiInputMenu(1);
        int numShares = scanner.nextInt();
        modelObject.addShareToModel(companyName, LocalDate.now(), numShares);
      }
      else
        viewObject.notPresentError("Company");
    } while(!isValidCompany);
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
        String selectedId = scanner.next().trim();
        flag = modelObject.idIsPresent(selectedId);
        if (flag) {
          viewObject.showValuation(modelObject.getValuation(selectedId, (a) -> true));
        } else {
          viewObject.notPresentError("File");
        }
      } while (!flag);
    }
  }

  @Override
  public void uploadPortfolio() {
    boolean validPath = false;
    do {
      viewObject.showUploadPortfolioOptions();
      String path = scanner.next().trim();
      if (path.length() > 0) {
        // if portfolio successfully uploaded , it will return true.
        validPath = modelObject.addPortfolioByUpload(path);
      }
      if (!validPath) {
        viewObject.printInvalidInputMessage();
      }
    } while (!validPath);
  }

  @Override
  public void go() {
    boolean invalidInput;
    boolean haveUserContinue;

    do {
      invalidInput = false;
      haveUserContinue = true;

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
          haveUserContinue = false;
          break;
        default:
          invalidInput = true;
          break;
      }
      if (invalidInput) {
        viewObject.printInvalidInputMessage();
      }
    } while (invalidInput || haveUserContinue);
  }

}
