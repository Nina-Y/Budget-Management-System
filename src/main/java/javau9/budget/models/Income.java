package javau9.budget.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double sum;
    private String category; // 1 - salary, 2 - rental income, 3 - child benefit, 4 - other
    private LocalDate date;
    private String info;

    public Income(double sum, String category, LocalDate date, String info) {
        this.sum = sum;
        this.category = category;
        this.date = date;
        this.info = info;
    }

    public Income() {} // for Hibernate

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
        return "Income{" +
                "id=" + id +
                ", sum=" + sum +
                ", category=" + category +
                ", date=" + date +
                ", info='" + info + '\'' +
                '}';
    }
}

