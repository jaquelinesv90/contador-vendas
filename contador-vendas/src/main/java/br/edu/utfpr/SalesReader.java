package br.edu.utfpr;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class SalesReader {

    private final List<Sale> sales;

    public SalesReader(String salesFile) {

        final var dataStream = ClassLoader.getSystemResourceAsStream(salesFile);

        if (dataStream == null) {
            throw new IllegalStateException("File not found or is empty");
        }

        final var builder = new CsvToBeanBuilder<Sale>(new InputStreamReader(dataStream, StandardCharsets.UTF_8));

        sales = builder
                .withType(Sale.class)
                .withSeparator(';')
                .build()
                .parse();
    }

    public BigDecimal totalOfCompletedSales() {
        final var completedSales = sales.stream()
                .filter(sale -> sale.getStatus().equals(Sale.Status.COMPLETED))
                .count();
        return new BigDecimal(completedSales);
    }

    public BigDecimal totalOfCancelledSales() {
        final var cancelledSales = sales.stream()
                .filter(sale -> sale.getStatus().equals(Sale.Status.CANCELLED))
                .count();

        return new BigDecimal(cancelledSales);
    }

    public Optional<Sale> mostRecentCompletedSale() {
        return sales.stream()
                .filter(sale -> sale.getStatus().equals(Sale.Status.COMPLETED))
                .max(Comparator.comparing(s -> s.getSaleDate().toEpochDay()));
    }

    public long daysBetweenFirstAndLastCancelledSale() {
        final var listCancelledSalesFirst = sales.stream().filter(sale -> sale.getStatus().equals(Sale.Status.CANCELLED))
                .map(Sale::getSaleDate)
                .min(LocalDate::compareTo)
                .get();

        final var listCancelledSalesLast = sales.stream().filter(sale -> sale.getStatus().equals(Sale.Status.CANCELLED))
                .map(Sale::getSaleDate)
                .max(LocalDate::compareTo)
                .get();

        return ChronoUnit.DAYS.between(listCancelledSalesFirst,listCancelledSalesLast);
    }

    public BigDecimal totalCompletedSalesBySeller(String sellerName) {
        final var completedSalesBySeller = sales.stream()
                .filter(s -> s.getStatus().equals(Sale.Status.CANCELLED))
                .filter(p -> p.getSeller().equals(sellerName))
                .collect(Collectors.counting());
        return new BigDecimal(completedSalesBySeller);
    }

    public long countAllSalesByManager(String managerName) {
        final var total = sales.stream()
                .filter(sale -> sale.getManager().equals(managerName))
                .count();
        return total;
    }

    public BigDecimal totalSalesByStatusAndMonth(Sale.Status status, Month... months) {

        final var total = sales.stream()
                .filter(sale -> sale.getStatus().equals(status))
                .filter(e -> e.getSaleDate().getMonth().equals(months))
                .count();

        return new BigDecimal(total);
    }

    public Map<String, Long> countCompletedSalesByDepartment() {
        final var completedSalesByDepartment = sales.stream()
                .filter(sale -> sale.getStatus().equals(Sale.Status.COMPLETED))
                .collect(Collectors.groupingBy(p -> p.getDepartment(), Collectors.counting()));

        return completedSalesByDepartment;
    }

    public Map<Integer, Map<String, Long>> countCompletedSalesByPaymentMethodAndGroupingByYear() {

        return Map.of();
    }

    public Map<String, BigDecimal> top3BestSellers() {

       final var top3 = sales.stream()
               .collect(Collectors.groupingBy(p -> p.getNumber()))
               .entrySet()
               .stream()
               .sorted(Comparator.comparing(Map.Entry::getKey))
               .limit(3)
               .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getValue,(e1,e2) -> e1,LinkedHashMap::new));

       top3.forEach((key,value) -> System.out.println(key));

       return Map.of();
    }
}
