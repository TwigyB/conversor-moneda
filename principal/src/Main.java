import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.util.Map;


public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();
        String apiKey = "fd5892d0681da223cc4d5f62";
        String baseURL = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/USD";

        client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURL))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Mapear el JSON de respuesta
        Map<String, Object> jsonMap = gson.fromJson(response.body(), Map.class);
        Map<String, Double> conversionRates = (Map<String, Double>) jsonMap.get("conversion_rates");

        int opcion = 0;

        Scanner lectura = new Scanner(System.in);
        while (opcion != 7) {
            System.out.println("\n***** Conversor de Moneda *****");
            System.out.println("1. Dólar -> Peso Argentino");
            System.out.println("2. Peso Argentino -> Dólar");
            System.out.println("3. Dólar -> Real Brasileño");
            System.out.println("4. Real Brasileño -> Dólar");
            System.out.println("5. Dólar -> Peso Colombiano");
            System.out.println("6. Peso Colombiano -> Dólar");
            System.out.println("7. Salir");
            System.out.print("Elige una opción: ");
            opcion = lectura.nextInt();

            if (opcion >= 1 && opcion <= 6) {
                System.out.print("Ingresa el valor a convertir: ");
                double cantidad = lectura.nextDouble();
                double resultado = 0.0;

                switch (opcion) {
                    case 1:
                        resultado = cantidad * conversionRates.get("ARS");
                        System.out.println("Resultado: " + resultado + " pesos argentinos");
                        break;
                    case 2:
                        resultado = cantidad / conversionRates.get("ARS");
                        System.out.println("Resultado: " + resultado + " dólares");
                        break;
                    case 3:
                        resultado = cantidad * conversionRates.get("BRL");
                        System.out.println("Resultado: " + resultado + " reales brasileños");
                        break;
                    case 4:
                        resultado = cantidad / conversionRates.get("BRL");
                        System.out.println("Resultado: " + resultado + " dólares");
                        break;
                    case 5:
                        resultado = cantidad * conversionRates.get("COP");
                        System.out.println("Resultado: " + resultado + " pesos colombianos");
                        break;
                    case 6:
                        resultado = cantidad / conversionRates.get("COP");
                        System.out.println("Resultado: " + resultado + " dólares");
                        break;
                }
            } else if (opcion == 7) {
                System.out.println("Saliendo del programa. ¡Hasta luego!");
            } else {
                System.out.println("Opción inválida. Intenta nuevamente.");
            }
        }

        lectura.close();
    }
}