import java.io.*;
import java.net.*;

public class Main {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(8080)) {
            System.out.println("Servidor rodando na porta 8080...");

            while (true) {
                try (Socket client = server.accept()) {
                    handleClient(client);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
        }
    }

    private static void handleClient(Socket client) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);

        // Leitura da solicitação HTTP
        String requestLine = in.readLine();
        if (requestLine == null || requestLine.isEmpty()) return;

        String[] requestParts = requestLine.split(" ");
        String method = requestParts[0];
        String resource = requestParts[1];

        // Lógica para responder diferentes páginas
        if (resource.equals("/")) {
            sendResponse(out, "text/html", "index.html");
        } else if (resource.equals("/pagina_usuario")) {
            sendResponse(out, "text/html", "pagina_usuario.html");
        } else {
            sendNotFound(out);
        }
    }

    private static void sendResponse(PrintWriter out, String contentType, String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            sendNotFound(out);
            return;
        }

        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: " + contentType);
        out.println();
        
        try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                out.println(line);
            }
        }
    }

    private static void sendNotFound(PrintWriter out) {
        out.println("HTTP/1.1 404 Not Found");
        out.println("Content-Type: text/html");
        out.println();
        out.println("<html><body><h1>404 - Página Não Encontrada</h1></body></html>");
    }
}
