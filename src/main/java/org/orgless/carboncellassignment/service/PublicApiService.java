package org.orgless.carboncellassignment.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.orgless.carboncellassignment.utils.ApiEntry;
import org.orgless.carboncellassignment.utils.RequestInfoDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PublicApiService {
    private final JsonParser parser = new JsonParser();

    public List<ApiEntry> getEntries(RequestInfoDto infoDto) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI("https://api.publicapis.org/entries"))
                .build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
        final List<ApiEntry> allEntries = parser.parse(response.body());

        final String category = infoDto.getCategory();
        int temp = infoDto.getMaxEntries() == null? Integer.MAX_VALUE: infoDto.getMaxEntries();
        if(temp < 1)
            throw new IllegalArgumentException("Either maxEntries were not passed or were invalid. maxEntries must be a positive integer");

        return allEntries.stream()
                .filter(entry -> entry.getCategory().equalsIgnoreCase(category))
                .limit(temp)
                .toList();
    }

    private static class JsonParser {
        private ObjectMapper objectMapper = defaultObjectMapper();

        private ObjectMapper defaultObjectMapper() {
            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return objectMapper;
        }

        List<ApiEntry> parse(InputStream inputStream) throws IOException {
            JsonNode jsonNode = objectMapper.readTree(inputStream);
            JsonNode entries = jsonNode.get("entries");

//            System.out.println(entries.toPrettyString());
            List<ApiEntry> result = new ArrayList<>();
            Iterator<JsonNode> iterator = entries.elements();
            while(iterator.hasNext()) {
                JsonNode current = iterator.next();
                result.add(
                        objectMapper.treeToValue(current, ApiEntry.class)
                );
//                System.out.println(result.getLast());
            }

            return result;
        }
    }

}

