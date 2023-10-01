package Script_GUI;

import Mining0Farming.Monitor;
import Mining0Farming.Priority;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class GuiCommand implements ICommand {
	public String getCommandName() {
		return "bob";
	}
		  
	public String getCommandUsage(ICommandSender sender) {
		return "/" + getCommandName();
	}
	
	public List<String> getCommandAliases() {
		return new ArrayList<>(Arrays.asList(new String[] { "config", "configbob" }));
	}
		  
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length == 0) {
			Priority.displayScreen = new GuiStart();
		}else if(args[0].equals("fix")) {
			(Minecraft.getMinecraft()).thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.LIGHT_PURPLE + "Fixing"));
			Monitor.start = true;
	    } 
	}
		  
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}
		  
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		return new ArrayList<>();
	}
		  
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}
		  
	public int compareTo(@Nonnull ICommand o) {
		return 0;
	}

}
