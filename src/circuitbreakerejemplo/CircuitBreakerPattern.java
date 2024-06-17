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
public class CircuitBreakerPattern {
    public static void main(String[] args) {
        CircuitBreaker circuitBreaker = new CircuitBreaker(3, 5000); // 3 failures allowed before opening for 5 seconds
        ExternalService externalService = new ExternalService(circuitBreaker);

        for (int i = 0; i < 10; i++) {
            try {
                String response = externalService.performRequest();
                System.out.println("Response: " + response);
            } catch (Exception ex) {
                System.out.println("Exception: " + ex.getMessage());
            }
        }
    }
}
