package com.example.backend.service;

import com.example.backend.model.PixabayImage;
import com.example.backend.model.PixabayResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
public class PixabayService {

    private static final String PIXABAY_API_URL = "https://pixabay.com/api/";
    private static final String PIXABAY_API_KEY = "44874779-c9013d83ce17fb0e3fcb4f73f";

    private final RestTemplate restTemplate;

    public PixabayService() {
        this.restTemplate = new RestTemplate();
    }

    public PixabayImage getReverseImageUrl() {
        String url = UriComponentsBuilder.fromHttpUrl(PIXABAY_API_URL)
                .queryParam("key", PIXABAY_API_KEY)
                .queryParam("q", "Card Card Game Playing Card game poker gambling magic card cutout")
                .queryParam("image_type", "photo")
                .queryParam("id", "7031432")
                .toUriString();

        PixabayResponse response = restTemplate.getForObject(url, PixabayResponse.class);

        if (response != null && !response.getHits().isEmpty()) {          
            return response.getHits().get(0);
        }

        return null;
    }
    public List<PixabayImage> getAnverseImagesUrl() {
        List<String> ids = new ArrayList<String>() {{
            add("28375");
            add("28337");
            add("28374");
            add("28317");
            add("28344");            
            // Añade más valores si es necesario
        }};
        List<PixabayImage> cards = new ArrayList<PixabayImage>();
        for(String id : ids){

        
        String url = UriComponentsBuilder.fromHttpUrl(PIXABAY_API_URL)
                .queryParam("key", PIXABAY_API_KEY)
                .queryParam("q", "Card Card Game Playing Card game poker gambling magic card cutout")
                .queryParam("image_type", "photo")
                .queryParam("id", id)
                .toUriString();

        PixabayResponse response = restTemplate.getForObject(url, PixabayResponse.class);

        if (response != null && !response.getHits().isEmpty()) {          
            cards.add(response.getHits().get(0));
        }
    }

        return cards;
    }
}
