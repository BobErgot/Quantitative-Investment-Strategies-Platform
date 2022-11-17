Design Changes we have made compared to last submission:

Model:
1. Last design lacked an abstract class between the model interface and concrete implementation
   class.
2. In this new design we have added an abstract class between the two which resulted in the
   "ModelImplementation" class methods to be moved to abstract without any code change and the same
   code is now reused by "FlexibleModelImplementation".
3. The reason we decided against inheritance is that any change in parent will cause undesirable
   effects in the child classes which will require the child classed to overwrite that parent class
   change which can become cumbersome if a parent class has multiple child classes in case there are
    more than one type of portfolios.

Controller:
1. The controller is moved to the Command Design Pattern with the view that the code becomes much
    easier to maintain in the controller class as new functionality and features are added.
2. The switch case that was present in the previous design was expanding rapidly with new
   functionalities and hence from future point of view we decided to remodel the controller.
3. Although the controller design was remodelled there is no logical change in the way features were
   implemented and the logic was simply moved to the individual command classes.



General Design -

Model Folder:
We have 12 classes grouped under model. They can be furthered subdivided into three groups:
1. APIInterface - This interface represents all the API operations to be supported by the concrete
                  implementation.
    a. WebAPI -  This class represents operation of web API call to the ALPHA VANTAGE API

2. FileInterface - This interface represents all the I/O operations to be supported by a file
                   database.
    a. FileAbstract - This clas contains all the method definitions that are common to the concrete
                      implementations of the FileInterface like create, write, delete or clear a
                      file and check if files exists and create file path.
        i. CSVFile - This class represents files and its operations when it's data is comma
                     -separated values(csv) format.

3. ModelInterface - This interface represents all the Model operations to be supported by the
                    concrete implementation and hides low level model operations from whoever is
                    trying to access information. It is the single point the controller interacts
                    with, it contains all the functions that this stock software can have, it
                    creates portfolio objects & share objects while connecting with the File
                    Interface.
    a. ModelAbstract - This clas contains all the method definitions that are common to the concrete
                       implementations of the FileInterface like get a portfolio by its id, get
                       portfolio valuation, cost basis value, map the shares to given date, get
                       valuation based on lambda filters, check if ticker symbol exits in database,
                       etc.
        i. ModelImplementation: This class supports all the functionalities developed during the last
                                assignment.
       ii. FlexibleModelImplementation: This class supports all the functionalities needed by the
                                        flexible portfolio like create flexible portfolio and buy &
                                        sell stocks from it.

Other are low level model classes whose access is hidden from the user and can only be operated with
 through the use of ModelInterface concrete implementation.
1. Portfolio - This class represents a portfolio. A portfolio has a unique id for one user, creation
               date of portfolio and list of share objects associated with this portfolio. Portfolio
                object that keeps track of portfolio's features. Cannot be changed if instantiated.
                Can filter, map and reduce the list of shares with various functions.

2. Share - This class represents a share. A share has a date of purchase, company symbol/ticker,
           price on the date of purchase and number of stocks bought.

3. Periodicity - This is an enum class that has possible filters for performance graph.


View Folder:
We have 2 classes grouped under view.
1. View - This interface represents all the operations to be supported by a view implementation.
    a. ViewImpl - The view implementation that simply shows appropriate message to the user based on
                  the method call from the controller. It has absolutely no visibility of controller
                   or model implementation or working and simply behaves based on the arguments
                   received from the controller.

Controller Folder:
The controller folder has 12 classes where 9 classes implement the "StockPortfolioCommand" interface
 in order to implement command design pattern for efficient code maintenance in the future.

1. Controller - This interface has various modular functions to control the flow of the operation
                between the model and the view, this interacts with model and view interface.
    a. ControllerImpl - The controller implementation that receives all its inputs from an
                        InputStream object and transmits all outputs to a PrintStream object. It
                        also interacts with the model based on the received input from the user.