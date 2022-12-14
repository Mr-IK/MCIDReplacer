package jp.mrik.mcidreplacer;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class MCIDReplacer extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("mrep").setExecutor(new MRepCommand(this));
        Bukkit.getPluginManager().registerEvents(this,this);
        mcidRep = new AConfigFile(this,"mcidrep.yml");
        mcidRep.saveDefaultConfig();
        reloadReplaceData();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        event.setJoinMessage("§e"+MCIDReplacer.getReplaceData(event.getPlayer().getName())+" がログインしました");
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        event.setQuitMessage("§e"+MCIDReplacer.getReplaceData(event.getPlayer().getName())+" がログアウトしました");
    }

    private static AConfigFile mcidRep;

    private static HashMap<String,String> dataMap = new HashMap<>();

    public static void reloadReplaceData(){
        dataMap = new HashMap<>();
        FileConfiguration config = mcidRep.getConfig();
        for(String key : config.getKeys(false)){
            dataMap.put(key,config.getString(key));
        }
    }

    public static void setReplaceData(String mcid,String name){
        if(name==null){
            dataMap.remove(mcid);
        }else{
            dataMap.put(mcid,name);
        }
        mcidRep.getConfig().set(mcid,name);
        mcidRep.saveConfig();
    }

    public static String getReplaceData(String mcid){
        if(!dataMap.containsKey(mcid)){
            return mcid;
        }
        return dataMap.get(mcid);
    }

    public static HashMap<String,String> getDataMap(){
        return dataMap;
    }
}
