package org.example.whatsapp_cloud_java.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@Service
public class SendMessageService {
    String token = "Bearer <TOKEN>";

    public void Message(String from, String responseMessage) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://graph.facebook.com/v16.0/xxxxxxxxxxxx/messages"))
                .header("Authorization", token)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{ \"messaging_product\": \"whatsapp\", \"recipient_type\": \"individual\", " +
                        "\"to\": \"" + from + "\", \"type\": \"text\", \"text\": { \"preview_url\": false, " +
                        "\"body\": \"" + responseMessage + "\" } }"))
                .build();
        HttpClient http = HttpClient.newHttpClient();
        http.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public void templateMessage(String from) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(new URI("https://graph.facebook.com/v16.0/xxxxxxxxxxxx/messages"))
                .header("Authorization", token)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{"
                        + "\"messaging_product\": \"whatsapp\",  "
                        + "\"to\": \"" + from + "\","
                        + "\"type\": \"template\","
                        + "\"template\": {"
                        + "    \"name\": \"hello_world\","
                        + "    \"language\": {"
                        + "        \"code\": \"en_US\""
                        + "    }"
                        + "}"
                        + "}"))
                .build();
        HttpClient http1 = HttpClient.newHttpClient();
        http1.send(request1, HttpResponse.BodyHandlers.ofString());
    }
}
