package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandUtils {

    public static String executeCommand(String command) {
        StringBuilder output = new StringBuilder();

        Process process;
        try {
            process = Runtime.getRuntime().exec(command);
            process.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();

    }
}
