class Player:
    def __init__(self, name: str):
        self.name = name
        self.position = 1
        self.coins = 0
        self.in_penalty_box = False

    def add_coin(self):
        self.coins += 1

    def advance_by(self, roll: int, board_size: int):
        self.position += roll
        if self.position > board_size:
            self.position -= board_size

    def has_won(self, winning_coins: int) -> bool:
        return self.coins == winning_coins

    def __str__(self):
        return self.name
