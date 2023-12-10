package br.edu.utfpr;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.Month;

public class Application {

    public static final String SELLER = "Rosangela Almeida";
    public static final String MANAGER = "Elenice Mendes";

    public Application() {
        startReading();
    }

    private void startReading() {

        final SalesReader reader = new SalesReader("dados.csv");

        final var totalOfCompletedSales = reader.totalOfCompletedSales();
        System.out.printf("O valor total de vendas completadas foi de %s%n", toCurrency(totalOfCompletedSales));

        final var totalOfCancelledSales = reader.totalOfCancelledSales();
        System.out.printf("O valor total de vendas canceladas foi de %s%n", toCurrency(totalOfCancelledSales));

        reader.mostRecentCompletedSale()
                .ifPresent(sale -> System.out.printf("Venda mais recente foi realizada em %s%n", sale.getSaleDate()));

        final var daysBetweenFirstAndLastSale = reader.daysBetweenFirstAndLastCancelledSale();
        System.out.printf("Se passaram %s dias entre a primeira e a ultima venda cancelada%n", daysBetweenFirstAndLastSale);

        final var totalSalesBySeller = reader.totalCompletedSalesBySeller(SELLER);
        System.out.printf("A vendedora %s totalizou %s em vendas%n", SELLER, toCurrency(totalSalesBySeller));

        final var countOfSalesByManager = reader.countAllSalesByManager(MANAGER);
        System.out.printf("A equipe do gerente %s realizou %s vendas%n", MANAGER, countOfSalesByManager);

        final var totalSalesByStatusAndMonth = reader.totalSalesByStatusAndMonth(Sale.Status.COMPLETED, Month.JULY, Month.SEPTEMBER);
        System.out.printf("As venda com o status no mes indicado somaram %s%n", toCurrency(totalSalesByStatusAndMonth));

        System.out.println("-------------------");
        System.out.println("Contagem de vendas por departamento\n");

        final var salesCountByDepartment = reader.countCompletedSalesByDepartment();
        salesCountByDepartment.forEach((key, value) -> System.out.printf("Departamento %s teve %s vendas", key, value).println());

        System.out.println("-------------------");
        System.out.println("Contagem de vendas por meio de pagamento agrupadas por ano\n");

        final var salesCountByPaymentMethodByYear = reader.countCompletedSalesByPaymentMethodAndGroupingByYear();
        salesCountByPaymentMethodByYear.keySet()
                .forEach(year -> {
                    System.out.println("> No ano de " + year);
                    salesCountByPaymentMethodByYear.get(year)
                            .forEach((key, value) -> System.out.printf("Meio de pagamento %s teve %s vendas", key, value).println());
                });

        System.out.println("-------------------");
        System.out.println("Top 3 de vendedores\n");

        final var topThreeSellers = reader.top3BestSellers();
        topThreeSellers.forEach((key, value) -> System.out.printf("%s com %s em vendas", key, this.toCurrency(value)).println());
    }

    private String toCurrency(BigDecimal value) {
        return NumberFormat.getInstance().format(value);
    }

    public static void main(String[] args) {
        new Application();
    }
}
