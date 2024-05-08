package javau9.budget.controllers;

import jakarta.validation.Valid;
import javau9.budget.models.Expense;
import javau9.budget.models.Income;
import javau9.budget.services.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class BudgetHomeController {

    @Autowired
    BudgetService budgetService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("allIncomeslist", budgetService.getIncomeList());
        model.addAttribute("allExpenseslist", budgetService.getExpensesList());
        double totalIncome = budgetService.getTotalIncome();
        double totalExpenses = budgetService.getTotalExpenses();
        model.addAttribute("totalIncome", totalIncome);
        model.addAttribute("totalExpenses", totalExpenses);
        return "index";
    }

    @GetMapping("/incomeList")
    public String getIncomeList(Model model) {
        model.addAttribute("allIncomeslist", budgetService.getIncomeList());
        double totalIncome = budgetService.getTotalIncome();
        model.addAttribute("totalIncome", totalIncome);
        return "incomeList";
    }

    @GetMapping("/addNewIncome")
    public String addNewIncome(Model model) {
        Income income = new Income();
        model.addAttribute("income", income);
        return "newIncome";
    }

    @PostMapping("/saveInc")
    public String saveIncome(@ModelAttribute("income") Income income) {
        budgetService.saveInc(income);
        return "redirect:/";
    }

    @GetMapping("/findIncome")
    public String findIncome() {
        return "findIncome";
    }

    @GetMapping("/getIncome")
    public String getIncomeById(@RequestParam("id") Long id, Model model) {
        try {
            Optional<Income> income = budgetService.getIncomeById(id);
            if (income.isPresent()) {
                model.addAttribute("income", income.get());
                return "incomeDetails";
            } else {
                model.addAttribute("error", "Income not found!");
                return "findIncome";
            }
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred: " + e.getMessage());
            return "findIncome";
        }
    }

    // 1. first edit income
    @GetMapping("/editIncome/{id}")
    public String showUpdateIncomeForm(@PathVariable("id") Long id, Model model) {
        Optional<Income> income = budgetService.getIncomeById(id);
        if (income.isPresent()) {
            model.addAttribute("income", income.get());
            return "updateIncome";
        } else {
            model.addAttribute("error", "Income not found");
            return "redirect:/incomeList";
        }
    }

    // 2. then update income
    // persists the updated entity in the database
    @PostMapping("/updateIncome/{id}") // persists the updated entity in the database
    public String updateIncome(@PathVariable("id") Long id, @Valid Income income,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            income.setId(id);
            return "updateIncome";
        }
        budgetService.saveInc(income);
        return "redirect:/";
    }

    @GetMapping("/deleteIncome/{id}")
    public String deleteIncome(@PathVariable(value = "id") Long id) {
        budgetService.removeIncome(id);
        return "redirect:/";
    }

    @GetMapping("/expensesList")
    public String getExpensesList(Model model) {
        model.addAttribute("allExpenseslist", budgetService.getExpensesList());
        double totalExpenses = budgetService.getTotalExpenses();
        model.addAttribute("totalExpenses", totalExpenses);
        return "expensesList";
    }

    @GetMapping("/addNewExpense")
    public String addNewExpense(Model model) {
        Expense expense = new Expense();
        model.addAttribute("expense", expense);
        return "newExpense";
    }

    @PostMapping("/saveExp")
    public String saveExpense(@ModelAttribute("expense") Expense expense) {
        budgetService.saveExp(expense);
        return "redirect:/";
    }

    @GetMapping("/findExpense")
    public String findExpense() {
        return "findExpense";
    }

    @GetMapping("/getExpense")
    public String getExpenseById(@RequestParam("id") Long id, Model model) {
        try {
            Optional<Expense> expense = budgetService.getExpenseById(id);
            if (expense.isPresent()) {
                model.addAttribute("expense", expense.get());
                return "expenseDetails";
            } else {
                model.addAttribute("error", "Expense not found!");
                return "findExpense";
            }
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred: " + e.getMessage());
            return "findExpense";
        }
    }

    // 1. first edit expense
    @GetMapping("/editExpense/{id}")
    public String showUpdateExpenseForm(@PathVariable("id") Long id, Model model) {
        Optional<Expense> expense = budgetService.getExpenseById(id);
        if (expense.isPresent()) {
            model.addAttribute("expense", expense.get());
            return "updateExpense";
        } else {
            model.addAttribute("error", "Expense not found");
            return "redirect:/expenseList";
        }
    }

    // 2. then update expense
    // persists the updated entity in the database
    @PostMapping("/updateExpense/{id}")
    public String updateExpense(@PathVariable("id") Long id, @Valid Expense expense,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            expense.setId(id);
            return "updateExpense";
        }
        budgetService.saveExp(expense);
        return "redirect:/";
    }

    @GetMapping("/deleteExpense/{id}")
    public String deleteExpense(@PathVariable(value = "id") Long id) {
        budgetService.removeExpense(id);
        return "redirect:/";
    }

    @GetMapping("/balance")
    public String getBalance(Model model) {
        double balance = budgetService.getBalance();
        model.addAttribute("balance", balance);
        return "balance";
    }

    @GetMapping("/filterDates")
    public String filterByDates(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                Model model) {
        List<Income> filteredIncomes = budgetService.getIncomesBetweenDates(startDate, endDate);
        List<Expense> filteredExpenses = budgetService.getExpensesBetweenDates(startDate, endDate);

        model.addAttribute("allIncomeslist", filteredIncomes);
        model.addAttribute("allExpenseslist", filteredExpenses);
        model.addAttribute("totalIncome", filteredIncomes.stream().mapToDouble(Income::getSum).sum());
        model.addAttribute("totalExpenses", filteredExpenses.stream().mapToDouble(Expense::getSum).sum());
        return "index";
    }
}
