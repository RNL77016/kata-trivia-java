import random
from .game import Game

def main():
    not_a_winner = True
    game = Game()

    game.add("Chet")
    game.add("Pat")
    game.add("Sue")

    rand = random.Random()

    while not_a_winner:
        game.roll(rand.randint(0, 5) + 1)

        if rand.randint(0, 8) == 7:
            not_a_winner = game.wrong_answer()
        else:
            not_a_winner = game.handle_correct_answer()

if __name__ == "__main__":
    main()
