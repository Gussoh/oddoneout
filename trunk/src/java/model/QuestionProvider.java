/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gussoh
 */
public abstract class QuestionProvider {

    private BlockingQueue<Question> questionCache = new LinkedBlockingQueue<Question>();
    private QuestionProviderException lastException;

    /**
     * 
     * @param cacheSize Maximum number of questions in prefetch cache.
     */
    public QuestionProvider(int prefetchSize) {
        questionCache = new LinkedBlockingQueue<Question>(prefetchSize);
        new Thread(new PrefetchThread()).start();
    }

    public QuestionProvider() {
        this(10);
    }

    protected abstract Question createQuestion() throws QuestionProviderException;

    public Question getPrefetchedQuestion() throws QuestionProviderException {

        try {
            if (!(questionCache.isEmpty() && lastException != null)) {
                return questionCache.take();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(QuestionProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw lastException;
    }

    private class PrefetchThread implements Runnable {

        @Override
        public void run() {
            for (;;) {
                try {
                    System.out.println("Fetching new question, cache size: " + questionCache.size() + " / " + (questionCache.size() + questionCache.remainingCapacity()));
                    questionCache.put(createQuestion());
                    lastException = null;
                } catch (InterruptedException ex) {
                    Logger.getLogger(QuestionProvider.class.getName()).log(Level.SEVERE, null, ex);
                } catch (QuestionProviderException ex) {
                    lastException = ex;
                    try {
                        System.out.println("Error, sleeping 5 seconds...");
                        Thread.sleep(5000);
                    } catch (InterruptedException ex1) {
                    }
                }
            }
        }
    }
}
