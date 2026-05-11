package trivia;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedHashMap;

public class QuestionDeck {
    private final Map<String, Queue<String>> questionsByCategory = new LinkedHashMap<>();

    public QuestionDeck(int initialQuestions) {
        questionsByCategory.put("Pop", new LinkedList<>());
        questionsByCategory.put("Science", new LinkedList<>());
        questionsByCategory.put("Sports", new LinkedList<>());
        questionsByCategory.put("Rock", new LinkedList<>());

        for (int i = 0; i < initialQuestions; i++) {
            questionsByCategory.get("Pop").add("Pop Question " + i);
            questionsByCategory.get("Science").add("Science Question " + i);
            questionsByCategory.get("Sports").add("Sports Question " + i);
            questionsByCategory.get("Rock").add("Rock Question " + i);
        }
    }

    public String nextQuestion(String category) {
        return questionsByCategory.get(category).poll();
    }
}
