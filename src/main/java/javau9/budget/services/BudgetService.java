package javau9.budget.services;

import javau9.budget.models.Expenses;
import javau9.budget.models.Income;

import java.util.List;
import java.util.Optional;

public interface BudgetService {
    List<Income> getIncomeList();
    Income addIncome(Income income);
    Boolean removeIncome(Long id);
    Optional<Income> getIncomeById(Long id);
    Optional<Income> updateIncome(Long id, Income income);
    void saveInc(Income income);

    List<Expenses> getExpensesList();
    Expenses addExpense(Expenses expense);
    Boolean removeExpense(Long id);
    Optional<Expenses> getExpenseById(Long id);
    Optional<Expenses> updateExpense(Long id, Expenses expense);
    void saveExp(Expenses expense);

    double getTotalIncome();
    double getTotalExpenses();
    double getBalance();
}

