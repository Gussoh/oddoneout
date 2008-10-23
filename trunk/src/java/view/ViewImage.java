/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.nio.ByteBuffer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.GameState;
import model.Question;

/**
 *
 * @author Gussoh
 */
public class ViewImage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            
            response.setHeader("Cache-Control", "no-cache, must-revalidate");
            response.setHeader("Expires", "Sat, 26 Jul 1997 05:00:00 GMT"); // random date in the past
            HttpSession session = request.getSession();
            
            
            String param = request.getParameter(controller.OddOneOut.PARAMETER_ANSWER);
            GameState gameState = (GameState) session.getAttribute(controller.OddOneOut.ATTRIBUTE_GAME_STATE);
            
            if(gameState == null) {
                return;
            }
            
            Question question = null;
            
            if(!gameState.isGameFinished()) { // if game isnt finished, allow only the current question to be shown
                question = gameState.getCurrentQuestion();
            } else { // otherwise expect a question parameter
                String questionParam = request.getParameter(controller.OddOneOut.PARAMETER_QUESTION);
                
                if(questionParam == null || questionParam.length() == 0) {
                    return;
                }
                
                int questionIndex = Integer.parseInt(questionParam);
                if(gameState.getQuestions().get(questionIndex) != null) {
                    question = gameState.getQuestions().get(questionIndex);
                } else { // game finished but no or invalid question parameter -- or is everything caught?
                    return;
                }
            } 
              
            
            if (param == null || param.isEmpty()) {
                return;
            } else {
                int imageIndex = Integer.parseInt(param);
                if(question.getQuestionImages().get(imageIndex) == null) {
                    return;
                }
                    
                ByteBuffer buffer = question.getQuestionImages().get(imageIndex).getImageData();
                response.setContentLength(question.getQuestionImages().get(imageIndex).getSize());
                response.setContentType(question.getQuestionImages().get(imageIndex).getContentType());
                response.getOutputStream().write(buffer.array());
            }

// If error we should write to log file. Cannot write msg since it is an image.
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        } catch (ClassCastException ex) {
            ex.printStackTrace();
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }
}
