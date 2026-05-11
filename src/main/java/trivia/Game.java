package trivia;

import java.util.ArrayList;
import java.util.LinkedList;

// REFACTOR ME
public class Game implements IGame {
   private static final int BOARD_SIZE = 12;
   private static final int WINNING_COINS = 6;
   private static final int INITIAL_QUESTIONS = 50;

   java.util.List<Player> players = new ArrayList<>();

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
      Player player = new Player(playerName);
      players.add(player);

      System.out.println(playerName + " was added");
      System.out.println("They are player number " + players.size());
      return true;
   }

   public int howManyPlayers() {
      return players.size();
   }

   public void roll(int roll) {
      Player player = players.get(currentPlayer);
      System.out.println(player.getName() + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (player.isInPenaltyBox()) {
         if (roll % 2 != 0) {
            isGettingOutOfPenaltyBox = true;

            System.out.println(player.getName() + " is getting out of the penalty box");
            movePlayer(roll);

            System.out.println(player.getName()
                               + "'s new location is "
                               + player.getPosition());
            System.out.println("The category is " + currentCategory());
            askQuestion();
         } else {
            System.out.println(player.getName() + " is not getting out of the penalty box");
            isGettingOutOfPenaltyBox = false;
         }

      } else {

         movePlayer(roll);

         System.out.println(player.getName()
                            + "'s new location is "
                            + player.getPosition());
         System.out.println("The category is " + currentCategory());
         askQuestion();
      }

   }

   private void movePlayer(int roll) {
      players.get(currentPlayer).advanceBy(roll, BOARD_SIZE);
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
      Player player = players.get(currentPlayer);
      if (player.getPosition() - 1 == 0) return "Pop";
      if (player.getPosition() - 1 == 4) return "Pop";
      if (player.getPosition() - 1 == 8) return "Pop";
      if (player.getPosition() - 1 == 1) return "Science";
      if (player.getPosition() - 1 == 5) return "Science";
      if (player.getPosition() - 1 == 9) return "Science";
      if (player.getPosition() - 1 == 2) return "Sports";
      if (player.getPosition() - 1 == 6) return "Sports";
      if (player.getPosition() - 1 == 10) return "Sports";
      return "Rock";
   }

   public boolean handleCorrectAnswer() {
      Player player = players.get(currentPlayer);
      if (player.isInPenaltyBox()) {
         if (isGettingOutOfPenaltyBox) {
            System.out.println("Answer was correct!!!!");
            player.addCoin();
            System.out.println(player.getName()
                               + " now has "
                               + player.getCoins()
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
         player.addCoin();
         System.out.println(player.getName()
                            + " now has "
                            + player.getCoins()
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
      Player player = players.get(currentPlayer);
      System.out.println("Question was incorrectly answered");
      System.out.println(player.getName() + " was sent to the penalty box");
      player.setInPenaltyBox(true);

      nextPlayer();
      return true;
   }


   private boolean isGameStillInProgress() {
      return !players.get(currentPlayer).hasWon(WINNING_COINS);
   }
}
