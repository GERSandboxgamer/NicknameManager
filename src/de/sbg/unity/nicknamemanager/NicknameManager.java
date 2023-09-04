package de.sbg.unity.nicknamemanager;

import net.risingworld.api.Plugin;


public class NicknameManager extends Plugin{
    
    public Nicks Nicknames;
    private nmConsole Console;

    @Override
    public void onEnable() {
        this.Console = new nmConsole(this);
        this.Nicknames = new Nicks(this, Console);
    }

    @Override
    public void onDisable() {
        
    }

    
    
}
