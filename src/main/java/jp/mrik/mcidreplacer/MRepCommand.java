package jp.mrik.mcidreplacer;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class MRepCommand implements CommandExecutor {

    private MCIDReplacer plugin;

    public MRepCommand(MCIDReplacer plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("mrep.admin")){
            p.sendMessage("Unknown command. Type \"/help\" for help.");
            return true;
        }
        if (args.length == 0) {
            p.sendMessage("§a/mrep show : MCID置換の情報を表示する");
            p.sendMessage("§c/mrep set [MCID] [置換先] : MCID置換を設定する");
            p.sendMessage("§c/mdvc del [MCID] : 置換設定を削除する");
        }else if(args.length == 1){
            if(args[0].equalsIgnoreCase("show")){
                p.sendMessage("§6MCID置換情報");
                for(String mcid : MCIDReplacer.getDataMap().keySet()){
                    p.sendMessage("§e"+mcid+"§a: §6"+MCIDReplacer.getReplaceData(mcid));
                }
                return true;
            }
        }else if(args.length == 2){
            if(args[0].equalsIgnoreCase("del")){
                MCIDReplacer.setReplaceData(args[1],null);
                p.sendMessage("§aMCID:§e"+args[1]+" §aの置換を解除しました");
                return true;
            }
        }else if(args.length == 3){
            if(args[0].equalsIgnoreCase("set")){
                MCIDReplacer.setReplaceData(args[1],args[2]);
                p.sendMessage("§aMCID:§e"+args[1]+" §aの置換を §e"+args[2]+" §aにセットしました");
                return true;
            }
        }
        return true;
    }
}
