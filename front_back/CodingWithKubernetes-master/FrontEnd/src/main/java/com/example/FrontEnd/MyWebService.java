package com.example.FrontEnd;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MyWebService {

    @Value("${backEndURL}")
    String backEndURL;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String hello() {
        try {
            HttpPost httpPost = new HttpPost("https://commentanalyzer.googleapis.com/$discovery/rest?version=v1alpha1");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            String json = "{\r\n" +
                    "  \"clientToken\": \"AIzaSyCTyZsZm4Z9Zd0j7zs97q9KnhZh9f2pDwo\",\r\n" +
                    "  \"comment\": {\r\n" +
                    "\"text\": \"Ta geule\",\r\n" +
                    "}" +
                    "  \"requestedAttributes\": {\r\n" +
                    "\"TOXICITY\": \"{}\",\r\n" +
                    "\"PROFANITY\": \"{}\",\r\n" +
                    "}" +
                    "  \"languages\": [\"en\"],\r\n" +
                    "}";
            StringEntity stringEntity = new StringEntity(json);
            httpPost.setEntity(stringEntity);

            System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            String responseBody = httpclient.execute(httpPost, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            RestTemplate restTemplate = new RestTemplate();
            String s = restTemplate.getForObject(backEndURL, String.class);
            return "hello (from the front end)" + " " + s + " (from the back end)\n" + responseBody;
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
    }

}