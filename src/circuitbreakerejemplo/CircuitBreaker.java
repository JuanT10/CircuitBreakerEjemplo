/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package circuitbreakerejemplo;

/**
 *
 * @author Juan
 */
public class CircuitBreaker {
    private CircuitBreakerState state;
    private int failureThreshold;
    private int consecutiveFailures;
    private int resetThreshold;
    private long lastStateChangedTimestamp;

    public CircuitBreaker(int failureThreshold, int resetThreshold) {
        this.state = CircuitBreakerState.CLOSED;
        this.failureThreshold = failureThreshold;
        this.resetThreshold = resetThreshold;
        this.consecutiveFailures = 0;
        this.lastStateChangedTimestamp = System.currentTimeMillis();
    }

    public boolean allowRequest() {
        if (state == CircuitBreakerState.OPEN && isTimeToReset()) {
            state = CircuitBreakerState.HALF_OPEN;
            consecutiveFailures = 0;
        }
        return state == CircuitBreakerState.CLOSED || state == CircuitBreakerState.HALF_OPEN;
    }

    public void requestFailed() {
        consecutiveFailures++;
        if (consecutiveFailures >= failureThreshold) {
            state = CircuitBreakerState.OPEN;
            lastStateChangedTimestamp = System.currentTimeMillis();
        }
    }

    public void requestSucceeded() {
        if (state == CircuitBreakerState.HALF_OPEN || state == CircuitBreakerState.OPEN) {
            state = CircuitBreakerState.CLOSED;
            consecutiveFailures = 0;
        }
    }

    private boolean isTimeToReset() {
        return (System.currentTimeMillis() - lastStateChangedTimestamp) >= resetThreshold;
    }
}
