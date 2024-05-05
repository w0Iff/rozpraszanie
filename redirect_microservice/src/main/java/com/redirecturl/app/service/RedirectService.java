package com.redirecturl.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redirecturl.app.entity.Url;
import com.redirecturl.app.exception.UrlWasCleanedException;
import com.redirecturl.app.repository.UrlRepository;

@Service
public class RedirectService {

    private UrlRepository urlRepository;

    private CleanerExternalService cleanerExternalService;

    private final Logger log = LoggerFactory.getLogger(RedirectService.class);

    public RedirectService(@Autowired UrlRepository urlRepository,
            @Autowired CleanerExternalService cleanerExternalService) {
        this.urlRepository = urlRepository;
        this.cleanerExternalService = cleanerExternalService;
    }

    public String exec(String shortedUrl) {
        log.info("wykonuje zadanie z URL: {}", shortedUrl);
        List<Url> urls = cleanerExternalService.exec();
        for (Url url : urls) {
            if (url.getLongUrl().equals(shortedUrl)) {
                throw new UrlWasCleanedException("URL wygasł: " + url.getLongUrl());
            }
        }
        long id = Long.parseLong(shortedUrl);

        String longUrl = urlRepository.findById(id)
                .orElseThrow(() -> new UrlWasCleanedException("URL wygasł lub nie istnieje: " + shortedUrl))
                .getLongUrl();

        log.info("Przekierowanie {} na {}", shortedUrl, longUrl);
        return longUrl;
    }

}
