import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        File listBin = new File("./basket.bin");

        String[] products = {"Витамины", "Хлеб", "Лимонад", "Курица", "Шоколад"};
        int[] prices = {50, 14, 80, 100, 90};

        Basket basket = new Basket(products, prices);


        if (listBin.exists()) {
            try {
                basket = Basket.loadFromBinFile(listBin);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


        System.out.println("Список возможных товаров :");
        for (int i = 1; i <= basket.size(); i++) {
            System.out.println(i + ". " + basket.getProduct(i) + " " + basket.getPrice(i) + " руб/шт");
        }
        while (true) {
            int productNumber;
            int productCount;
            System.out.println("Выберите товар и количество или введите `end` для завершения");
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                System.out.println("Ваша корзина:");
                basket.printCart();

                break;
            } else {
                String[] parts = input.split(" ");
                if (parts.length != 2) {
                    System.out.println("Введите номер продукта и количество через пробел");
                    continue;
                }
                try {
                    productNumber = Integer.parseInt(parts[0]) - 1;
                    if (productNumber < 0 || products.length < productNumber - 1) {
                        System.out.println("Не верно введен номер продуктов. Введите номер продуктов из предложенного списка");
                        continue;
                    }
                    productCount = Integer.parseInt(parts[1]);
                    if (productCount < 0) {
                        System.out.println("Количество продуктов не может быть отрицательным числом или нулем");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Не верно введены данные. Введите номер продукта и количество продуктов только числами");
                    continue;
                }
                basket.addToCart(productNumber, productCount);
                basket.saveBin(listBin);
            }
        }
        basket.saveTxt(new File("basket.txt"));
    }
}