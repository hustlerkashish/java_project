#Tax Calculation Billing System
Overview
The Tax Calculation Billing System is a Java-based desktop application built using Swing. It allows users to create and manage bills by adding products with specific tax rates depending on their categories. The application provides a simple graphical interface for entering product details, calculating taxes, and generating invoices.

Features
Add Products: Users can input product details such as ID, name, price, and category. Each product will have a tax rate applied based on its category.
Tax Calculation: The system automatically calculates tax for each product based on the product category. Different categories have different tax rates.
Invoice Generation: Users can generate an invoice that calculates the total price for all products, including tax.
Bill History Search: Users can search for past bills using their unique bill number.
Bill Summary: The system maintains and displays a summary of the total number of bills generated and total sales.
Bill Tracking: Keeps track of the current bill, previous bills, and details about the last bill generated.
Product Categories and Tax Rates
Food: 5% tax
Electronics: 18% tax
Clothing: 12% tax
Other: 10% default tax
User Interface
Panels
Input Panel: Users can enter product details here.
Product Table: Displays added products along with calculated tax and total price.
Bill Information: Shows information about the current bill and system-wide statistics such as total sales.
Invoice and Search Panel: Contains buttons for generating invoices and searching for past bills.
Buttons
Add Product: Adds a new product to the current bill.
Generate Invoice: Finalizes the bill, calculates the total amount, and resets the interface for the next bill.
Search Bill: Allows searching for a previously generated bill by its unique bill number.
Future Enhancements
Data Persistence: Add the ability to save and load bills from a database or file system.
Export Invoice: Export invoices as PDFs or printable formats.
Enhanced Search: Add more filters for bill searching (e.g., by date range, customer, etc.).
