import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class PrincipalConAPI {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();

        while (true) {
            System.out.println("\n💱 Conversor de Monedas con API ExchangeRate");

            System.out.print("👉 Moneda de origen (por ejemplo USD): ");
            String monedaOrigen = scanner.nextLine().toUpperCase();

            System.out.print("👉 Moneda de destino (por ejemplo EUR): ");
            String monedaDestino = scanner.nextLine().toUpperCase();

            System.out.print("👉 Cantidad a convertir: ");
            double cantidad = Double.parseDouble(scanner.nextLine());

            String direccion = "https://v6.exchangerate-api.com/v6/fd5892d0681da223cc4d5f62/latest/" + monedaOrigen;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(direccion))
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            ExchangeResponse datos = gson.fromJson(response.body(), ExchangeResponse.class);

            if (!datos.getResult().equals("success")) {
                System.out.println("❌ Error al obtener los datos de la API.");
                continue;
            }

            Double tasaCambio = datos.getConversionRates().get(monedaDestino);
            if (tasaCambio == null) {
                System.out.println("❌ Moneda de destino no válida.");
                continue;
            }

            double resultado = cantidad * tasaCambio;
            System.out.printf("✅ %.2f %s = %.2f %s%n", cantidad, monedaOrigen, resultado, monedaDestino);

            System.out.print("\n🔁 ¿Deseas hacer otra conversión? (s/n): ");
            String continuar = scanner.nextLine().toLowerCase();
            if (!continuar.equals("s")) {
                System.out.println("👋 ¡Gracias por usar el conversor!");
                break;
            }
        }
    }
}
