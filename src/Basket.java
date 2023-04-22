import com.google.gson.Gson;

import java.io.*;

public class Basket implements Serializable {
    private static final long serialVersionUID = 1L;
    private String[] products;
    private int[] prices;
    private long[] cart;

    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        this.cart = new long[products.length];
    }

    public static Basket loadFromJSONFile(File file) throws IOException {
        Basket basket;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            Gson gson = new Gson();
            basket = gson.fromJson(builder.toString(), Basket.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return basket;
    }

    public void addToCart(int productNum, int amount) {
        cart[productNum] += amount;
    }

    public void printCart() {
        long sum = 0;
        System.out.println("Ваша корзина:");
        for (int i = 0; i < products.length; i++) {
            if (cart[i] != 0) {
                long sumProduct = cart[i] * prices[i];
                System.out.println((i + 1) + ". " + products[i] + " "
                        + cart[i] + " шт по " + prices[i] + " руб/шт - " + sumProduct + " руб в сумме");
                sum += sumProduct;
            }
        }
        System.out.println("Итого " + sum + " руб");
    }

    public void saveJSON(File file) throws IOException {
        try (PrintWriter out = new PrintWriter(file)) {
            Gson gson = new Gson();
            String json = gson.toJson(this);
            out.println(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public long size() {
        return cart.length;
    }

    public String getProduct(int num) {
        return products[num - 1];
    }

    public int getPrice(int num) {
        return prices[num - 1];
    }
}
