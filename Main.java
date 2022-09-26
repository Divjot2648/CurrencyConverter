import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HashMap<Integer, String> Currencycodes = new HashMap<Integer, String>();

        //Currency Codes

        Currencycodes.put(1, "USD");
        Currencycodes.put(2, "CAD");
        Currencycodes.put(3, "EUR");
        Currencycodes.put(4, "HKD");
        Currencycodes.put(5, "INR");

        String fromCode, toCode;
        double amount;

        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to Currency Converter!!");

        System.out.println("Currency Converting FROM?");
        System.out.println("1:USD \t 2:CAD \t 3:EUR \t 4:HKD \t 5:INR");
        fromCode = Currencycodes.get(sc.nextInt());

        System.out.println("Currency Converting TO?");
        System.out.println("1:USD \t 2:CAD \t 3:EUR \t 4:HKD \t 5:INR");
        toCode = Currencycodes.get(sc.nextInt());

        System.out.println("Amount you wish to convert?");
        amount = sc.nextFloat();

        //sendHttpGETRequest(fromCode,toCode,amount);

        System.out.println("Thank you for using Currency converter!!");

    }
    private static void sendHttpGETRequest(String fromCode, String toCode , double amount) throws IOException {
        String GET_URL = "https://open.er-api.com/v6/latest?base=" + toCode + "&symbols=" + fromCode;
        URL url = new URL(GET_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int responseCode = httpURLConnection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject obj = new JSONObject(response.toString());
            Double exchangeRate = obj.getJSONObject("Rates").getDouble(fromCode);
            System.out.println(obj.getJSONObject("Rates"));
            System.out.println(exchangeRate);
            System.out.println();
            System.out.println(amount + fromCode + " = " + amount/exchangeRate + toCode);
        }
        else{
            System.out.println("GET Request Failed");
        }
    }
}