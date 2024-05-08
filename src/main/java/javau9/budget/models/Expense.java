package javau9.budget.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Sum is mandatory")
    private double sum;
    @NotBlank(message = "Category is mandatory")
    private String category; // 1 - food, 2 - utility bills, 3 - insurance, 4 - other
    @NotBlank(message = "Payment method is mandatory")
    private String paymentMethod; // 1 - cash, 2 - card, 3 - bankTransfer

    private LocalDate date;
    private String info;

    public Expense(double sum, String category, String paymentMethod, LocalDate date, String info) {
        this.sum = sum;
        this.category = category;
        this.paymentMethod = paymentMethod;
        this.date = date;
        this.info = info;
    }

    public Expense() {
    }

    //<editor-fold desc="Getters/Setters">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    //</editor-fold>

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", sum=" + sum +
                ", category=" + category +
                ", paymentMethod=" + paymentMethod +
                ", localDate=" + date +
                ", info='" + info + '\'' +
                '}';
    }
}

