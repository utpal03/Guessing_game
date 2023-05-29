import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final int MAX_EASY_ATTEMPTS = 10;
    private static final int MAX_MEDIUM_ATTEMPTS = 6;
    private static final int MAX_HARD_ATTEMPTS = 3;

    private static Player currentPlayer;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        currentPlayer = new Player();

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        currentPlayer.setName(name);

        System.out.println("========== Hi, " + name + "! Welcome to the Number Guessing Game ==========");
        System.out.println("Press Enter to get started");
        scanner.nextLine();
        clearScreen();

        boolean playing = true;
        while (playing) {
            clearScreen();
            displayMainMenu();

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    startGame(scanner);
                    break;
                case 2:
                    viewRecords();
                    break;
                case 3:
                    displayRules();
                    break;
                case 4:
                    System.out.println("\nThanks!! We'll see you again, " + name + "!");
                    playing = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }

        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Start Game");
        System.out.println("2. View Records");
        System.out.println("3. Rules");
        System.out.println("4. Quit");
        System.out.print("Enter your choice: ");
    }

    private static void startGame(Scanner scanner) {
        System.out.println("\nChoose Your level: ");
        System.out.println("1. Easy");
        System.out.println("2. Medium");
        System.out.println("3. Hard");
        System.out.print("Enter your choice: ");
        int level = scanner.nextInt();

        int maxAttempts;
        switch (level) {
            case 1:
                maxAttempts = MAX_EASY_ATTEMPTS;
                break;
            case 2:
                maxAttempts = MAX_MEDIUM_ATTEMPTS;
                break;
            case 3:
                maxAttempts = MAX_HARD_ATTEMPTS;
                break;
            default:
                System.out.println("Invalid choice. Returning to the main menu.");
                return;
        }

        System.out.println("\nStarting the game...");
        sleep(2000);
        clearScreen();

        int randomNumber = generateRandomNumber(scanner);
        int attempts = 0;
        boolean won = false;

        while (attempts < maxAttempts) {
            System.out.print("Enter your guessed number: ");
            int guessedNumber = scanner.nextInt();

            if (guessedNumber == randomNumber) {
                System.out.println("YAY!! YOU WON");
                currentPlayer.incrementScore();
                won = true;
                break;
            } else if (guessedNumber < randomNumber) {
                System.out.println("The number is bigger than you guessed.");
            } else {
                System.out.println("The number is smaller than you guessed.");
            }
            attempts++;
        }

        if (!won) {
            System.out.println("You've exhausted maximum attempts.");
            System.out.println("OPS... YOU LOSE!");
            System.out.println("\nBetter luck next time");
        }

        System.out.println("\nPress Enter to return to the main menu.");
        scanner.nextLine();
        new Scanner(System.in).nextLine();
    }

    private static void viewRecords() {
        System.out.println("\n--- High Scores ---");
        System.out.println("Player: " + currentPlayer.getName());
        System.out.println("Score: " + currentPlayer.getScore());
        System.out.println("-------------------");

        saveProgress();

        System.out.println("\nPress Enter to return to the main menu.");
        new Scanner(System.in).nextLine();
    }

    private static void displayRules() {
        System.out.println("\nGuessing Game RULES:\n");
        System.out.println("- The computer will generate a random number.");
        System.out.println("- You have to guess the number within a certain range.");
        System.out.println("- The game will provide hints to help you narrow down your guesses.");
        System.out.println("\n- You have a maximum of 10 guesses to find the correct number in EASY-LEVEL.");
        System.out.println("- You have a maximum of 6 guesses to find the correct number in MEDIUM-LEVEL.");
        System.out.println("- You have a maximum of 3 guesses to find the correct number in HARD-LEVEL.");
        System.out.println("\nPress Enter to return to the main menu.");
        new Scanner(System.in).nextLine();
    }

    private static int generateRandomNumber(Scanner scanner) {
        System.out.print("Enter the minimum number: ");
        int min = scanner.nextInt();

        System.out.print("Enter the maximum number: ");
        int max = scanner.nextInt();

        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void saveProgress() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("progress.txt"))) {
            writer.println(currentPlayer.getName());
            writer.println(currentPlayer.getScore());
        } catch (IOException e) {
            System.out.println("Failed to save progress: " + e.getMessage());
        }
    }
}