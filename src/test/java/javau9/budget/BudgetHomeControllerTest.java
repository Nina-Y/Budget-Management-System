package javau9.budget;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javau9.budget.controllers.BudgetHomeController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import javau9.budget.services.BudgetService;
import javau9.budget.models.Income;
import java.util.Arrays;
import java.util.Optional;

@WebMvcTest(BudgetHomeController.class)
public class BudgetHomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BudgetService budgetService;

    private Income income;
    private final double TOTAL_INCOME = 10000.0;
    private final double TOTAL_EXPENSES = 5000.0;

    @BeforeEach
    public void setup() {
        income = new Income();
        income.setId(1L);
        income.setSum(5000.0);
        income.setCategory("Salary");

        when(budgetService.getIncomeList()).thenReturn(Arrays.asList(income));
        when(budgetService.getTotalIncome()).thenReturn(TOTAL_INCOME);
        when(budgetService.getTotalExpenses()).thenReturn(TOTAL_EXPENSES);
    }

    @Test
    public void testViewHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allIncomeslist", "allExpenseslist", "totalIncome", "totalExpenses"))
                .andExpect(view().name("index"));
    }

    @Test
    public void testGetIncomeList() throws Exception {
        mockMvc.perform(get("/incomeList"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allIncomeslist", "totalIncome"))
                .andExpect(view().name("incomeList"));
    }

    @Test
    public void testAddNewIncome() throws Exception {
        mockMvc.perform(get("/addNewIncome"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("income"))
                .andExpect(view().name("newIncome"));
    }

    @Test
    public void testSaveIncome() throws Exception {
        mockMvc.perform(post("/saveInc")
                        .flashAttr("income", income))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
        verify(budgetService).saveInc(any(Income.class));
    }

    @Test
    public void testGetIncomeByIdFound() throws Exception {
        when(budgetService.getIncomeById(1L)).thenReturn(Optional.of(income));

        mockMvc.perform(get("/getIncome").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("income"))
                .andExpect(view().name("incomeDetails"));
    }

    @Test
    public void testGetIncomeByIdNotFound() throws Exception {
        when(budgetService.getIncomeById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/getIncome").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("error"))
                .andExpect(view().name("findIncome"));
    }

    @Test
    public void testShowUpdateIncomeForm() throws Exception {
        when(budgetService.getIncomeById(1L)).thenReturn(Optional.of(income));

        mockMvc.perform(get("/editIncome/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("income"))
                .andExpect(view().name("updateIncome"));
    }
}

