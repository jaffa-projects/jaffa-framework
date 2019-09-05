/*
 * *************************************************TAPESTRY PROPRIETARY VER 2.0
 *
 *   Copyright © 2019 Tapestry Solutions.
 *   THIS PROGRAM IS PROPRIETARY TO TAPESTRY SOLUTIONS.
 *   REPRODUCTION, DISCLOSURE, OR USE, IN WHOLE OR IN PART,
 *   UNDERTAKEN EXCEPT WITH PRIOR WRITTEN AUTHORIZATION OF
 *   TAPESTRY SOLUTIONS IS PROHIBITED.
 *
 * *************************************************TAPESTRY PROPRIETARY VER 2.0
 */

package org.jaffa.transaction.daos;

import java.util.List;

/**
 * Allows the management of transaction work states.
 */
public interface TransactionWorkDAO {

    /**
     * Adds the ID to the queue of ready-to-process transactions.
     *
     * @param transactionId the transaction ID.
     */
    void addToReadyQueue(String transactionId);

    /**
     * Polls the ready queue to see if there's work available, and if so, removes the ID and returns it.
     *
     * @return the next ID to be processed, or null if the queue is empty.
     */
    String pollReadyQueue();

    /**
     * Removes the ID from the ready queue, if present.
     *
     * @param transactionId the transaction ID.
     * @return true if the ID was removed.
     */
    boolean removeFromReadyQueue(String transactionId);

    /**
     * Adds the ID to the type-specific queue of singleton transactions waiting for others to finish.
     *
     * @param transactionType the type of the transaction.
     * @param transactionId   the transaction ID.
     */
    void addToSingletonQueue(String transactionType, String transactionId) ;

    /**
     * Informs the queue that the singleton transaction has completed, freeing the way for the next singleton.
     *
     * @param transactionType the type of the transaction.
     * @param transactionId   the transaction ID.
     */
    void singletonHasCompleted(String transactionType, String transactionId);

    /**
     * Gets the singleton for the type that's currently ready for processing.
     *
     * @param transactionType the type of the transaction.
     * @return the ID of the ready singleton, or null if none is on-deck.
     */
    String getReadySingleton(String transactionType);
    /**
     * Clears the singleton for the type that's currently ready for processing.
     *
     * @param transactionType the type of the transaction.
     */
    void clearReadySingleton(String transactionType) ;

    /**
     * Informs the queue that the transaction was not available.
     *
     * @param transactionId the transaction ID.
     */
    void transactionUnavailable(String transactionId);

    /**
     * @param transactionType
     * @return
     */
    default String peekSingletonReadyQueue(String transactionType) {
        return new String();
    }

    /**
     * @return
     */
    List<String> getSingletonTransactionTypes() ;

    /**
     *
     * @param transactionType
     * @return
     */
    boolean isSingletonProcessing(String transactionType);

    /**
     * @param transactionType
     * @param transactionId
     * @return
     */
    boolean removeFromSingletonReadyQueue(String transactionType, String transactionId) ;
    /**
     * @param transactionType
     * @param transactionId
     */
    void addToSingletonReadyQueue(String transactionType, String transactionId);

    /**
     * @param transactionType
     * @param transactionId
     * @return
     */
    boolean isSingletonInProcess(String transactionType, String transactionId);


    /**
     * @param transactionId
     * @return
     */
    boolean isInProcess(String transactionId);
}
