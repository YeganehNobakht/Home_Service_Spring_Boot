package ir.maktab.homeService.data.entity;

import javax.persistence.*;

@Entity
public class CustomerComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String comment;
    private Double score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_comment", nullable = false, foreignKey = @ForeignKey(name = "customer_comment_fk"))
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialist_comment", nullable = false, foreignKey = @ForeignKey(name = "specialist_comment_fk"))
    private Specialist specialist;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private CustomerOrder customerOrder;

    public Integer getId() {
        return id;
    }

    public CustomerComment setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public CustomerComment setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Double getScore() {
        return score;
    }

    public CustomerComment setScore(Double score) {
        this.score = score;
        return this;
    }

    public Customer getCustomer() {
        return customer;
    }

    public CustomerComment setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    public CustomerComment setSpecialist(Specialist specialist) {
        this.specialist = specialist;
        return this;
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public CustomerComment setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
        return this;
    }
}
