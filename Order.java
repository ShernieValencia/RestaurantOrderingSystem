import java.util.ArrayList;

public class Order {
    private int orderId;
    private ArrayList<CartItem> items;
    private String status;

    public Order(int orderId, ArrayList<CartItem> items) {
        this.orderId = orderId;
        this.items = items;
        this.status = "Pending";
    }

    public int getOrderId() {
        return orderId;
    }

    public ArrayList<CartItem> getItems() {
        return items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotal() {
        double total = 0;

        for (CartItem item : items) {
            total += item.getSubtotal();
        }

        return total;
    }
}