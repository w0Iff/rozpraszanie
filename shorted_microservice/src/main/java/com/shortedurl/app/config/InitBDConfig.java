package com.shortedurl.app.config;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.shortedurl.app.entity.ForbiddenWord;
import com.shortedurl.app.entity.Url;
import com.shortedurl.app.repository.ForbiddenWordRepository;
import com.shortedurl.app.repository.UrlRepository;

@Component
public class InitBDConfig implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(InitBDConfig.class);
    private final ForbiddenWordRepository forbiddenWordRepository;
    private final UrlRepository urlRepository;

    private final Random random = new Random();

    public InitBDConfig(@Autowired ForbiddenWordRepository forbiddenWordRepository,
            @Autowired UrlRepository urlRepository) {
        this.forbiddenWordRepository = forbiddenWordRepository;
        this.urlRepository = urlRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadInitialData();
        loadExpiredData();
    }

    public void loadInitialData() {
        List<ForbiddenWord> forbiddenWords = forbiddenWordRepository.findAll();

        if (forbiddenWords.isEmpty()) {
            log.info(" Ładowanie danych...");

            forbiddenWordRepository.saveAll(List.of(
                    new ForbiddenWord(UUID.randomUUID(), "za", LocalDateTime.now()),
                    new ForbiddenWord(UUID.randomUUID(), "malo", LocalDateTime.now()),
                    new ForbiddenWord(UUID.randomUUID(), "punktow", LocalDateTime.now()),
                    new ForbiddenWord(UUID.randomUUID(), "nie", LocalDateTime.now()),
                    new ForbiddenWord(UUID.randomUUID(), "zdales", LocalDateTime.now()),
                    new ForbiddenWord(UUID.randomUUID(), "polityka", LocalDateTime.now()),
                    new ForbiddenWord(UUID.randomUUID(), "religia", LocalDateTime.now())));
            log.info("Dane załadowane poprawnie.");

        }

    }

    public void loadExpiredData() {

        long id;
        do {
            id = random.nextLong(Long.MAX_VALUE);
        } while (urlRepository.existsById(id));

        Url url = new Url(id, "https://www.google.com", LocalDateTime.of(2024, 4, 1, 0, 0, 0));
        urlRepository.save(url);
    }
}
