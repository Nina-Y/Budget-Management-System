package javau9.budget.services;

import javau9.budget.models.Expenses;
import javau9.budget.models.Income;
import javau9.budget.repositories.ExpensesRepository;
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
    ExpensesRepository expensesRepository;

    @Autowired
    public BudgetServiceImpl(IncomeRepository incomeRepository, ExpensesRepository expensesRepository) {
        this.incomeRepository = incomeRepository;
        this.expensesRepository = expensesRepository;
        //seedDummyDataIncome(); // после первого запуска закомментить, иначе каждый раз будет добавляться
        //seedDummyDataExpenses(); // -//-
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
        List<Expenses> expenses = new ArrayList<>();
        // expenses.add(new Expenses(100.0, "food", "cash", LocalDateTime.now(), "someInfo")); // as an option
        expenses.add(new Expenses(100.0, "food", "cash", LocalDate.parse("2024-02-15"), "someInfo"));
        expenses.add(new Expenses(300.0, "utility bills", "bank transfer", LocalDate.parse("2024-02-15"), "someInfo"));
        expenses.add(new Expenses(50.0, "insurance", "bank transfer", LocalDate.parse("2024-02-15"), "someInfo"));
        expenses.add(new Expenses(100.0, "insurance", "bank transfer", LocalDate.parse("2024-02-15"), "someInfo"));
        expenses.add(new Expenses(100.0, "food", "card", LocalDate.parse("2024-02-15"), "someInfo"));
        expensesRepository.saveAll(expenses); // don't forget to add
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
        return true; // ?
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
    public List<Expenses> getExpensesList() {
        return expensesRepository.findAll();
    }

    @Override
    public Expenses addExpense(Expenses expense) {
        return expensesRepository.save(expense);
    }

    @Override
    public Boolean removeExpense(Long id) {
        expensesRepository.deleteById(id);
        return true; // ?
    }

    @Override
    public Optional<Expenses> getExpenseById(Long id) {
        return expensesRepository.findById(id);
    }

    @Override
    public Optional<Expenses> updateExpense(Long id, Expenses expense) {
        Optional<Expenses> existingExpense = expensesRepository.findById(id);
        if(existingExpense.isEmpty())
            return Optional.empty();

        Expenses updatedExpense = existingExpense.get();
        updatedExpense.setSum(expense.getSum());
        updatedExpense.setCategory(expense.getCategory());
        updatedExpense.setPaymentMethod(expense.getPaymentMethod());
        updatedExpense.setDate(expense.getDate());
        updatedExpense.setInfo(expense.getInfo());

        return Optional.of(expensesRepository.save(updatedExpense));
    }

    @Override
    public void saveExp(Expenses expense) {
        expensesRepository.save(expense);
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
        List<Expenses> expenses = expensesRepository.findAll();
        double sum = 0L;
        for (Expenses exp : expenses) {
            sum += exp.getSum();
        }
        return sum;
    }

    @Override
    public double getBalance() {
        return getTotalIncome() - getTotalExpenses();
    }
}

