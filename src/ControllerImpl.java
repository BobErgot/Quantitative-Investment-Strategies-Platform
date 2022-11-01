import java.io.InputStream;
import java.io.PrintStream;
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
  public boolean createPortfolio() {
    viewObject.showCreatePortfolioMenu();
    int choice = scanner.nextInt();
    switch (choice) {
      case 1:
        this.addShareWithApiInput();
        break;
      case 2:
        modelObject.createPortfolio();
        break;
      case 3:
        return false;
    }
    return true;
  }

  @Override
  public void addShareWithApiInput() {
    viewObject.showAddShareWithApiInputMenu(0);
    String companyName = scanner.nextLine();
    viewObject.showAddShareWithApiInputMenu(1);
    int numShares = scanner.nextInt();
    modelObject.addShareToModel(companyName, numShares);
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
        int selectedId = scanner.nextInt();
        flag = modelObject.idIsPresent(selectedId);
        if (flag)
          viewObject.showValuation(modelObject.getValuation(id, (a) -> true));
      }
      while(!flag);
    }
  }
}
