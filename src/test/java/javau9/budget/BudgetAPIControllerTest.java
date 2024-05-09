package javau9.budget;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javau9.budget.controllers.BudgetAPIController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import javau9.budget.models.Expense;
import javau9.budget.models.Income;
import javau9.budget.services.BudgetService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

@WebMvcTest(BudgetAPIController.class)
public class BudgetAPIControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BudgetService budgetService;

    @Autowired
    private ObjectMapper objectMapper;

    private Income income;
    private Expense expense;

    @BeforeEach
    public void setup() {
        income = new Income();
        income.setId(1L);
        income.setSum(5000.0);
        income.setCategory("Salary");

        expense = new Expense();
        expense.setId(1L);
        expense.setSum(1500.0);
        expense.setCategory("Rent");
    }

    @Test
    public void testViewIncomeList() throws Exception {
        when(budgetService.getIncomeList()).thenReturn(List.of(income));
        mockMvc.perform(get("/viewIncomeList"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    public void testViewExpensesList() throws Exception {
        when(budgetService.getExpensesList()).thenReturn(List.of(expense));
        mockMvc.perform(get("/viewExpensesList"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    public void testAddIncome() throws Exception {
        when(budgetService.addIncome(any(Income.class))).thenReturn(income);
        mockMvc.perform(post("/incomeAdd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(income)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testDeleteIncome() throws Exception {
        when(budgetService.removeIncome(1L)).thenReturn(true);
        mockMvc.perform(delete("/incomeDel/{id}", 1))
                .andExpect(status().isOk());

        when(budgetService.removeIncome(1L)).thenReturn(false);
        mockMvc.perform(delete("/incomeDel/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetIncomeByID() throws Exception {
        when(budgetService.getIncomeById(1L)).thenReturn(Optional.of(income));
        mockMvc.perform(get("/income/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        when(budgetService.getIncomeById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/income/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateIncome() throws Exception {
        when(budgetService.updateIncome(eq(1L), any(Income.class))).thenReturn(Optional.of(income));
        mockMvc.perform(put("/incomeUpd/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(income)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        when(budgetService.updateIncome(eq(1L), any(Income.class))).thenReturn(Optional.empty());
        mockMvc.perform(put("/incomeUpd/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(income)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddExpense() throws Exception {
        when(budgetService.addExpense(any(Expense.class))).thenReturn(expense);
        mockMvc.perform(post("/expenseAdd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expense)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testDeleteExpense() throws Exception {
        when(budgetService.removeExpense(1L)).thenReturn(true);
        mockMvc.perform(delete("/expenseDel/{id}", 1))
                .andExpect(status().isOk());

        when(budgetService.removeExpense(1L)).thenReturn(false);
        mockMvc.perform(delete("/expenseDel/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetExpenseByID() throws Exception {
        when(budgetService.getExpenseById(1L)).thenReturn(Optional.of(expense));
        mockMvc.perform(get("/expense/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        when(budgetService.getExpenseById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/expense/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateExpense() throws Exception {
        when(budgetService.updateExpense(eq(1L), any(Expense.class))).thenReturn(Optional.of(expense));
        mockMvc.perform(put("/expenseUpd/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expense)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        when(budgetService.updateExpense(eq(1L), any(Expense.class))).thenReturn(Optional.empty());
        mockMvc.perform(put("/expenseUpd/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expense)))
                .andExpect(status().isNotFound());
    }
}

