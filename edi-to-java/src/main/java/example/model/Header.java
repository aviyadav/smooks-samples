package example.model;

import java.math.BigDecimal;
import java.util.Date;

public class Header {

    private String orderId;
    private long orderStatus;
    private BigDecimal netAmount;
    private BigDecimal totalAmount;
    private BigDecimal tax;
    private Date date;
    private Customer customer;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(long orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        StringBuilder desc = new StringBuilder();

        desc.append("\tCustomer: ").append(customer).append("\n");
        desc.append("\tDate: ").append(date).append("\n");
        desc.append("\tDetails: ID=").append(orderId).append(", Status=").append(orderStatus).append(", Total=").append(totalAmount).append("\n");

        return desc.toString();
    }
}
