import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.file.*;

public class Main {
    public static void main(String[] args) {
        String os = System.getProperty("os.name").toLowerCase();
        if (!os.contains("mac") && !os.contains("win")) {
            JOptionPane.showMessageDialog(null, "This application only supports Mac and Windows.", "Unsupported OS", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Download iClient");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 150);
            frame.setLayout(new BorderLayout());

            JLabel label = new JLabel("Download iClient 1.20.2", SwingConstants.CENTER);
            JButton button = new JButton("Download");

            button.addActionListener(e -> {
                if (os.contains("mac")) {
                    File minecraftFolder = new File(System.getProperty("user.home") + "/Library/Application Support/minecraft");
                    if (!minecraftFolder.exists()) {
                        JOptionPane.showMessageDialog(frame, "Can't find Minecraft folder", "Error", JOptionPane.ERROR_MESSAGE);
                        System.exit(1);
                    }
                    File versionsFolder = new File(minecraftFolder, "versions/iclient");
                    if (!versionsFolder.exists() && versionsFolder.mkdirs()) {
                        JOptionPane.showMessageDialog(frame, "iClient folder created: " + versionsFolder.getPath(), "Success", JOptionPane.INFORMATION_MESSAGE);
                    }

                    try {
                        downloadFile("https://github.com/Clicker-games-Studio/iClient/releases/download/iclient3/iclient.jar", new File(versionsFolder, "iclient.jar"));
                        downloadFile("https://github.com/Clicker-games-Studio/iClient/releases/download/iclient3/iclient.json", new File(versionsFolder, "iclient.json"));
                        JOptionPane.showMessageDialog(frame, "Download complete!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Failed to download files", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (os.contains("win")) {
                    File tempMinecraftFolder = new File(System.getenv("TEMP") + "\\.minecraft");
                    if (!tempMinecraftFolder.exists()) {
                        JOptionPane.showMessageDialog(frame, "Can't find Minecraft folder in %TEMP%", "Error", JOptionPane.ERROR_MESSAGE);
                        System.exit(1);
                    }
                    File versionsFolder = new File(tempMinecraftFolder, "versions\\iclient");
                    if (!versionsFolder.exists() && versionsFolder.mkdirs()) {
                        JOptionPane.showMessageDialog(frame, "iClient folder created: " + versionsFolder.getPath(), "Success", JOptionPane.INFORMATION_MESSAGE);
                    }

                    try {
                        downloadFile("https://github.com/Clicker-games-Studio/iClient/releases/download/iclient3/iclient.jar", new File(versionsFolder, "iclient.jar"));
                        downloadFile("https://github.com/Clicker-games-Studio/iClient/releases/download/iclient3/iclient.json", new File(versionsFolder, "iclient.json"));
                        JOptionPane.showMessageDialog(frame, "Download complete!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Failed to download files", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            frame.add(label, BorderLayout.CENTER);
            frame.add(button, BorderLayout.SOUTH);

            frame.setVisible(true);
        });
    }

    private static void downloadFile(String fileURL, File destination) throws IOException {
        try (InputStream in = new URL(fileURL).openStream()) {
            Files.copy(in, destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
