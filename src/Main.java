import controller.Controller;
import controller.ControllerImpl;

public class Main {
  public static void main(String[] args){
    Controller c = new ControllerImpl(System.in, System.out);
    c.go();
  }
}
