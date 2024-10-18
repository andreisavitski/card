package eu.senla.card.manager;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Exchanger;
@Component
public class ExchangerManager {
    private final Map<String, Exchanger<String>> exchangerMap = new ConcurrentHashMap<>();

    public String generateCorrelationId() {
        return UUID.randomUUID().toString();
    }

    public Exchanger<String> createExchanger(String correlationId) {
        Exchanger<String> exchanger = new Exchanger<>();
        exchangerMap.put(correlationId, exchanger);
        return exchanger;
    }

    public Exchanger<String> getExchanger(String correlationId) {
        return exchangerMap.get(correlationId);
    }

    public void removeExchanger(String correlationId){
        exchangerMap.remove(correlationId);
    }
}
