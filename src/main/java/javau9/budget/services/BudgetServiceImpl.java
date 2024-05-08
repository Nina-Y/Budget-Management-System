package javau9.budget.services;

import javau9.budget.models.Expense;
import javau9.budget.models.Income;
import javau9.budget.repositories.ExpenseRepository;
import javau9.budget.repositories.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BudgetServiceImpl implements BudgetService {

    IncomeRepository incomeRepository;
    ExpenseRepository expenseRepository;

    @Autowired
    public BudgetServiceImpl(IncomeRepository incomeRepository, ExpenseRepository expenseRepository) {
        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
        seedDummyDataIncome(); // после первого запуска закомментить, иначе каждый раз будет добавляться
        seedDummyDataExpenses(); // -//-
    }

    public void seedDummyDataIncome() {
        List<Income> incomes = new ArrayList<>();
        incomes.add(new Income(1500.0, "salary", LocalDate.parse("2024-02-10"), "someInfo"));
        incomes.add(new Income(500.0, "rental income", LocalDate.parse("2024-02-10"), "someInfo"));
        incomes.add(new Income(100.0, "child benefit", LocalDate.parse("2024-02-10"), "someInfo"));
        incomes.add(new Income(200.0, "other", LocalDate.parse("2024-02-10"), "someInfo"));
        incomes.add(new Income(300.0, "other", LocalDate.parse("2024-02-10"), "someInfo"));
        incomeRepository.saveAll(incomes); // don't forget to add
    }

    public void seedDummyDataExpenses() {
        List<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense(100.0, "food", "cash", LocalDate.parse("2024-02-15"), "someInfo"));
        expenses.add(new Expense(300.0, "utility bills", "bank transfer", LocalDate.parse("2024-02-15"), "someInfo"));
        expenses.add(new Expense(50.0, "insurance", "bank transfer", LocalDate.parse("2024-02-15"), "someInfo"));
        expenses.add(new Expense(100.0, "insurance", "bank transfer", LocalDate.parse("2024-02-15"), "someInfo"));
        expenses.add(new Expense(100.0, "food", "card", LocalDate.parse("2024-02-15"), "someInfo"));
        expenseRepository.saveAll(expenses); // don't forget to add
    }

    @Override
    public List<Income> getIncomeList() {
        return incomeRepository.findAll();
    }

    @Override
    public Income addIncome(Income income) {
        return incomeRepository.save(income);
    }

    @Override
    public Boolean removeIncome(Long id) {
        incomeRepository.deleteById(id);
        return true;
    }

    @Override
    public Optional<Income> getIncomeById(Long id) {
        return incomeRepository.findById(id);
    }


    @Override
    public Optional<Income> updateIncome(Long id, Income income) {
        Optional<Income> existingIncome = incomeRepository.findById(id);
        if (existingIncome.isEmpty())
            return Optional.empty();

        Income updatedIncome = existingIncome.get();
        updatedIncome.setSum(income.getSum());
        updatedIncome.setCategory(income.getCategory());
        updatedIncome.setDate(income.getDate());
        updatedIncome.setInfo(income.getInfo());
        return Optional.of(incomeRepository.save(updatedIncome));
    }

    @Override
    public void saveInc(Income income) {
        incomeRepository.save(income);
    }

    @Override
    public List<Expense> getExpensesList() {
        return expenseRepository.findAll();
    }

    @Override
    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    @Override
    public Boolean removeExpense(Long id) {
        expenseRepository.deleteById(id);
        return true;
    }

    @Override
    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
    }

    @Override
    public Optional<Expense> updateExpense(Long id, Expense expense) {
        Optional<Expense> existingExpense = expenseRepository.findById(id);
        if(existingExpense.isEmpty())
            return Optional.empty();

        Expense updatedExpense = existingExpense.get();
        updatedExpense.setSum(expense.getSum());
        updatedExpense.setCategory(expense.getCategory());
        updatedExpense.setPaymentMethod(expense.getPaymentMethod());
        updatedExpense.setDate(expense.getDate());
        updatedExpense.setInfo(expense.getInfo());

        return Optional.of(expenseRepository.save(updatedExpense));
    }

    @Override
    public void saveExp(Expense expense) {
        expenseRepository.save(expense);
    }

    @Override
    public double getTotalIncome() {
        List<Income> incomes = incomeRepository.findAll();
        double sum = 0L;
        for (Income inc : incomes) {
            sum += inc.getSum();
        }
        return sum;
    }

    @Override
    public double getTotalExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        double sum = 0L;
        for (Expense exp : expenses) {
            sum += exp.getSum();
        }
        return sum;
    }

    @Override
    public double getBalance() {
        return getTotalIncome() - getTotalExpenses();
    }
}

