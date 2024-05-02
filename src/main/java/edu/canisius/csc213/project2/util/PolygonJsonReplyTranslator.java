package edu.canisius.csc213.project2.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.canisius.csc213.project2.quotes.*;


import java.io.IOException;

public class PolygonJsonReplyTranslator {

    public StockQuote translateJsonToFinancialInstrument(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        StockQuote retval;

        JsonNode jsonNode = objectMapper.readTree(json);
        JsonNode resultNode = jsonNode.get("results");
        resultNode = resultNode.get(0);

        String symbol = jsonNode.get("ticker").asText();
        double closePrice = resultNode.get("c").asDouble();
        int numberOfTransactions = resultNode.get("n").asInt();
        double highestPrice = resultNode.get("h").asDouble();
        double lowestPrice = resultNode.get("l").asDouble();
        double openPrice = resultNode.get("o").asDouble();
        long timestamp = resultNode.get("t").asLong();
        double tradingVolume = resultNode.get("v").asDouble();

        retval = new StockQuote( symbol,  closePrice,  highestPrice,  lowestPrice,
            numberOfTransactions, openPrice, timestamp, tradingVolume);

        return retval;
    }
}
