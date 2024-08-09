import java.util.*;

class Stock {
    String symbol;
    double price;

    Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }
}

class Portfolio {
    Map<String, Integer> holdings = new HashMap<>();
    double balance;

    Portfolio(double initialBalance) {
        this.balance = initialBalance;
    }

    void buyStock(String symbol, int quantity, double price) {
        holdings.put(symbol, holdings.getOrDefault(symbol, 0) + quantity);
        balance -= quantity * price;
    }

    void sellStock(String symbol, int quantity, double price) {
        if (holdings.getOrDefault(symbol, 0) >= quantity) {
            holdings.put(symbol, holdings.get(symbol) - quantity);
            balance += quantity * price;
        } else {
            System.out.println("Not enough shares to sell.");
        }
    }

    void displayPortfolio() {
        System.out.println("Portfolio:");
        for (String symbol : holdings.keySet()) {
            System.out.println(symbol + ": " + holdings.get(symbol) + " shares");
        }
        System.out.println("Balance: $" + balance);
    }
}

public class StockTradingPlatform {
    static Map<String, Stock> marketData = new HashMap<>();
    static Portfolio portfolio = new Portfolio(10000); // Initial balance of $10,000

    public static void main(String[] args) {
        initializeMarketData();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewMarketData();
                    break;
                case 2:
                    buyStock(scanner);
                    break;
                case 3:
                    sellStock(scanner);
                    break;
                case 4:
                    portfolio.displayPortfolio();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void initializeMarketData() {
        marketData.put("AAPL", new Stock("AAPL", 150));
        marketData.put("GOOGL", new Stock("GOOGL", 2800));
        marketData.put("AMZN", new Stock("AMZN", 3400));
    }

    private static void viewMarketData() {
        System.out.println("Market Data:");
        for (String symbol : marketData.keySet()) {
            Stock stock = marketData.get(symbol);
            System.out.println(symbol + ": $" + stock.price);
        }
    }

    private static void buyStock(Scanner scanner) {
        System.out.print("Enter stock symbol: ");
        String symbol = scanner.next();
        if (!marketData.containsKey(symbol)) {
            System.out.println("Invalid stock symbol.");
            return;
        }
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        Stock stock = marketData.get(symbol);
        double totalCost = stock.price * quantity;
        if (totalCost > portfolio.balance) {
            System.out.println("Insufficient balance.");
        } else {
            portfolio.buyStock(symbol, quantity, stock.price);
            System.out.println("Bought " + quantity + " shares of " + symbol);
        }
    }

    private static void sellStock(Scanner scanner) {
        System.out.print("Enter stock symbol: ");
        String symbol = scanner.next();
        if (!marketData.containsKey(symbol)) {
            System.out.println("Invalid stock symbol.");
            return;
        }
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        Stock stock = marketData.get(symbol);
        portfolio.sellStock(symbol, quantity, stock.price);
        System.out.println("Sold " + quantity + " shares of " + symbol);
    }
}
       
