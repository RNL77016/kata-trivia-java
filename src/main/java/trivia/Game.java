package trivia;

import java.util.ArrayList;
import java.util.LinkedList;

// REFACTOR ME
public class Game implements IGame {
   private static final int BOARD_SIZE = 12;
   private static final int WINNING_COINS = 6;
   private static final int INITIAL_QUESTIONS = 50;

   ArrayList players = new ArrayList();
   int[] playerPositions = new int[6];
   int[] playerCoins = new int[6];
   boolean[] playerInPenaltyBox = new boolean[6];

   LinkedList popQuestions = new LinkedList();
   LinkedList scienceQuestions = new LinkedList();
   LinkedList sportsQuestions = new LinkedList();
   LinkedList rockQuestions = new LinkedList();

   int currentPlayer = 0;
   boolean isGettingOutOfPenaltyBox;

   public Game() {
      for (int i = 0; i < INITIAL_QUESTIONS; i++) {
         popQuestions.addLast("Pop Question " + i);
         scienceQuestions.addLast(("Science Question " + i));
         sportsQuestions.addLast(("Sports Question " + i));
         rockQuestions.addLast(createRockQuestion(i));
      }
   }

   public String createRockQuestion(int index) {
      return "Rock Question " + index;
   }

   public boolean hasEnoughPlayers() {
      return (howManyPlayers() >= 2);
   }

   public boolean add(String playerName) {
      playerPositions[howManyPlayers()] = 1;
      playerCoins[howManyPlayers()] = 0;
      playerInPenaltyBox[howManyPlayers()] = false;
      players.add(playerName);

      System.out.println(playerName + " was added");
      System.out.println("They are player number " + players.size());
      return true;
   }

   public int howManyPlayers() {
      return players.size();
   }

   public void roll(int roll) {
      System.out.println(players.get(currentPlayer) + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (playerInPenaltyBox[currentPlayer]) {
         if (roll % 2 != 0) {
            isGettingOutOfPenaltyBox = true;

            System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
            movePlayer(roll);

            System.out.println(players.get(currentPlayer)
                               + "'s new location is "
                               + playerPositions[currentPlayer]);
            System.out.println("The category is " + currentCategory());
            askQuestion();
         } else {
            System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
            isGettingOutOfPenaltyBox = false;
         }

      } else {

         movePlayer(roll);

         System.out.println(players.get(currentPlayer)
                            + "'s new location is "
                            + playerPositions[currentPlayer]);
         System.out.println("The category is " + currentCategory());
         askQuestion();
      }

   }

   private void movePlayer(int roll) {
      playerPositions[currentPlayer] = playerPositions[currentPlayer] + roll;
      if (playerPositions[currentPlayer] > BOARD_SIZE) playerPositions[currentPlayer] = playerPositions[currentPlayer] - BOARD_SIZE;
   }

   private void askQuestion() {
      if (currentCategory() == "Pop")
         System.out.println(popQuestions.removeFirst());
      if (currentCategory() == "Science")
         System.out.println(scienceQuestions.removeFirst());
      if (currentCategory() == "Sports")
         System.out.println(sportsQuestions.removeFirst());
      if (currentCategory() == "Rock")
         System.out.println(rockQuestions.removeFirst());
   }


   private String currentCategory() {
      if (playerPositions[currentPlayer] - 1 == 0) return "Pop";
      if (playerPositions[currentPlayer] - 1 == 4) return "Pop";
      if (playerPositions[currentPlayer] - 1 == 8) return "Pop";
      if (playerPositions[currentPlayer] - 1 == 1) return "Science";
      if (playerPositions[currentPlayer] - 1 == 5) return "Science";
      if (playerPositions[currentPlayer] - 1 == 9) return "Science";
      if (playerPositions[currentPlayer] - 1 == 2) return "Sports";
      if (playerPositions[currentPlayer] - 1 == 6) return "Sports";
      if (playerPositions[currentPlayer] - 1 == 10) return "Sports";
      return "Rock";
   }

   public boolean handleCorrectAnswer() {
      if (playerInPenaltyBox[currentPlayer]) {
         if (isGettingOutOfPenaltyBox) {
            System.out.println("Answer was correct!!!!");
            playerCoins[currentPlayer]++;
            System.out.println(players.get(currentPlayer)
                               + " now has "
                               + playerCoins[currentPlayer]
                               + " Gold Coins.");

            boolean winner = isGameStillInProgress();
            nextPlayer();

            return winner;
         } else {
            nextPlayer();
            return true;
         }


      } else {

         System.out.println("Answer was corrent!!!!");
         playerCoins[currentPlayer]++;
         System.out.println(players.get(currentPlayer)
                            + " now has "
                            + playerCoins[currentPlayer]
                            + " Gold Coins.");

         boolean winner = isGameStillInProgress();
         nextPlayer();

         return winner;
      }
   }

   private void nextPlayer() {
      currentPlayer++;
      if (currentPlayer == players.size()) currentPlayer = 0;
   }

   public boolean wrongAnswer() {
      System.out.println("Question was incorrectly answered");
      System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
      playerInPenaltyBox[currentPlayer] = true;

      nextPlayer();
      return true;
   }


   private boolean isGameStillInProgress() {
      return !(playerCoins[currentPlayer] == WINNING_COINS);
   }
}
