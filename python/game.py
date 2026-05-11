from .i_game import IGame
from .player import Player
from .question_deck import QuestionDeck

class Game(IGame):
    BOARD_SIZE = 12
    WINNING_COINS = 6
    INITIAL_QUESTIONS = 50

    def __init__(self):
        self.players = []
        self.question_deck = QuestionDeck(self.INITIAL_QUESTIONS)
        self.current_player_idx = 0
        self.is_getting_out_of_penalty_box = False

    def add(self, player_name: str) -> bool:
        player = Player(player_name)
        self.players.append(player)
        print(f"{player_name} was added")
        print(f"They are player number {len(self.players)}")
        return True

    def how_many_players(self) -> int:
        return len(self.players)

    def roll(self, roll: int):
        player = self.players[self.current_player_idx]
        print(f"{player.name} is the current player")
        print(f"They have rolled a {roll}")

        if player.in_penalty_box:
            if roll % 2 != 0:
                self.is_getting_out_of_penalty_box = True
                print(f"{player.name} is getting out of the penalty box")
                self._execute_turn(player, roll)
            else:
                print(f"{player.name} is not getting out of the penalty box")
                self.is_getting_out_of_penalty_box = False
        else:
            self._execute_turn(player, roll)

    def _execute_turn(self, player: Player, roll: int):
        player.advance_by(roll, self.BOARD_SIZE)
        print(f"{player.name}'s new location is {player.position}")
        print(f"The category is {self._current_category()}")
        self._ask_question()

    def _ask_question(self):
        category = self._current_category()
        print(self.question_deck.next_question(category))

    def _current_category(self) -> str:
        player = self.players[self.current_player_idx]
        category_map = {0: "Pop", 1: "Science", 2: "Sports"}
        return category_map.get((player.position - 1) % 4, "Rock")

    def handle_correct_answer(self) -> bool:
        player = self.players[self.current_player_idx]
        if player.in_penalty_box:
            if self.is_getting_out_of_penalty_box:
                print("Answer was correct!!!!")
                player.in_penalty_box = False
                player.add_coin()
                print(f"{player.name} now has {player.coins} Gold Coins.")
                winner_in_progress = self._is_game_still_in_progress()
                self._next_player()
                return winner_in_progress
            else:
                self._next_player()
                return True
        else:
            print("Answer was correct!!!!")
            player.add_coin()
            print(f"{player.name} now has {player.coins} Gold Coins.")
            winner_in_progress = self._is_game_still_in_progress()
            self._next_player()
            return winner_in_progress

    def _next_player(self):
        self.current_player_idx += 1
        if self.current_player_idx == len(self.players):
            self.current_player_idx = 0

    def wrong_answer(self) -> bool:
        player = self.players[self.current_player_idx]
        print("Question was incorrectly answered")
        print(f"{player.name} was sent to the penalty box")
        player.in_penalty_box = True
        self._next_player()
        return True

    def _is_game_still_in_progress(self) -> bool:
        return not self.players[self.current_player_idx].has_won(self.WINNING_COINS)
