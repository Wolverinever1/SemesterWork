package model;

public class OrdersCustomerProducts {
	private String orderId;
	private String customerName;
	private String orderDate;
	private String model;
	private String productName;
	private String count;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public OrdersCustomerProducts(String orderId, String orderDate, String customerName, String model,
			String productName, String count) {
		this.orderId = orderId;
		this.customerName = customerName;
		this.orderDate = orderDate;
		this.model = model;
		this.productName = productName;
		this.count = count;
	}

}
