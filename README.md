# Budget Management System

## Overview
Simple Spring Boot project with connection to MySQL and UI (Thymeleaf).
The Budget Management System is a web application designed to help users manage their financial transactions. It allows users to track their incomes and expenses, add new entries, update existing ones, and delete unwanted records.

## Features
- **View Balances**: Check current balance status.
- **Manage Incomes**: Add, list, update, and delete income records.
- **Manage Expenses**: Add, list, update, and delete expense records.
- **Search**: Find incomes and expenses by ID.
- - **REST API**: Manage incomes and expenses via RESTful endpoints.

## Controllers
### BudgetHomeController
Manages navigation and views within the application, handling routes like `/`, `/incomeList`, and `/expensesList`.

### BudgetAPIController
Provides RESTful APIs for managing incomes and expenses, including endpoints like `/viewIncomeList`, `/incomeAdd`, and `/expenseAdd`.

## Services
### BudgetServiceImpl
Implements business logic for managing incomes and expenses, interfacing with MySQL through the `IncomeRepository` and `ExpenseRepository`.

## Technologies
- Java: Backend logic.
- Spring Boot: Application framework.
- MySQL: Database management.
- Thymeleaf: Templating for HTML views.
- HTML/CSS: Frontend presentation.

## Testing
- Unit tests for controllers (`BudgetHomeControllerTest`, `BudgetAPIControllerTest`) ensure the correct handling of web requests.
- Service tests (`BudgetServiceImplTest`) verify the business logic and repository interactions.

## Getting Started
To get a local copy up and running follow these simple steps:
1. Clone the repo
2. Install all dependencies
3. Configure the MySQL database
4. Run the application
5. Navigate to `http://localhost:8080/` to access the application web interface
6. Use Postman or any other API client to interact with the REST API at `http://localhost:8080/api`

## Contributions
Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

- Fork the Project
- Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
- Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
- Push to the Branch (`git push origin feature/AmazingFeature`)
- Open a Pull Request
