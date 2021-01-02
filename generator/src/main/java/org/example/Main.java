package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private final static String RANDOM_WORDS = "/8000words";
    private final static String NAMES = "/names";
    private final static String SURNAMES = "/surnames";
    private final static String IMAGES = "/images";
    private final static String HASHTAGS = "/hashtags";
    private final static String HOST = "http://localhost:8080/api";

    private final static Random random = new Random();

    public static void main(String[] args) {
        random.setSeed(5);
        int count = 500;
        List<String> randomWords = getFile(RANDOM_WORDS);
        List<String> names = getFile(NAMES);
        List<String> surnames = getFile(SURNAMES);
        Set<String> passwords = generatePassword(randomWords, count);
        Map<String, String> usernameToEmail = generateUsernameToEmails(generateUsername(randomWords, count));
        List<User> users = generateUsers(usernameToEmail, passwords, names, surnames, count);
        List<String> images = getFile(IMAGES);
        StringBuilder stringBuilder = new StringBuilder();
        images.forEach(stringBuilder::append);
        String jsonImages = stringBuilder.toString();
        List<Image> imageList = deserializeListImages(jsonImages);
        distributionImages(users, imageList);
        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter stringWriter = new StringWriter();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(cleanWriter(stringWriter), users);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String request = stringWriter.toString();
//        writeToFile(request);
        sendRequestAboutCreate(request, authorize("admin", "root"));
        createOtherData(users);
    }

    private static void createOtherData(List<User> users) {
        users.forEach(user -> {
            String jwt = authorize(user.getUsername(), user.getPassword());
            for (int likeCount = 0; likeCount < 15; likeCount++) {
                int randomLike = random(users.size() - 1, 0);
                createLike(user.getId(), randomLike, jwt);
            }
            for (int guestCount = 0; guestCount < 15; guestCount++) {
                int randomGuest = random(users.size() - 1, 0);
                createGuest(user.getId(), randomGuest, jwt);
            }
            List<String> tags = getFile(HASHTAGS);
            for (int tagCount = 0; tagCount < 5; tagCount++) {
                String tag = tags.get(random(tags.size() - 1, 0));
                createTag(user.getId(), tag, jwt);
            }
        });
    }

    private static void createGuest(Long id, int to, String jwt) {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(HOST + "/users/guests/from/" + id + "/to/" + to);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("x-auth-token", "T_" + jwt);
            client.execute(httpPost);
            client.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void createLike(Long id, int to, String jwt) {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(HOST + "/users/likes/from/" + id + "/to/" + to);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("x-auth-token", "T_" + jwt);
            client.execute(httpPost);
            client.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void createTag(Long id, String tag, String jwt) {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(HOST + "/users/" + id + "/tags/" + tag);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("x-auth-token", "T_" + jwt);
            client.execute(httpPost);
            client.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void distributionImages(List<User> users, List<Image> images) {
        users.forEach(user -> user.setImage(images.stream().filter(image -> image.getUserId().equals(user.getId())).findFirst().get()));
    }

    private static List<Image> deserializeListImages(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CollectionType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, Image.class);
            return objectMapper.readValue(json, javaType);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static String authorize(String username, String password) {
        String jwt = "";
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(HOST + "/auth/login");
            ObjectMapper objectMapper = new ObjectMapper();
            StringWriter stringWriter = new StringWriter();
            Map<String, String> data = new HashMap<>();
            data.put("username", username);
            data.put("password", password);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(cleanWriter(stringWriter), data);
            StringEntity entity = new StringEntity(stringWriter.toString());
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            HttpResponse response = client.execute(httpPost);
            String body = EntityUtils.toString(response.getEntity());
            jwt = body.substring(1, body.length() - 1);
            client.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return jwt;
    }

    private static void sendRequestAboutCreate(String json, String jwt) {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(HOST + "/users/batch");
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("x-auth-token", "T_" + jwt);
            client.execute(httpPost);
            client.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static List<String> getFile(String filename) {
        try (InputStream inputStream = Main.class.getResourceAsStream(filename)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            return reader.lines().collect(Collectors.toList());
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

    private static void writeToFile(String text) {
        String filePath = "users.txt";
        Path path = Paths.get(filePath);
        boolean doesFileExist = Files.exists(path, LinkOption.NOFOLLOW_LINKS);
        try {
            if (!doesFileExist) {
                File file = new File(filePath);
                if (file.createNewFile()) {
                    throw new IOException();
                }
            }
            Files.write(Paths.get(filePath), text.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static StringWriter cleanWriter(StringWriter stringWriter) {
        StringBuffer sb = stringWriter.getBuffer();
        sb.delete(0, sb.length());
        return stringWriter;
    }

    private static Set<String> generateUsername(List<String> lines, int count) {
        StringBuilder sb = new StringBuilder();
        Set<String> usernames = new LinkedHashSet<>();
        while (usernames.size() < count) {
            for (int i = 0; i != 3; i++) {
                String word = lines.get(random((lines.size() - 1), 0));
                if (i != 0) {
                    sb.append(word.substring(0, 1).toUpperCase()).append(word.substring(1));
                } else {
                    sb.append(word);
                }
            }
            usernames.add(sb.toString());
            sb.setLength(0);
        }
        return usernames;
    }

    private static Set<String> generatePassword(List<String> lines, int count) {
        StringBuilder sb = new StringBuilder();
        Set<String> passwords = new LinkedHashSet<>();
        while (passwords.size() < count) {
            for (int i = 0; i != 2; i++) {
                String word = lines.get(random(lines.size() - 1, 0));
                if (i != 0) {
                    sb.append(word.substring(0, 1).toUpperCase()).append(word.substring(1));
                } else {
                    sb.append(word);
                }
            }
            for (int i = 0; i != 2; i++) {
                sb.setCharAt(random(sb.length() - 1, 1), Character.forDigit(random(9, 0), 10));
            }
            passwords.add(sb.toString());
            sb.setLength(0);
        }
        return passwords;
    }

    private static Map<String, String> generateUsernameToEmails(Set<String> usernames) {
        StringBuilder sb = new StringBuilder();
        String[] domains = new String[]{"@gmail.com", "@mail.ru", "@yandex.ru", "@yahoo.com", "@rambler.ru", "@outlook.com"};
        Map<String, String> result = new HashMap<>();
        usernames.forEach(username -> {
            sb.append(username).append(domains[random(domains.length - 1, 0)]);
            result.put(username, sb.toString());
            sb.setLength(0);
        });
        return result;
    }

    private static List<User> generateUsers(
            Map<String, String> usernameToEmail,
            Set<String> passwords,
            List<String> names,
            List<String> surnames,
            int count
    ) {
        Iterator<String> passIterator = passwords.iterator();
        Iterator<Map.Entry<String, String>> usernameToEmailIterator = usernameToEmail.entrySet().iterator();
        List<User> users = new ArrayList<>();
        long userId = 2L;
        while (users.size() < count) {
            User user = new User();
            Map.Entry<String, String> pair = usernameToEmailIterator.next();
            user.setId(userId);
            user.setUsername(pair.getKey());
            user.setEmail(pair.getValue());
            user.setPassword(passIterator.next());
            int randomNumber = random(names.size() - 1, 0);
            user.setFirstName(names.get(randomNumber));
            user.setGender(randomNumber % 2 == 0 ? "man" : "woman");
            user.setLastName(surnames.get(random(surnames.size() - 1, 0)));
            user.setForm(generateForm());
            user.setBirthday(generateBirthday());
            user.setDescription(generateDescription());
            users.add(user);
            userId++;
        }
        return users;
    }

    private static Date generateBirthday() {
        Date date;
        while (true) {
            int year = random(2002, 1990);
            int m = random(12, 1);
            String month;
            if (m / 10 == 0 && m != 10) {
                month = 0 + Integer.toString(m);
            } else {
                month = Integer.toString(m);
            }
            int d = random(27, 1);
            String day;
            if (d / 10 == 0 && d != 10 && d != 20) {
                day = 0 + Integer.toString(d);
            } else {
                day = Integer.toString(d);
            }
            try {
                date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                        .parse(String.format("%s/%s/%s 01:00:00", day, month, year));
            } catch (Exception ex) {
                continue;
            }
            break;
        }
        return date;
    }

    private static Form generateForm() {
        Form form = new Form();
        boolean taste = random.nextBoolean();
        if (taste) {
            taste = random.nextBoolean();
            form.setMan(true);
            form.setWoman(!taste);
        } else {
            form.setMan(false);
            form.setWoman(true);
        }
        form.setFriendship(random.nextBoolean());
        form.setLove(random.nextBoolean());
        form.setSex(random.nextBoolean());
        form.setFlirt(random.nextBoolean());
        int ageFrom = random(30, 18);
        form.setAgeFrom(ageFrom);
        form.setAgeTo(random(80, ageFrom));
        return form;
    }

    private static String generateDescription() {
        String[] descriptions = new String[]
                {
                        "I am looking for love",
                        "I am looking for friendship",
                        "I am looking for dating",
                        "I am looking for sex"
                };
        return descriptions[random(descriptions.length - 1, 0)];
    }

    private static int random(int max, int min) {
        int diff = max - min;
        return random.nextInt(diff + 1) + min;
    }

}
