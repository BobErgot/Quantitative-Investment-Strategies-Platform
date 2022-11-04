JAR commands to run the program:
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

4. From main menu select option 2 then select option 2 (Relative Path), provide the path "example\portfolio.csv" or "example/portfolio.csv"

5. From main menu select option 3 to view all portfolios

5. Select ID of portfolio you want to get valuation of portfolios for

6. Enter date that you want to valuate your portfolios on & your answer will be present.