package com.skrocacz;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.*;

public class SkrocURL {
    private static final long MIN_TIME_BETWEEN_URLS = 5000;
    private static final Map<String, String> shortenedURLs = new HashMap<>();
    public static String shortenURL(String url) {
        String shortenedID = generateShortenedURL();
        String shortenedURL = "krotki.pl/" + shortenedID;
        shortenedURLs.put(shortenedID, url);
        return shortenedURL;
    }
    private static String generateShortenedURL() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            char randomChar = (char) ('a' + Math.random() * ('z' - 'a' + 1));
            sb.append(randomChar);
        }
        return sb.toString();
    }

    public static boolean validateURL(String url) {
        if (url.length() > 20 || !url.contains(".")) {
            return false;
        }

        Pattern pattern = Pattern.compile("^[a-zA-Z0-9./:]+$");
        Matcher matcher = pattern.matcher(url);

        return matcher.matches();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        long lastURLInputTime = 0;

        while (true) {
            System.out.print("Wprowadź URL (wpisz 'z' aby zakończyć): ");
            String inputURL = scanner.nextLine();

            if (inputURL.equalsIgnoreCase("z")) {
                System.out.println("Zakończono program.");
                break;
            }

            if (!validateURL(inputURL)) {
                System.out.println("Wprowadzony URL jest nieprawidłowy.");
                continue;
            }

            long currentTime = System.currentTimeMillis();
            if (currentTime - lastURLInputTime < MIN_TIME_BETWEEN_URLS) {
                System.out.println("Poczekaj co najmniej 5 sekund między kolejnymi wprowadzeniami.");
                continue;
            }

            String shortenedURL = shortenURL(inputURL);
            System.out.println("Skrócony URL: " + shortenedURL);

            lastURLInputTime = currentTime;
        }
    }
}


