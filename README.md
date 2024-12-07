# Project Documentation

## Overview
This is a personal finance program that will be able to hold and display your frequently checked personal financial information all in one single place, and to display analytics from that info.

The program will allow you to view all your bills, debts, and balances from all your accounts in one place. It will provide reminders for payment due dates. It will not only help you to easily see all your finances in one place, but also show useful graphical representations of them.

---

## Development Environment
- Java with Java FX
- MySQL database
- Integration with Plaid API

---

## Implementation Phases
1. Design Database and Create Login/Signup UI
2. Create Overview UI
3. Create Manage Accounts UI
4. Create Settings UI
5. Research Plaid API integration
6. Create tables/graphs in UIs:
   - Overview
   - Transactions
   - Spending
   - Budgeting
7. Code Implementation:
   - Integrating user interfaces with respective tables/graphs
   - Integrating database
   - Integrating Plaid

---

## Screen Layouts
![Screen Layouts](Documentation%20Screenshots/loginscreen.png)

![Screen Layouts](Documentation%20Screenshots/signupscreen.png)

![Screen Layouts](Documentation%20Screenshots/accounts.png)

![Screen Layouts](Documentation%20Screenshots/overview.png)

![Screen Layouts](Documentation%20Screenshots/transactions.png)

![Screen Layouts](Documentation%20Screenshots/spending.png)

![Screen Layouts](Documentation%20Screenshots/budget.png)

![Screen Layouts](Documentation%20Screenshots/accounts.png)

---

## Database Diagram
![Database Diagram](Documentation%20Screenshots/databasediagram.png)

---

## High-Level System Architecture & Data Flow Diagram
![System Architecture & Data Flow Diagram](Documentation%20Screenshots/dataflowdiagram.png)

---

## Use Case Diagram
![Use Case Diagram](Documentation%20Screenshots/usecasediagram.png)

---

## High-Level Test Cases
- **User was able to:**
  - Sign up
  - Log in
  - Change password
  - Change profile picture
  - Log in via Plaid webpage
  - Import accounts from Plaid
  - View correct total liquid money, total debt, & number of accounts
  - View correct list of transactions
  - View correct spending categories as a pie chart
  - Designate any number of categories as essential & non-essential then view as a pie chart
  - Delete all financial accounts
  - Sign out
