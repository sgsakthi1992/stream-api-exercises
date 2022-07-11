package demo.streamapi.exercises;

import demo.streamapi.repos.CustomerRepo;
import org.springframework.stereotype.Service;
import demo.streamapi.models.Order;
import demo.streamapi.models.Product;
import demo.streamapi.repos.OrderRepo;
import demo.streamapi.repos.ProductRepo;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class Exercises {
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;
    private final CustomerRepo customerRepo;

    public Exercises(ProductRepo productRepo, OrderRepo orderRepo, CustomerRepo customerRepo) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
        this.customerRepo = customerRepo;
    }

    public void exercise1() {
        //Exercise 1 — Obtain a list of products belongs to category “Books” with price > 100
        var books = productRepo.findAll().stream()
                .filter(product -> product.getCategory().equalsIgnoreCase("Books"))
                .filter(product -> product.getPrice() > 100)
                .collect(Collectors.toList());
        books.forEach(System.out::println);
    }

    public void exercise2() {
        //Exercise 2 — Obtain a list of order with products belong to category “Baby”
        var babyProducts = orderRepo.findAll().stream()
                .filter(order -> order.getProducts().stream()
                        .anyMatch(product -> product.getCategory().equalsIgnoreCase("Baby")))
                .collect(Collectors.toList());
        babyProducts.forEach(System.out::println);
    }

    public void exercise3() {
        //Exercise 3 — Obtain a list of product with category = “Toys” and then apply 10% discount
        var toys = productRepo.findAll().stream()
                .filter(product -> product.getCategory().equalsIgnoreCase("Toys"))
                .map(product -> product.withPrice(product.getPrice() * .9))
                .collect(Collectors.toList());
        toys.forEach(System.out::println);
    }

    public void exercise4() {
        //Exercise 4 — Obtain a list of products ordered by customer of tier 2 between 01-Feb-2021 and 01-Apr-2021
        var products = orderRepo.findAll().stream()
                .filter(order -> order.getOrderDate().compareTo(LocalDate.of(2021, 2, 1)) >= 0)
                .filter(order -> order.getOrderDate().isBefore(LocalDate.of(2021, 4, 1)))
                .filter(order -> order.getCustomer().getTier() == 2)
                .flatMap(order -> order.getProducts().stream())
                .distinct()
                .collect(Collectors.toList());
        products.forEach(System.out::println);
    }

    public void exercise5() {
        //Exercise 5 — Get the cheapest products of “Books” category
        var cheapestProduct = productRepo.findAll().stream()
                .filter(product -> product.getCategory().equalsIgnoreCase("Books"))
                .min(Comparator.comparing(Product::getPrice));
        System.out.println(cheapestProduct);
    }

    public void exercise6() {
        //Exercise 6 — Get the 3 most recent placed order
        var latestOrders = orderRepo.findAll().stream()
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .limit(3)
                .collect(Collectors.toList());
        latestOrders.forEach(System.out::println);
    }

    public void exercise7() {
        //Exercise 7 — Get a list of orders which were ordered on 15-Mar-2021, log the order records to the console and then return its product list
        var products = orderRepo.findAll().stream()
                .filter(order -> order.getOrderDate().isEqual(LocalDate.of(2021, 3, 15)))
                .peek(System.out::println)
                .flatMap(order -> order.getProducts().stream())
                .distinct()
                .collect(Collectors.toList());
        products.forEach(System.out::println);
    }

    public void exercise8() {
        //Exercise 8 — Calculate total lump sum of all orders placed in Feb 2021
        var total = orderRepo.findAll().stream()
                .filter(order -> order.getOrderDate().getMonthValue() == 2 && order.getOrderDate().getYear() == 2021)
                .flatMap(order -> order.getProducts().stream())
                .mapToDouble(Product::getPrice)
                .sum();
        System.out.println(total);
    }

    public void exercise9() {
        //Exercise 9 — Calculate order average payment placed on 14-Mar-2021
        var average = orderRepo.findAll().stream()
                .filter(order -> order.getOrderDate().isEqual(LocalDate.of(2021, 3, 15)))
                .flatMap(order -> order.getProducts().stream())
                .mapToDouble(Product::getPrice)
                .average().orElse(0.0);
        System.out.println(average);
    }

    public void exercise10() {
        //Exercise 10 — Obtain a collection of statistic figures (i.e. sum, average, max, min, count) for all products of category “Books”
        var statistics = productRepo.findAll().stream()
                .filter(product -> product.getCategory().equalsIgnoreCase("Books"))
                .mapToDouble(Product::getPrice)
                .summaryStatistics();
        System.out.println(statistics);
    }

    public void exercise11() {
        //Exercise 11 — Obtain a data map with order id and order’s product count
        var orders = orderRepo.findAll().stream()
                .collect(Collectors.toMap(Order::getId, order -> order.getProducts().size()));
        orders.forEach((k, v) -> System.out.println(k + "-" + v));
    }

    public void exercise12() {
        //Exercise 12 — Produce a data map with order records grouped by customer
        var data = orderRepo.findAll().stream()
                .collect(Collectors.groupingBy(Order::getCustomer));
        data.forEach((k, v) -> System.out.println(k + "-" + v));
    }

    public void exercise13() {
        //Exercise 13 — Produce a data map with order record and product total sum
        var data = orderRepo.findAll().stream()
                .collect(Collectors.toMap(Function.identity(),
                        order -> order.getProducts().stream().mapToDouble(Product::getPrice).sum()));
        data.forEach((k, v) -> System.out.println(k + "-" + v));
    }

    public void exercise14() {
        //Exercise 14 — Obtain a data map with list of product name by category
        var data = productRepo.findAll().stream()
                .collect(Collectors.groupingBy(Product::getCategory, Collectors.mapping(Product::getName, Collectors.toList())));
        data.forEach((k, v) -> System.out.println(k + "-" + v));
    }

    public void exercise15() {
        //Exercise 15 — Get the most expensive product by category
        var data = productRepo.findAll().stream()
                .collect(Collectors.groupingBy(Product::getCategory, Collectors.maxBy(Comparator.comparing(Product::getPrice))));
        data.forEach((k, v) -> System.out.println(k + "-" + v));
    }
}
