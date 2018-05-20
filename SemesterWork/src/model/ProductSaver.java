package model;

public class ProductSaver {

	private static Product product;
	private static boolean update;

	public static Product getProduct() {
		return product;
	}

	public static void setProduct(Product product) {
		ProductSaver.product = product;
	}

	public static boolean isUpdate() {
		return update;
	}

	public static void setUpdate(boolean update) {
		ProductSaver.update = update;
	}
}
