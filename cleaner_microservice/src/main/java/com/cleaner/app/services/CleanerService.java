package com.cleaner.app.services;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaner.app.entity.Url;
import com.cleaner.app.repository.UrlRepository;

@Service
public class CleanerService {

    private final UrlRepository urlRepository;

    private final Logger logger = LoggerFactory.getLogger(CleanerService.class);

    public CleanerService(@Autowired UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public List<Url> exec() {
        LocalDateTime thresholdDate = LocalDateTime.now();
        thresholdDate = thresholdDate.minusDays(14);
        List<Url> urls = urlRepository.findByCreatedAtLessThan(thresholdDate);
        if (urls.isEmpty()) {
            logger.info("Nie znaleziono URL starszych niż {} ", thresholdDate);
            return Collections.emptyList();
        }
        logger.info("Usuwam URL'e starsze niż {}", thresholdDate);
        logger.info("Znaleizono {} URL do usunięcia", urls.size());

        urls.forEach(url -> logger.info("Usuwam {}", url.getLongUrl()));

        urlRepository.deleteAll(urls);
        return urls;
    }
}
