package jp.mrik.mcidreplacer;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MRepCommand implements CommandExecutor {

    private MCIDReplacer plugin;

    public MRepCommand(MCIDReplacer plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            commandExecute(sender,args);
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("mrep.admin")){
            sendMessage(p,"Unknown command. Type \"/help\" for help.");
            return true;
        }
        commandExecute(p,args);
        return true;
    }

    public void commandExecute(CommandSender p,String[] args){
        if (args.length == 0) {
            sendMessage(p,"§a/mrep show : MCID置換の情報を表示する");
            sendMessage(p,"§a/mrep set [MCID] [置換先] : MCID置換を設定する");
            sendMessage(p,"§a/mrep del [MCID] : 置換設定を削除する");
            sendMessage(p,"§a/mrep reload : 置換設定用ファイルを再読み込みする");
        }else if(args.length == 1){
            if(args[0].equalsIgnoreCase("show")){
                sendMessage(p,"§6MCID置換情報");
                for(String mcid : MCIDReplacer.getDataMap().keySet()){
                    sendMessage(p,"§e"+mcid+"§a: §6"+MCIDReplacer.getReplaceData(mcid));
                }
            }else if(args[0].equalsIgnoreCase("reload")){
                MCIDReplacer.reloadReplaceData();
                sendMessage(p,"§aファイル設定をリロードしました");
            }
        }else if(args.length == 2){
            if(args[0].equalsIgnoreCase("del")){
                MCIDReplacer.setReplaceData(args[1],null);
                sendMessage(p,"§aMCID:§e"+args[1]+" §aの置換を解除しました");
            }
        }else if(args.length == 3){
            if(args[0].equalsIgnoreCase("set")){
                MCIDReplacer.setReplaceData(args[1],args[2]);
                sendMessage(p,"§aMCID:§e"+args[1]+" §aの置換を §e"+args[2]+" §aにセットしました");
            }
        }
    }
    
    public void sendMessage(CommandSender p,String text){
        if (!(p instanceof Player)) {
            p.sendMessage(ChatColor.stripColor(text));
            return;
        }
        p.sendMessage(text);
    }
}
