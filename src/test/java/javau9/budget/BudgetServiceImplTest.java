package javau9.budget;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import javau9.budget.models.Expense;
import javau9.budget.models.Income;
import javau9.budget.repositories.ExpenseRepository;
import javau9.budget.repositories.IncomeRepository;
import javau9.budget.services.BudgetServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BudgetServiceImplTest {

    @Mock
    private IncomeRepository incomeRepository;

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private BudgetServiceImpl budgetService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(incomeRepository.findAll()).thenReturn(new ArrayList<>());
        when(expenseRepository.findAll()).thenReturn(new ArrayList<>());
    }

    @Test
    public void testGetIncomeList() {
        List<Income> expectedIncomes = new ArrayList<>();
        expectedIncomes.add(new Income(2000.0, "salary", LocalDate.parse("2024-01-01"), "Company"));
        when(incomeRepository.findAll()).thenReturn(expectedIncomes);

        List<Income> actualIncomes = budgetService.getIncomeList();
        assertFalse(actualIncomes.isEmpty());
        assertEquals(expectedIncomes.size(), actualIncomes.size());
        assertEquals(expectedIncomes.get(0), actualIncomes.get(0));
    }

    @Test
    public void testAddIncome() {
        Income newIncome = new Income(1500.0, "freelance", LocalDate.parse("2024-02-01"), "Project");
        when(incomeRepository.save(any(Income.class))).thenReturn(newIncome);

        Income savedIncome = budgetService.addIncome(newIncome);
        assertNotNull(savedIncome);
        assertEquals(newIncome.getSum(), savedIncome.getSum());
    }

    @Test
    public void testRemoveIncome() {
        Long incomeId = 1L;
        doNothing().when(incomeRepository).deleteById(incomeId);
        Boolean result = budgetService.removeIncome(incomeId);

        assertTrue(result);
        verify(incomeRepository, times(1)).deleteById(incomeId);
    }

    @Test
    public void testGetIncomeById() {
        Optional<Income> expectedIncome = Optional.of(new Income(01500.0, "freelance", LocalDate.parse("2024-02-01"), "Project"));
        when(incomeRepository.findById(1L)).thenReturn(expectedIncome);

        Optional<Income> actualIncome = budgetService.getIncomeById(1L);
        assertTrue(actualIncome.isPresent());
        assertEquals(expectedIncome.get(), actualIncome.get());
    }

    @Test
    public void testUpdateIncome() {
        Income existingIncome = new Income(1500.0, "freelance", LocalDate.parse("2024-02-01"), "Project");
        Income updatedIncome = new Income(2000.0, "freelance updated", LocalDate.parse("2024-02-01"), "Project Updated");
        when(incomeRepository.findById(1L)).thenReturn(Optional.of(existingIncome));
        when(incomeRepository.save(any(Income.class))).thenReturn(updatedIncome);

        Optional<Income> result = budgetService.updateIncome(1L, updatedIncome);
        assertTrue(result.isPresent());
        assertEquals(updatedIncome.getCategory(), result.get().getCategory());
        assertEquals(updatedIncome.getSum(), result.get().getSum());
    }

    @Test
    public void testGetExpenseList() {
        List<Expense> expectedExpenses = new ArrayList<>();
        expectedExpenses.add(new Expense(300.0, "utilities", "cash", LocalDate.parse("2024-01-15"), "Electricity"));
        when(expenseRepository.findAll()).thenReturn(expectedExpenses);

        List<Expense> actualExpenses = budgetService.getExpensesList();
        assertFalse(actualExpenses.isEmpty());
        assertEquals(expectedExpenses.size(), actualExpenses.size());
        assertEquals(expectedExpenses.get(0), actualExpenses.get(0));
    }
}
