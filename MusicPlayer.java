import java.util.*;

/**
 * Song class stores details about a song.
 */
class Song {
    String title; // Song title
    String artist; // Artist name
    String album; // Album name
    String duration; // Song duration

    Song(String title, String artist, String album, String duration) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
    }

    // Display song details
    void display() {
        System.out.println("Title: " + title + ", Artist: " + artist +
                ", Album: " + album + ", Duration: " + duration);
    }
}

/**
 * MusicPlayer class provides a basic music player prototype.
 */
public class MusicPlayer {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Song> library = new ArrayList<>(); // Music library
    static ArrayList<Song> playlist = new ArrayList<>(); // User playlist
    static int currentSongIndex = 0; // Index for playback

    public static void main(String[] args) {
        while (true) {
            // Main menu
            System.out.println("\n--- Music Player Menu ---");
            System.out.println("1. Add Song to Library");
            System.out.println("2. View Library");
            System.out.println("3. Create Playlist");
            System.out.println("4. Add Song to Playlist");
            System.out.println("5. View Playlist");
            System.out.println("6. Play Song");
            System.out.println("7. Pause Song");
            System.out.println("8. Stop Song");
            System.out.println("9. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addSong();
                case 2 -> viewLibrary();
                case 3 -> createPlaylist();
                case 4 -> addSongToPlaylist();
                case 5 -> viewPlaylist();
                case 6 -> playSong();
                case 7 -> pauseSong();
                case 8 -> stopSong();
                case 9 -> {
                    System.out.println("Exiting Music Player...");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    /**
     * Adds a song to the library.
     */
    static void addSong() {
        System.out.print("Enter song title: ");
        String title = sc.nextLine();
        System.out.print("Enter artist name: ");
        String artist = sc.nextLine();
        System.out.print("Enter album name: ");
        String album = sc.nextLine();
        System.out.print("Enter duration (mm:ss): ");
        String duration = sc.nextLine();

        Song song = new Song(title, artist, album, duration);
        library.add(song);

        System.out.println("Song added successfully to library.");
    }

    /**
     * Displays all songs in the library.
     */
    static void viewLibrary() {
        System.out.println("\n--- Music Library ---");
        for (Song song : library) {
            song.display();
        }
    }

    /**
     * Creates a new empty playlist.
     */
    static void createPlaylist() {
        playlist.clear();
        System.out.println("Playlist created successfully.");
    }

    /**
     * Adds a song from the library to the playlist.
     */
    static void addSongToPlaylist() {
        viewLibrary();
        System.out.print("Enter song number to add to playlist: ");
        int index = sc.nextInt() - 1;
        sc.nextLine();

        if (index >= 0 && index < library.size()) {
            playlist.add(library.get(index));
            System.out.println("Song added to playlist successfully.");
        } else {
            System.out.println("Invalid song selection.");
        }
    }

    /**
     * Displays the playlist.
     */
    static void viewPlaylist() {
        System.out.println("\n--- Playlist ---");
        int i = 1;
        for (Song song : playlist) {
            System.out.print(i++ + ". ");
            song.display();
        }
    }

    /**
     * Plays the current song in the playlist.
     */
    static void playSong() {
        if (playlist.size() == 0) {
            System.out.println("Playlist is empty.");
            return;
        }
        Song currentSong = playlist.get(currentSongIndex);
        System.out.println("Playing: " + currentSong.title + " by " + currentSong.artist);
    }

    /**
     * Pauses playback.
     */
    static void pauseSong() {
        System.out.println("Playback paused.");
    }

    /**
     * Stops playback.
     */
    static void stopSong() {
        System.out.println("Playback stopped.");
    }
}
