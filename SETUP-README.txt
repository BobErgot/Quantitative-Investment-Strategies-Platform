A text description (SETUP-README.txt) in the res folder of how exactly to run your program  from the
 JAR file. If you require the jar file to be in a specific folder, or require other files  with it,
 please include these directions here. You should also include detailed instructions on how  to run
 your program to create a portfolio, purchase stocks of at least 3 different companies in that
 portfolio at different dates and then query the value and cost basis of that portfolio on two
 specific dates. We will run your program with this data to begin grading. You should also include
 a list of stocks that your program supports, along with dates on which its value can be determined
  (if there are restrictions in your program about which data is available).


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