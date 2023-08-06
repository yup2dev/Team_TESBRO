package com.team.tesbro.lesson_ticket;

import com.team.tesbro.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Controller
@RequiredArgsConstructor
public class LessonTicketController {

    @GetMapping("/ticket/pay")
    public String ticketc(){
        return "ticket";
    }

    @RequestMapping("/ticket/succsess")
    public String ok(@RequestParam String paymentKey, @RequestParam String orderId, @RequestParam Integer amount, Model model) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.tosspayments.com/v1/payments/confirm"))
                .header("Authorization", "Basic dGVzdF9za19XZDQ2cW9wT0I4OU1LbmJKb2JkOFptTTc1eTB2Og==")
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString("{\"paymentKey\":\"{PAYMENT_KEY}\",\"amount\":15000,\"orderId\":\"jSMW8URlObRFnJ4R3lpp1\"}"))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        return "success";
    }
}
