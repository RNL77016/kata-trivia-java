from collections import deque

class QuestionDeck:
    def __init__(self, initial_questions: int):
        self.questions_by_category = {
            "Pop": deque(),
            "Science": deque(),
            "Sports": deque(),
            "Rock": deque()
        }

        for i in range(initial_questions):
            self.questions_by_category["Pop"].append(f"Pop Question {i}")
            self.questions_by_category["Science"].append(f"Science Question {i}")
            self.questions_by_category["Sports"].append(f"Sports Question {i}")
            self.questions_by_category["Rock"].append(f"Rock Question {i}")

    def next_question(self, category: str) -> str:
        return self.questions_by_category[category].popleft()
