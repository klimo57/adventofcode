package at.klimo.aoc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Input {

    private static final String SESSION = "53616c7465645f5f2003e940ecec9885a7e0305d785bc15140191f073881ae073c520b3570db623cab156b55c169b439a997dd8a47aef7686985de326b937cdb";

    private final URL url;

    public Input(int year, int day) throws MalformedURLException {
        this.url = new URL(String.format("https://adventofcode.com/%s/day/%s/input", year, day));
    }

    public String[] get() throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Cookie", "session=" + SESSION);
        try(var in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String line;
            var content = new ConcurrentLinkedDeque<String>();
            while((line = in.readLine()) != null) {
                content.add(line);
            }
            return content.toArray(String[]::new);
        } catch (Exception e) {
            if(Properties.DEBUG) {
                System.out.println("HTTP error: ");
                con.getHeaderFields().entrySet()
                    .forEach(entry -> {
                        System.out.println("\t" + entry.getKey() + " >>>");
                        entry.getValue().forEach(val -> System.out.println("\t\t" + val));
                    });
            }
            throw e;
        }
    }
}
