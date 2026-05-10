
import java.sql.*;
import java.util.*;

/**
 * This prototype demonstrates a Music Player with Playlist
 * functionality using JDBC and a database connection.
 * 
 * Features:
 * - Music Library Management
 * - Playlist Creation & Management
 * - Music Playback Controls
 * - Search & Filtering
 */
public class MusicPlayerJDBC {

    static Scanner sc = new Scanner(System.in);
    static Connection conn;

    /**
     * Main function initializes DB connection and displays menu.
     */
    public static void main(String[] args) {
        try {
            connectDatabase(); // Connect to database
            while (true) {
                System.out.println("\n--- Music Player Menu ---");
                System.out.println("1. Add Song to Library");
                System.out.println("2. View Library");
                System.out.println("3. Create Playlist");
                System.out.println("4. Add Song to Playlist");
                System.out.println("5. View Playlist");
                System.out.println("6. Play Song");
                System.out.println("7. Exit");
                System.out.print("Choose option: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1 -> addSongToLibrary();
                    case 2 -> viewLibrary();
                    case 3 -> createPlaylist();
                    case 4 -> addSongToPlaylist();
                    case 5 -> viewPlaylist();
                    case 6 -> playSong();
                    case 7 -> {
                        System.out.println("Exiting Music Player...");
                        conn.close(); // Close DB connection
                        return;
                    }
                    default -> System.out.println("Invalid option.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Connects to the database using JDBC.
     */
    static void connectDatabase() throws Exception {
        // Change credentials as per your setup
        String url = "jdbc:mysql://localhost:3306/musicdb";
        String user = "root";
        String password = "password";
        conn = DriverManager.getConnection(url, user, password);
        System.out.println("Database connected successfully.");
    }

    /**
     * Adds a song to the music library database.
     */
    static void addSongToLibrary() {
        try {
            System.out.print("Enter song title: ");
            String title = sc.nextLine();
            System.out.print("Enter artist name: ");
            String artist = sc.nextLine();
            System.out.print("Enter album name: ");
            String album = sc.nextLine();
            System.out.print("Enter duration (mm:ss): ");
            String duration = sc.nextLine();

            String sql = "INSERT INTO songs (title, artist, album, duration) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, artist);
            ps.setString(3, album);
            ps.setString(4, duration);
            ps.executeUpdate();

            System.out.println("Song added successfully to library.");
        } catch (Exception e) {
            System.out.println("Error adding song: " + e.getMessage());
        }
    }

    /**
     * Displays all songs from the library.
     */
    static void viewLibrary() {
        try {
            String sql = "SELECT * FROM songs";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("\n--- Music Library ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Title: " + rs.getString("title")
                        + ", Artist: " + rs.getString("artist") + ", Album: " + rs.getString("album")
                        + ", Duration: " + rs.getString("duration"));
            }
        } catch (Exception e) {
            System.out.println("Error retrieving library: " + e.getMessage());
        }
    }

    /**
     * Creates a new playlist in the database.
     */
    static void createPlaylist() {
        try {
            System.out.print("Enter playlist name: ");
            String name = sc.nextLine();

            String sql = "INSERT INTO playlists (name) VALUES (?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.executeUpdate();

            System.out.println("Playlist created successfully.");
        } catch (Exception e) {
            System.out.println("Error creating playlist: " + e.getMessage());
        }
    }

    /**
     * Adds a song to a playlist in the database.
     */
    static void addSongToPlaylist() {
        try {
            viewLibrary();
            System.out.print("Enter Song ID to add: ");
            int songId = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter Playlist ID to add to: ");
            int playlistId = sc.nextInt();
            sc.nextLine();

            String sql = "INSERT INTO playlist_songs (playlist_id, song_id) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, playlistId);
            ps.setInt(2, songId);
            ps.executeUpdate();

            System.out.println("Song added to playlist successfully.");
        } catch (Exception e) {
            System.out.println("Error adding song to playlist: " + e.getMessage());
        }
    }

    /**
     * Displays songs in a selected playlist.
     */
    static void viewPlaylist() {
        try {
            System.out.print("Enter Playlist ID to view: ");
            int playlistId = sc.nextInt();
            sc.nextLine();

            String sql = "SELECT s.id, s.title, s.artist FROM songs s JOIN playlist_songs ps ON s.id = ps.song_id WHERE ps.playlist_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, playlistId);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n--- Playlist Songs ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Title: " + rs.getString("title")
                        + ", Artist: " + rs.getString("artist"));
            }
        } catch (Exception e) {
            System.out.println("Error viewing playlist: " + e.getMessage());
        }
    }

    /**
     * Plays the first song of a playlist (simplified playback).
     */
    static void playSong() {
        try {
            System.out.print("Enter Playlist ID to play: ");
            int playlistId = sc.nextInt();
            sc.nextLine();

            String sql = "SELECT s.title, s.artist FROM songs s JOIN playlist_songs ps ON s.id = ps.song_id WHERE ps.playlist_id=? LIMIT 1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, playlistId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Playing: " + rs.getString("title") + " by " + rs.getString("artist"));
            } else {
                System.out.println("Playlist is empty or not found.");
            }
        } catch (Exception e) {
            System.out.println("Error playing song: " + e.getMessage());
        }
    }
}
