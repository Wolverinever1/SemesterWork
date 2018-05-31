package model;

import java.util.LinkedList;
import java.util.List;

public class ProductSaver {

	private static Product product;
	private static boolean update;
	private static List<Integer> id = new LinkedList<>();

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

	public static List<Integer> getId() {
		return id;
	}

	public static void setId(List<Integer> id) {
		ProductSaver.id = id;
	}
}
