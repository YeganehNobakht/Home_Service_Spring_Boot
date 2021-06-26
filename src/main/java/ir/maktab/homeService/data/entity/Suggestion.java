package ir.maktab.homeService.data.entity;

import ir.maktab.homeService.data.entity.enums.SuggestionStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Suggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double price;
    private String workDescription;
    private Integer durationOfWork;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Enumerated(EnumType.STRING)
    private SuggestionStatus suggestionStatus;

    @ManyToOne
    @JoinColumn(name = "customerorder_suggestion", nullable = false, foreignKey = @ForeignKey(name = "suggestion_order_fk"))
    private CustomerOrder customerOrder;

    @ManyToOne
    @JoinColumn(name = "specialist_suggestion", nullable = false, foreignKey = @ForeignKey(name = "suggestion_specialist_fk"))
    private Specialist specialist;

    public Integer getId() {
        return id;
    }

    public Suggestion setId(Integer id) {
        this.id = id;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public Suggestion setPrice(Double price) {
        this.price = price;
        return this;
    }

    public String getWorkDescription() {
        return workDescription;
    }

    public Suggestion setWorkDescription(String workDescription) {
        this.workDescription = workDescription;
        return this;
    }

    public Integer getDurationOfWork() {
        return durationOfWork;
    }

    public Suggestion setDurationOfWork(Integer durationOfWork) {
        this.durationOfWork = durationOfWork;
        return this;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Suggestion setStartTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public Suggestion setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
        return this;
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    public Suggestion setSpecialist(Specialist specialist) {
        this.specialist = specialist;
        return this;
    }

    public SuggestionStatus getSuggestionStatus() {
        return suggestionStatus;
    }

    public Suggestion setSuggestionStatus(SuggestionStatus suggestionStatus) {
        this.suggestionStatus = suggestionStatus;
        return this;
    }
}