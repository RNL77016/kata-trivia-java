from .i_game import IGame
from collections import deque

class GameOld(IGame):
    def __init__(self):
        self.players = []
        self.places = [0] * 6
        self.purses = [0] * 6
        self.in_penalty_box = [False] * 6

        self.pop_questions = deque()
        self.science_questions = deque()
        self.sports_questions = deque()
        self.rock_questions = deque()

        for i in range(50):
            self.pop_questions.append(f"Pop Question {i}")
            self.science_questions.append(f"Science Question {i}")
            self.sports_questions.append(f"Sports Question {i}")
            self.rock_questions.append(f"Rock Question {i}")

        self.current_player = 0
        self.is_getting_out_of_penalty_box = False

    def add(self, player_name: str) -> bool:
        self.places[len(self.players)] = 1
        self.purses[len(self.players)] = 0
        self.in_penalty_box[len(self.players)] = False
        self.players.append(player_name)
        print(f"{player_name} was added")
        print(f"They are player number {len(self.players)}")
        return True

    def roll(self, roll: int):
        print(f"{self.players[self.current_player]} is the current player")
        print(f"They have rolled a {roll}")

        if self.in_penalty_box[self.current_player]:
            if roll % 2 != 0:
                self.is_getting_out_of_penalty_box = True
                print(f"{self.players[self.current_player]} is getting out of the penalty box")
                self.places[self.current_player] += roll
                if self.places[self.current_player] > 12:
                    self.places[self.current_player] -= 12
                print(f"{self.players[self.current_player]}'s new location is {self.places[self.current_player]}")
                print(f"The category is {self._current_category()}")
                self._ask_question()
            else:
                print(f"{self.players[self.current_player]} is not getting out of the penalty box")
                self.is_getting_out_of_penalty_box = False
        else:
            self.places[self.current_player] += roll
            if self.places[self.current_player] > 12:
                self.places[self.current_player] -= 12
            print(f"{self.players[self.current_player]}'s new location is {self.places[self.current_player]}")
            print(f"The category is {self._current_category()}")
            self._ask_question()

    def _ask_question(self):
        category = self._current_category()
        if category == "Pop":
            print(self.pop_questions.popleft())
        elif category == "Science":
            print(self.science_questions.popleft())
        elif category == "Sports":
            print(self.sports_questions.popleft())
        elif category == "Rock":
            print(self.rock_questions.popleft())

    def _current_category(self) -> str:
        pos = self.places[self.current_player] - 1
        if pos in [0, 4, 8]: return "Pop"
        if pos in [1, 5, 9]: return "Science"
        if pos in [2, 6, 10]: return "Sports"
        return "Rock"

    def handle_correct_answer(self) -> bool:
        if self.in_penalty_box[self.current_player]:
            if self.is_getting_out_of_penalty_box:
                print("Answer was correct!!!!")
                self.in_penalty_box[self.current_player] = False # Fixed bug
                self.purses[self.current_player] += 1
                print(f"{self.players[self.current_player]} now has {self.purses[self.current_player]} Gold Coins.")
                winner = self._did_player_win()
                self._next_player()
                return winner
            else:
                self._next_player()
                return True
        else:
            print("Answer was correct!!!!") # Fixed typo
            self.purses[self.current_player] += 1
            print(f"{self.players[self.current_player]} now has {self.purses[self.current_player]} Gold Coins.")
            winner = self._did_player_win()
            self._next_player()
            return winner

    def _next_player(self):
        self.current_player += 1
        if self.current_player == len(self.players):
            self.current_player = 0

    def wrong_answer(self) -> bool:
        print("Question was incorrectly answered")
        print(f"{self.players[self.current_player]} was sent to the penalty box")
        self.in_penalty_box[self.current_player] = True
        self._next_player()
        return True

    def _did_player_win(self) -> bool:
        return not (self.purses[self.current_player] == 6)
