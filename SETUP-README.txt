Unzip the submitted file and copy the JAR file to desired location in an empty folder.

commands to run the program:
1. jar tf pdp.jar
2. jar xf pdp.jar stocker
3. java -jar pdp.jar

To run this program on to get valuation of a portfolio from a set of multiple portfolios with different stocks:

[EXAMPLE FOLDER CONTAINS THIS FORMAT]
[you can directly go to step 3 if you want to run this program with default values, skip upload part (step 4) as well]
1. Get stocks & portfolio in given format :

> Stocks are CSV file with four fields :

Ticker Name, Date of Stock Purchase, Cost of one stock, Number of stocks
Ticker Name2, Date of Stock Purchase2, Cost of one stock2, Number of stocks2
...

> Portfolio format :

<Name of Portfolio>, <Date of Portfolio Creation>, -F:<FILENAME.csv>
<Name of Portfolio2>, <Date of Portfolio Creation2>, -F:<FILENAME.csv2>
...

2. Save your Portfolio and stock folder in the same folder.

3. Run the jar file

4. From start menu select option 2 then select option 2 (Relative Path), provide the path "example\portfolio.csv" or "example/portfolio.csv"

5. From start menu select option 3 to view all portfolios

5. Select ID of portfolio you want to get valuation of portfolios for

6. Enter date that you want to valuate your portfolios on & your answer will be present.


Detailed Example:


* Home Menu:
1. Create Portfolio
2. Upload Portfolio from given path
3. View Portfolio
Type 'quit' or 'exit' to close the program
Please select an option from 1-x from above: 1

Which type of portfolio do you want to create?
1. Fixed Portfolio
2. Flexible Portfolio2

* Create Portfolio Menu:
1. Add Shares
Type 'back' to return to Home Menu
Please select an option from 1-x from above: 1

Enter Company Name: IBM

Enter Number of shares: 20

* Create Portfolio Menu:
1. Add Shares
2. Create Portfolio (Finalize current Portfolio)
Type 'back' to return to Home Menu
Please select an option from 1-x from above: 2

Please write down the unique name of portfolioexample

* Home Menu:
1. Create Portfolio
2. Upload Portfolio from given path
3. View Portfolio
Type 'quit' or 'exit' to close the program
Please select an option from 1-x from above: 3

Portfolios available:
------------------------------------------------------------------------------------
||Serial Number     ||Portfolio Name                          ||Creation Date     ||
||------------------||----------------------------------------||------------------||
||0.                ||lkdsfjkl                                ||2022-11-17        ||
||------------------||----------------------------------------||------------------||
||1.                ||example                                 ||2022-11-17        ||
------------------------------------------------------------------------------------

* View Portfolio Menu:
1. View composition of a particular portfolio
2. Valuation of Portfolio on a specific date
3. Cost basis of a portfolio till a specific date
4. Purchase a share and add to portfolio
5. Sell a share from portfolio
6. Performance of portfolio over time
Type 'back' to return to Home Menu
Please select an option from 1-x from above: 4

Select a 'Portfolio Name' from above list:	example

Enter Company Name: AAPL

Enter Number of shares: 20

Please enter date in yyyy-mm-dd format: 2012-02-02

Paid amount from buying of Portfolio is:	$9122.4
This company's number of stocks have been updated!

Portfolios available:
------------------------------------------------------------------------------------
||Serial Number     ||Portfolio Name                          ||Creation Date     ||
||------------------||----------------------------------------||------------------||
||0.                ||lkdsfjkl                                ||2022-11-17        ||
||------------------||----------------------------------------||------------------||
||1.                ||example                                 ||2022-11-17        ||
------------------------------------------------------------------------------------

* View Portfolio Menu:
1. View composition of a particular portfolio
2. Valuation of Portfolio on a specific date
3. Cost basis of a portfolio till a specific date
4. Purchase a share and add to portfolio
5. Sell a share from portfolio
6. Performance of portfolio over time
Type 'back' to return to Home Menu
Please select an option from 1-x from above: 4

Select a 'Portfolio Name' from above list:	example

Enter Company Name: ZZZ

Enter Number of shares: 23

Please enter date in yyyy-mm-dd format: 2021-02-02

Paid amount from buying of Portfolio is:	$20.23
This company's number of stocks have been updated!

Portfolios available:
------------------------------------------------------------------------------------
||Serial Number     ||Portfolio Name                          ||Creation Date     ||
||------------------||----------------------------------------||------------------||
||0.                ||lkdsfjkl                                ||2022-11-17        ||
||------------------||----------------------------------------||------------------||
||1.                ||example                                 ||2022-11-17        ||
------------------------------------------------------------------------------------

* View Portfolio Menu:
1. View composition of a particular portfolio
2. Valuation of Portfolio on a specific date
3. Cost basis of a portfolio till a specific date
4. Purchase a share and add to portfolio
5. Sell a share from portfolio
6. Performance of portfolio over time
Type 'back' to return to Home Menu
Please select an option from 1-x from above: 4

Select a 'Portfolio Name' from above list:	example

Enter Company Name: UA

Enter Number of shares: 293

Please enter date in yyyy-mm-dd format: 2022-02-03

Paid amount from buying of Portfolio is:	$4754.88
This company's number of stocks have been updated!



Portfolios available:
------------------------------------------------------------------------------------
||Serial Number     ||Portfolio Name                          ||Creation Date     ||
||------------------||----------------------------------------||------------------||
||0.                ||lkdsfjkl                                ||2022-11-17        ||
||------------------||----------------------------------------||------------------||
||1.                ||example                                 ||2022-11-17        ||
------------------------------------------------------------------------------------

* View Portfolio Menu:
1. View composition of a particular portfolio
2. Valuation of Portfolio on a specific date
3. Cost basis of a portfolio till a specific date
4. Purchase a share and add to portfolio
5. Sell a share from portfolio
6. Performance of portfolio over time
Type 'back' to return to Home Menu
Please select an option from 1-x from above: 1

Select a 'Portfolio Name' from above list:	example

+id:example
creationDate:2022-11-17
Shares in this Portfolio:
+companyName:IBM,purchaseDate:2022-11-17,price:137.83,numShares:20
+companyName:AAPL,purchaseDate:2012-02-02,price:456.12,numShares:20
+companyName:ZZZ,purchaseDate:2021-02-02,price:0.8795652173913043,numShares:23
+companyName:UA,purchaseDate:2022-02-03,price:16.228259385665528,numShares:293


Portfolios available:
------------------------------------------------------------------------------------
||Serial Number     ||Portfolio Name                          ||Creation Date     ||
||------------------||----------------------------------------||------------------||
||0.                ||lkdsfjkl                                ||2022-11-17        ||
||------------------||----------------------------------------||------------------||
||1.                ||example                                 ||2022-11-17        ||
------------------------------------------------------------------------------------

* View Portfolio Menu:
1. View composition of a particular portfolio
2. Valuation of Portfolio on a specific date
3. Cost basis of a portfolio till a specific date
4. Purchase a share and add to portfolio
5. Sell a share from portfolio
6. Performance of portfolio over time
Type 'back' to return to Home Menu
Please select an option from 1-x from above: 3

Select a 'Portfolio Name' from above list:	example

Please enter date in yyyy-mm-dd format: 2021-02-02

Cost Basis of Portfolio is:	$9142.63

Portfolios available:
------------------------------------------------------------------------------------
||Serial Number     ||Portfolio Name                          ||Creation Date     ||
||------------------||----------------------------------------||------------------||
||0.                ||lkdsfjkl                                ||2022-11-17        ||
||------------------||----------------------------------------||------------------||
||1.                ||example                                 ||2022-11-17        ||
------------------------------------------------------------------------------------

* View Portfolio Menu:
1. View composition of a particular portfolio
2. Valuation of Portfolio on a specific date
3. Cost basis of a portfolio till a specific date
4. Purchase a share and add to portfolio
5. Sell a share from portfolio
6. Performance of portfolio over time
Type 'back' to return to Home Menu
Please select an option from 1-x from above: 3

Select a 'Portfolio Name' from above list:	2012-02-09

Invalid Input!
Select a 'Portfolio Name' from above list:	example

Please enter date in yyyy-mm-dd format: 2012-02-09

Cost Basis of Portfolio is:	$9122.4

Portfolios available:
------------------------------------------------------------------------------------
||Serial Number     ||Portfolio Name                          ||Creation Date     ||
||------------------||----------------------------------------||------------------||
||0.                ||lkdsfjkl                                ||2022-11-17        ||
||------------------||----------------------------------------||------------------||
||1.                ||example                                 ||2022-11-17        ||
------------------------------------------------------------------------------------

* View Portfolio Menu:
1. View composition of a particular portfolio
2. Valuation of Portfolio on a specific date
3. Cost basis of a portfolio till a specific date
4. Purchase a share and add to portfolio
5. Sell a share from portfolio
6. Performance of portfolio over time
Type 'back' to return to Home Menu
Please select an option from 1-x from above: 2

Select a 'Portfolio Name' from above list:	example

Please enter date in yyyy-mm-dd format: 2022-02-02

Valuation of Portfolio is:	$0.0

Portfolios available:
------------------------------------------------------------------------------------
||Serial Number     ||Portfolio Name                          ||Creation Date     ||
||------------------||----------------------------------------||------------------||
||0.                ||lkdsfjkl                                ||2022-11-17        ||
||------------------||----------------------------------------||------------------||
||1.                ||example                                 ||2022-11-17        ||
------------------------------------------------------------------------------------

* View Portfolio Menu:
1. View composition of a particular portfolio
2. Valuation of Portfolio on a specific date
3. Cost basis of a portfolio till a specific date
4. Purchase a share and add to portfolio
5. Sell a share from portfolio
6. Performance of portfolio over time
Type 'back' to return to Home Menu
Please select an option from 1-x from above: 2

Select a 'Portfolio Name' from above list:	example

Please enter date in yyyy-mm-dd format: 2022-11-17

Valuation of Portfolio is:	$8054.68

Portfolios available:
------------------------------------------------------------------------------------
||Serial Number     ||Portfolio Name                          ||Creation Date     ||
||------------------||----------------------------------------||------------------||
||0.                ||lkdsfjkl                                ||2022-11-17        ||
||------------------||----------------------------------------||------------------||
||1.                ||example                                 ||2022-11-17        ||
------------------------------------------------------------------------------------

* View Portfolio Menu:
1. View composition of a particular portfolio
2. Valuation of Portfolio on a specific date
3. Cost basis of a portfolio till a specific date
4. Purchase a share and add to portfolio
5. Sell a share from portfolio
6. Performance of portfolio over time
Type 'back' to return to Home Menu
Please select an option from 1-x from above: 2
