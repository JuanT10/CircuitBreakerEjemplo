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
public class ExternalService {
    private CircuitBreaker circuitBreaker;

    public ExternalService(CircuitBreaker circuitBreaker) {
        this.circuitBreaker = circuitBreaker;
    }

    public String performRequest() {
        if (circuitBreaker.allowRequest()) {
            try {
                if (shouldFail()) {
                    circuitBreaker.requestFailed();
                    throw new RuntimeException("Service unavailable");
                } else {
                    circuitBreaker.requestSucceeded();
                    return "Response from external service";
                }
            } catch (Exception ex) {
                circuitBreaker.requestFailed();
                throw ex;
            }
        } else {
            return "Fallback response";
        }
    }

    private boolean shouldFail() {
        return Math.random() < 0.3; // 30% chance of failure
    }
}
