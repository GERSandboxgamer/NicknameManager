package de.sbg.unity.nicknamemanager.database;

import de.sbg.unity.nicknamemanager.NicknameManager;
import de.sbg.unity.nicknamemanager.nmConsole;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import net.risingworld.api.database.Database;
import net.risingworld.api.objects.Player;

public class nmDatabase {

    private final Database Database;
    private final NicknameManager plugin;
    private final nmConsole Console;
    private final Connection conn;
    private PreparedStatement pstmt;

    public nmDatabase(NicknameManager plugin, nmConsole Console) {
        this.plugin = plugin;
        this.Console = Console;
        this.Database = plugin.getSQLiteConnection(plugin.getPath() + "/Databases/MoneyDatabase.db");
        conn = Database.getConnection();
    }
    
    public void saveAll() throws SQLException {
        saveAll(plugin.Nicknames.getNicknames());
    }
    
    public void laodAll() throws SQLException {
        loadAll(plugin.Nicknames.getNicknames());
    }

    /**
     * @hidden
     */
    public void createDatabse() {
        Database.execute("CREATE TABLE IF NOT EXISTS Nick ("
                + "ID INTEGER PRIMARY KEY NOT NULL, " //AUTOINCREMENT
                + "UID TXT,"
                + "Nickname TXT "
                + "); ");
    }

    public void addPlayer(Player player) throws SQLException {
        addPlayer(player.getUID());
    }

    public void addPlayer(String uid) throws SQLException {
        pstmt = conn.prepareStatement("INSERT INTO Nick (UID) VALUES (?)");
        pstmt.setString(1, uid);
        pstmt.executeUpdate();
        pstmt.close();
        Console.sendInfo("DB-Cash", "Add player '" + uid + "' to database!");
    }

    private void loadAll(HashMap<String, String> Nicknames) throws SQLException {
        int zähler = 0;
        try (ResultSet result = Database.executeQuery("SELECT * FROM 'Nick'")) {
            while (result.next()) {
                zähler += 1;
                String uid = result.getString("UID");
                String nick = result.getString("Nickname");
                Nicknames.put(uid, nick);
            }
        }
        Console.sendInfo("DB-Nick", "Load " + zähler + " players from database!");
    }

    private void saveAll(HashMap<String, String> Nicknames) throws SQLException {
        int zähler = 0;
        for (String s : Nicknames.keySet()) {
            zähler += 1;
            String nick = Nicknames.get(s);
            pstmt = conn.prepareStatement("UPDATE Nick SET Nickname=? WHERE UID='" + s + "'");
            pstmt.setString(1, nick);
            pstmt.executeUpdate();
            pstmt.close();
        }
        Console.sendInfo("DB-Nick", "Save " + zähler + " players to database!");

    }

}
