package edu.canisius.csc213.project2.quotes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.canisius.csc213.project2.util.*;

public class PolygonStockQuoteProvider implements StockQuoteProvider{

    @Override
    public StockQuote getStockQuote(String stockQuoteEndpoint) throws IOException {
        String json = sendGetRequest(stockQuoteEndpoint);
        PolygonJsonReplyTranslator jft = new PolygonJsonReplyTranslator();
        return jft.translateJsonToFinancialInstrument(json);

    }

    public static String sendGetRequest(String endpointUrl) {
        StringBuilder response = new StringBuilder();
        try{
            URL url = new URL(endpointUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
            conn.setRequestMethod("GET");
            InputStream inputStream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            
            reader.close();
            conn.disconnect();

        } catch (Exception e){
            e.printStackTrace();
        }
        return response.toString();
    }
    public String getEndpointUrl(String symbolName, String date, String apiKey) {
        if  (!date.substring(4, 5).equals("-")) {
            throw new IllegalArgumentException();
        }
        
        return "https://api.polygon.io/v2/aggs/ticker/" + symbolName + "/range/1/day/" + date + "/"+ date +"?apiKey=" + apiKey;
        
    }


}
