package de.sbg.unity.nicknamemanager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import net.risingworld.api.Server;


public class Nicks {
    
    private final NicknameManager plugin;
    private final nmConsole Console;
    private final HashMap<String, String> Nicknames;
    
    public Nicks(NicknameManager plugin, nmConsole Console) {
        this.Console = Console;
        this.plugin = plugin;
        this.Nicknames = new HashMap<>();
    }

    public HashMap<String, String> getNicknames() {
        return Nicknames;
    }
    
    public List<String> getUIDs() {
        List<String> l = new ArrayList<>();
        for (String s : Nicknames.keySet()) {
            l.add(s);
        }
        return l;
    }
    
    public List<String> getAllLastKnownPlayernames() {
        List<String> l = new ArrayList<>();
        for (String uid : getUIDs()) {
            l.add(Server.getLastKnownPlayerName(uid));
        }
        return l;
    }
    
    public Collection<String> getNicks() {
        return Nicknames.values();
    }
    
    
}
