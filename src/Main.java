import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static String[] products = {"Витамины", "Хлеб", "Лимонад", "Курица", "Шоколад"};
    static int[] prices = {50, 14, 80, 100, 90};
    static File list = new File("bask.json");

    public static void main(String[] args) throws IOException {

        File list1 = new File("./Basket/basket.txt");

        Basket basket;

        if (list.exists()) {
            basket = Basket.loadFromJSONFile(list);
        } else {
            basket = new Basket(products, prices);
        }


        ClientLog log = new ClientLog();

        while (true) {
            showPrice();

            System.out.println("Выберите товар и количество или введите `end` для завершения");
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                log.exportAsCSV(new File("log.csv"));
                break;
            }
            String[] parts = input.split(" ");
            int productNumber = Integer.parseInt(parts[0]) - 1;
            if (productNumber < 0 || products.length < productNumber - 1) {
                System.out.println("Не верно введен номер продуктов. Введите номер продуктов из предложенного списка");
                continue;
            }
            int productCount = Integer.parseInt(parts[1]);
            if (productCount < 0) {
                System.out.println("Количество продуктов не может быть отрицательным числом или нулем");
                continue;
            }
            basket.addToCart(productNumber, productCount);
            log.log(productNumber, productCount);
            basket.saveJSON(list);

        }
        basket.printCart();
    }

    public static void showPrice() {
        System.out.println("Список возможных товаров для покупки:");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + " " + products[i] + " " + prices[i] + " руб/шт");
        }
    }
}

