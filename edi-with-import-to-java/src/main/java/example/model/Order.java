package example.model;

import java.util.List;

public class Order {

    private Header header;
    private List<OrderItem> orderItems;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        StringBuilder desc = new StringBuilder();

        desc.append("Order Header: \n");
        desc.append(header);
        desc.append("Order Items: \n");
        for (int i = 0; i < orderItems.size(); i++) {
            desc.append("\t" + "(").append(i).append("): ").append(orderItems.get(i)).append("\n");
        }

        return desc.toString();
    }
}
