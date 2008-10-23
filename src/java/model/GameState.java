/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author P23225291
 */
public class GameState {

    private List<Question> questions = new LinkedList<Question>();
    private Map<Question, Integer> questionToAnswerMap = new HashMap<Question, Integer>();
    private int numQuestions,  currentQuestionIndex;
    private QuestionProvider questionProvider;

    public GameState(QuestionProvider questionProvider, int numQuestions) throws QuestionProviderException {
        this.questionProvider = questionProvider;
        this.numQuestions = numQuestions;

        questions.add(questionProvider.getPrefetchedQuestion());
        currentQuestionIndex = 0;
    }

    /**
     * Answers current questions and creates the next Question. 
     * After this method returns, getCurrentQuestion will return a new question.
     * @param answer
     */
    public void answerQuestionAndCreateNext(int answer) throws QuestionProviderException {
        if (answer < 0 || answer >= getCurrentQuestion().getQuestionImages().size()) {
            throw new IllegalStateException("Illegal answer index.");
        }
        if (isGameFinished()) {
            throw new IllegalStateException("Cannot answer question. Game already finished.");
        }
        questionToAnswerMap.put(getCurrentQuestion(), answer);

        currentQuestionIndex++;

        // question list and map doesnt match if question is added when game is finished.
        // Must be here since game is finished depends on currentQuestionIndex
        if (!isGameFinished()) {
            questions.add(questionProvider.getPrefetchedQuestion());
        }

    }

    /**
     * Returns current Question or null if game is finished.
     * @return
     */
    public Question getCurrentQuestion() {
        if (isGameFinished()) {
            return null;
        }
        return questions.get(currentQuestionIndex);
    }

    /**
     * Returns current question index. starts at 0.
     * @return the index.
     */
    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    /**
     * 
     * @return total number of questions to be answered before the game is finished.
     */
    public int getNumQuestions() {
        return numQuestions;
    }

    /**
     * 
     * @return true if game is finished, otherwise false.
     */
    public boolean isGameFinished() {
        return currentQuestionIndex >= numQuestions;
    }

    public Map<Question, Integer> getQuestionToAnswerMap() {
        return questionToAnswerMap;
    }

    public List<Question> getQuestions() {
        return questions;
    }
    
    public int getNumCorrect() {
        int correctAnswered = 0;
        for (Map.Entry<Question, Integer> entry : questionToAnswerMap.entrySet()) {
            if(entry.getValue() == entry.getKey().getCorrectIndex()) {
                correctAnswered++;
            }
        }
        return correctAnswered;
    }
    
    public double getCorrectRatio() {
        return ((double) getNumCorrect()) / getNumQuestions();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\nCurrentQuestionIndex: \t").append(currentQuestionIndex);
        sb.append("\nNumber of question: \t").append(numQuestions);
        sb.append("\nQuestion provider: \t").append(questionProvider);
        sb.append("\nQuestion List: \t").append(questions);
        sb.append("\nQuestion to Answer map: \t").append(questionToAnswerMap);
        return sb.toString();
    }
}
