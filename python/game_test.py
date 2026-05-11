import random
import sys
import io
import unittest
from .game import Game
from .game_old import GameOld

class GameTest(unittest.TestCase):

    def run_game(self, game_class, seed):
        # Redirect stdout to capture game output
        out = io.StringIO()
        sys.stdout = out
        
        try:
            game = game_class()
            game.add("Chet")
            game.add("Pat")
            game.add("Sue")

            rand = random.Random(seed)
            not_a_winner = True
            
            # Limit loop to prevent infinite runs if a bug is introduced
            turns = 0
            while not_a_winner and turns < 1000:
                game.roll(rand.randint(0, 5) + 1)

                if rand.randint(0, 8) == 7:
                    not_a_winner = game.wrong_answer()
                else:
                    not_a_winner = game.handle_correct_answer()
                turns += 1
                
            return out.getvalue()
        finally:
            sys.stdout = sys.__stdout__

    def test_seeds(self):
        print("Running Golden Master with 10,000 seeds...")
        for i in range(10000):
            if i % 1000 == 0:
                print(f"Testing seed {i}...")
            
            output_old = self.run_game(GameOld, i)
            output_new = self.run_game(Game, i)
            
            if output_old != output_new:
                self.assertEqual(output_old, output_new, f"Divergence detected at seed {i}")
        print("Golden Master passed successfully!")

if __name__ == "__main__":
    unittest.main()
