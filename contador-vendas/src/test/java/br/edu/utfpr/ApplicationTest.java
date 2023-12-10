package br.edu.utfpr;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Map;

import static br.edu.utfpr.Application.MANAGER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class ApplicationTest {

    private final SalesReader reader = new SalesReader("dados.csv");

    @Test
    void assert_totalOfCompletedSales() {
        assertThat(reader.totalOfCompletedSales())
                .isEqualTo(new BigDecimal("17876275.47"));
    }

    @Test
    void assert_totalOfCancelledSales() {
        assertThat(reader.totalOfCancelledSales())
                .isEqualTo(new BigDecimal("2010274.23"));
    }

    @Test
    void assert_dateOfMostRecentSale() {

        final var mostRecentSale = reader.mostRecentCompletedSale();

        assertThat(mostRecentSale)
                .map(Sale::getSaleDate)
                .hasValue(LocalDate.of(2014, 12, 31));
    }

    @Test
    void assert_daysBetweenFirstAndLastCancelledSale() {
        assertThat(reader.daysBetweenFirstAndLastCancelledSale())
                .isEqualTo(1814);
    }

    @Test
    void assert_totalCompletedSalesBySeller() {
        assertThat(reader.totalCompletedSalesBySeller(Application.SELLER))
                .isEqualTo(new BigDecimal("1904660.25"));
    }

    @Test
    void assert_countAllSalesByManager() {
        assertThat(reader.countAllSalesByManager(MANAGER))
                .isEqualTo(830);
    }

    @Test
    void assert_totalSalesByStatusAndMonth() {
        assertThat(reader.totalSalesByStatusAndMonth(Sale.Status.COMPLETED, Month.JULY, Month.SEPTEMBER))
                .isEqualTo(new BigDecimal("2979324.17"));
    }

    @Test
    void assert_countCompletedSalesByDepartment() {

        final var data = reader.countCompletedSalesByDepartment();

        assertThat(data)
                .contains(
                        entry("Roupas e Acessórios", 465L),
                        entry("Móveis e Decoração", 423L),
                        entry("Eletrodomésticos", 833L),
                        entry("Eletrônicos", 1315L),
                        entry("Casa e Construção", 895L),
                        entry("Esporte e Lazer", 437L),
                        entry("Utilidades Domésticas", 427L)
                );
    }

    @Test
    void assert_countCompletedSalesByPaymentMethodAndGroupingByYear() {

        final Map<String, Long> values2010 = Map.of(
                "Boleto Bancário", 107L,
                "Cartão de Crédito", 395L,
                "Cartão de Débito", 295L,
                "Transferência Eletrônica", 137L
        );

        final Map<String, Long> values2011 = Map.of(
                "Boleto Bancário", 139L,
                "Cartão de Crédito", 421L,
                "Cartão de Débito", 305L,
                "Transferência Eletrônica", 134L
        );

        final Map<String, Long> values2012 = Map.of(
                "Boleto Bancário", 131L,
                "Cartão de Crédito", 419L,
                "Cartão de Débito", 272L,
                "Transferência Eletrônica", 157L
        );

        final Map<String, Long> values2013 = Map.of(
                "Boleto Bancário", 133L,
                "Cartão de Crédito", 419L,
                "Cartão de Débito", 259L,
                "Transferência Eletrônica", 122L
        );

        final Map<String, Long> values2014 = Map.of(
                "Boleto Bancário", 132L,
                "Cartão de Crédito", 376L,
                "Cartão de Débito", 300L,
                "Transferência Eletrônica", 142L
        );

        final var data = reader.countCompletedSalesByPaymentMethodAndGroupingByYear();

        assertThat(data)
                .contains(
                        entry(2010, values2010),
                        entry(2011, values2011),
                        entry(2012, values2012),
                        entry(2013, values2013),
                        entry(2014, values2014)
                );
    }

    @Test
    void assert_top3BestSellers() {

        final var data = reader.top3BestSellers();

        assertThat(data)
                .containsExactly(
                        entry("Daniel Campos", new BigDecimal("2028668.65")),
                        entry("Adriana Gomes", new BigDecimal("1904660.25")),
                        entry("Newton Souza", new BigDecimal("1117876.28"))
                );
    }
}
