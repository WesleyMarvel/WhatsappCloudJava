package org.example.whatsapp_cloud_java.controllers;

import org.example.whatsapp_cloud_java.commons.MessageCheck;
import org.example.whatsapp_cloud_java.service.SendMessageService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import com.google.zxing.WriterException;
import org.json.JSONArray;
import org.json.JSONObject;

@RestController
@RequestMapping("/api/v1/whatsapp-connection/")
public class Whatsapp {
    private final SendMessageService sendMessageService;

    public Whatsapp(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @GetMapping("/webhook")
    public String verifyToken(@RequestParam("hub.mode") String mode,
                              @RequestParam("hub.challenge") String challenge,
                              @RequestParam("hub.verify_token") String verifyToken){
        String token = "wesleymambingerules-0998767";
        if (mode.equals("subscribe") && verifyToken.equals(token)){
            return challenge;
        } else {
            return "not valid";
        }
    }

    @PostMapping("/webhook")
    public String receiveMessage(@RequestBody String message) throws IOException, URISyntaxException, InterruptedException, WriterException {
        JSONObject jsonMessage = new JSONObject(message);
        JSONArray entries = jsonMessage.getJSONArray("entry");
        JSONObject firstEntry = entries.getJSONObject(0);
        JSONArray changes = firstEntry.getJSONArray("changes");
        JSONObject firstChange = changes.getJSONObject(0);
        JSONObject value = firstChange.getJSONObject("value");
        JSONArray contacts = value.getJSONArray("contacts");
        String from = "";
        String body = "";
        String name = "";
        String payload = "";
        JSONObject messageObj = new JSONObject();
        for (int i = 0; i < contacts.length(); i++) {
            JSONObject contact = contacts.getJSONObject(i);
            JSONObject profile = contact.getJSONObject("profile");
            name = profile.getString("name");
            String wa_id = contact.getString("wa_id");
        }
        JSONArray messages = value.getJSONArray("messages");
        System.out.println(message);
        for (int i = 0; i < messages.length(); i++) {
            try {
                messageObj = messages.getJSONObject(i);
                from = messageObj.getString("from");
                String id = messageObj.getString("id");
                String timestamp = messageObj.getString("timestamp");
                JSONObject textObj = messageObj.getJSONObject("text");
                body = textObj.getString("body");
            } catch (org.json.JSONException e) {
                JSONObject buttonObject = messageObj.getJSONObject("button");
                payload = buttonObject.getString("payload");
                String text = buttonObject.getString("text");
                System.out.println(text + payload + "************************************");
            }
        }
        if (MessageCheck.isGreeting(body)){
            sendMessageService.Message(from, "Hello I hope you are doing well, " + name);
            sendMessageService.templateMessage(from);
        }
        return message;
    }
}

