/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;

/**
 *
 * @author Gussoh
 */
public class Question {

    private int correctIndex;
    private List<QuestionImage> questionImages;
    private String string;

    protected Question(List<QuestionImage> questionImages, int correctIndex, String string) {
        this.correctIndex = correctIndex;
        this.string = string;
        this.questionImages = questionImages;
    }

    public String getString() {
        return string;
    }
    
    public int getCorrectIndex() {
        return correctIndex;
    }

    public List<QuestionImage> getQuestionImages() {
        return questionImages;
    }
}
