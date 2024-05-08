package javau9.budget.services;

import javau9.budget.models.Expense;
import javau9.budget.models.Income;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BudgetService {
    List<Income> getIncomeList();
    Income addIncome(Income income);
    Boolean removeIncome(Long id);
    Optional<Income> getIncomeById(Long id);
    Optional<Income> updateIncome(Long id, Income income);
    void saveInc(Income income);
    List<Income> getIncomesBetweenDates(LocalDate startDate, LocalDate endDate);

    List<Expense> getExpensesList();
    Expense addExpense(Expense expense);
    Boolean removeExpense(Long id);
    Optional<Expense> getExpenseById(Long id);
    Optional<Expense> updateExpense(Long id, Expense expense);
    void saveExp(Expense expense);
    List<Expense> getExpensesBetweenDates(LocalDate startDate, LocalDate endDate);

    double getTotalIncome();
    double getTotalExpenses();
    double getBalance();
}

