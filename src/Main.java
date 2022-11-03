import controller.Controller;
import controller.ControllerImpl;
import model.ModelImplementation;

public class Main {
  public static void main(String[] args) {
    Controller c = new ControllerImpl(System.in, System.out, new ModelImplementation());
    c.go();
  }
}
