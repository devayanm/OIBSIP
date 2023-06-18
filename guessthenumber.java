import java.util.Random;
import java.util.Scanner;

public class guessthenumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int rangeStart = 1;
        int rangeEnd = 100;
        int maxAttempts = 10;
        int score = 0;
        boolean playAgain = true;

        System.out.println("=== Guess the Number ===");

        while (playAgain) {
            int targetNumber = random.nextInt(rangeEnd - rangeStart + 1) + rangeStart;
            int attempts = 0;
            boolean guessedCorrectly = false;

            System.out.println("\nI'm thinking of a number between " + rangeStart + " and " + rangeEnd);
            System.out.println("Can you guess it? You have " + maxAttempts + " attempts.");

            while (attempts < maxAttempts && !guessedCorrectly) {
                System.out.print("Enter your guess: ");
                int guess = scanner.nextInt();
                scanner.nextLine();

                attempts++;

                if (guess == targetNumber) {
                    System.out.println("Congratulations! You guessed the correct number.");
                    System.out.println("Attempts: " + attempts);
                    score += maxAttempts - attempts + 1;
                    guessedCorrectly = true;
                } else if (guess < targetNumber) {
                    System.out.println("Too low! Try again.");
                } else {
                    System.out.println("Too high! Try again.");
                }
            }

            if (!guessedCorrectly) {
                System.out.println("\nGame over! You ran out of attempts.");
                System.out.println("The number was: " + targetNumber);
            }

            System.out.println("Your current score: " + score);
            System.out.print("\nDo you want to play again? (y/n): ");
            String playAgainChoice = scanner.nextLine();

            if (!playAgainChoice.equalsIgnoreCase("y")) {
                playAgain = false;
            }
        }

        System.out.println("\nThank you for playing Guess the Number!");
        System.out.println("Final score: " + score);

        scanner.close();
    }
}
