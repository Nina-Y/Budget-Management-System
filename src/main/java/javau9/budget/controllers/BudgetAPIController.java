package javau9.budget.controllers;

import javau9.budget.models.Expense;
import javau9.budget.models.Income;
import javau9.budget.services.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class BudgetAPIController {

    @Autowired
    BudgetService budgetService;

    @GetMapping("/viewIncomeList") // localhost:portas/api/incomes; eg. http://localhost:8080/swagger-ui/index.html
    public List<Income> viewIncomeList() {
        return budgetService.getIncomeList();
    }

    @GetMapping("/viewExpensesList") // localhost:portas/api/incomes
    public List<Expense> viewExpensesList() {
        return budgetService.getExpensesList();
    }

    @PostMapping("/incomeAdd")
    public ResponseEntity<Income> addIncome(@RequestBody Income income) {
        Income savedIncome = budgetService.addIncome(income);
        return ResponseEntity.ok(savedIncome);
    }

    @DeleteMapping("/incomeDel/{id}")
    public ResponseEntity<Boolean> deleteIncome(@PathVariable Long id) {
        boolean deleted = budgetService.removeIncome(id);
        if (deleted) {
            return ResponseEntity.ok().build(); // Grąžinamas 200 OK
        } else {
            return ResponseEntity.notFound().build(); // Grąžinamas 404 Not Found
        }
    }

    @GetMapping("/income/{id}")
    public ResponseEntity<Income> getIncomeByID(@PathVariable Long id) {
        return ResponseEntity.of(budgetService.getIncomeById(id));
    }

    @PutMapping("/incomeUpd/{id}")
    public ResponseEntity<Income> updateIncome(@PathVariable Long id, @RequestBody Income income) {
        return ResponseEntity.of(budgetService.updateIncome(id, income));
    }

    @PostMapping("/expenseAdd")
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense) {
        Expense savedExpense = budgetService.addExpense(expense);
        return ResponseEntity.ok(savedExpense);
    }

    @DeleteMapping("/expenseDel/{id}")
    public ResponseEntity<Boolean> deleteExpense(@PathVariable Long id) {
        boolean deleted = budgetService.removeExpense(id);
        if (deleted) {
            return ResponseEntity.ok().build(); // Grąžinamas 200 OK
        } else {
            return ResponseEntity.notFound().build(); // Grąžinamas 404 Not Found
        }
    }

    @GetMapping("/expense/{id}")
    public ResponseEntity<Expense> getExpenseByID(@PathVariable Long id) {
        return ResponseEntity.of(budgetService.getExpenseById(id));
    }

    @PutMapping("/expenseUpd/{id}")
    public ResponseEntity<Expense> updateIncome(@PathVariable Long id, @RequestBody Expense expense) {
        return ResponseEntity.of(budgetService.updateExpense(id, expense));
    }
}

