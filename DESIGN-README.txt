Model:
We have 5 main Classes in model:

1. ModelInterface : Model interface is the single point the controller interacts with, it contains all the functions that this stock software can have, it creates portfolio objects & share objects while connecting with the File Interface.
2. Portfolio : Portfolio object that keeps track of portfolio's features. Cannot be changed if instantiated. Can filter, map and reduce the list of shares with various funtions.
3. Share: Share object that contains properties of a share.
4. FileInterface: Handles file implementation
5. APIInterface: Uses AlphaVantage API to handle all of the shares with valid ticker symbols that are not present in the database.

Controller: Controller has various modular functions to control the flow of the operation between the model and the view, this interacts with model and view interface.

View: View prints appropriate values based on the function called.