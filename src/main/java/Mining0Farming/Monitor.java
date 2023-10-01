package Mining0Farming;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Monitor {
	public static Minecraft mc = Minecraft.getMinecraft();
	public static final Random random = new Random();
	public static boolean start = true;
	public static boolean cont = false;
	public static boolean isOnGarden = false;
	public static boolean macro = false;
	public static float yaw = 0; 
	public boolean cooldown = false;
	public boolean check10 = false;
	public boolean save; 
	public static boolean flying = false;
	public boolean during = false;
	private static final String[] FAILSAFE_MESSAGES = new String[] { 
		      "What", "what?", "what", "what??", "What???", "Wut?", "?", "what???", "yo huh", "yo huh?", 
		      "yo?", "ehhhhh??", "eh", "yo", "ahmm", "ehh", "LOL what", "Lol", 
		      "lol", "lmao", "Lmfao", "lmfao", "wtf is this", "wtf", "WTF", "wtf is this?", "wtf???", "tf", 
		      "tf?", "wth", "lmao what?", "????", "??", "???????", "???", "UMMM???", "Umm", "ummm???", 
		      "damn wth", "Dang it", "Damn", "damn wtf", "damn", "hmmm", "hm", "sus", "hmm", "Ok?", 
		      "ok?", "again lol", "again??", "ok damn" };
	
	public static boolean hasLine(String sbString) {
		if (mc != null && mc.thePlayer != null) {
			ScoreObjective sbo = mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
			if (sbo != null) {
				List<String> scoreboard = getSidebarLines();
				scoreboard.add(StringUtils.stripControlCodes(sbo.getDisplayName()));
				for (String s : scoreboard) {
					String validated = stripString(s);
					if (validated.contains(sbString))
						return true; 
				} 
			} 
	    } 
	    return false;
	 }
		  
	  public static String stripString(String s) {
		  char[] nonValidatedString = StringUtils.stripControlCodes(s).toCharArray();
		  StringBuilder validated = new StringBuilder();
		  for (char a : nonValidatedString) {
			  if (a <= 'Z' && a >= 'A' || a <= 'z' && a >= 'a'|| a==32)
				  validated.append(a); 
		  } 
		  return validated.toString();
	  }
		  
	  private static List<String> getSidebarLines() {
		  List<String> lines = new ArrayList<>();
		  Scoreboard scoreboard = mc.theWorld.getScoreboard();
		  if (scoreboard == null)
			  return lines; 
		  ScoreObjective objective = scoreboard.getObjectiveInDisplaySlot(1);
		  if (objective == null)
			  return lines; 
		  Collection<Score> scores = scoreboard.getSortedScores(objective);
		  List<Score> list = new ArrayList<>();
		  for (Score s : scores) {
		      if (s != null && s.getPlayerName() != null && !s.getPlayerName().startsWith("#"))
		    	  list.add(s); 
		  } 
		  if (list.size() > 15) {
			  scores = Lists.newArrayList(Iterables.skip(list, scores.size() - 15));
		  } else {
			  scores = list;
		  } 
		  for (Score score : scores) {
			  ScorePlayerTeam team = scoreboard.getPlayersTeam(score.getPlayerName());
		      lines.add(ScorePlayerTeam.formatPlayerName((Team)team, score.getPlayerName()));
		  } 
		  return lines;
	  }
	  public static boolean isInGarden() {
		  return (hasLine("The Garden")||hasLine("Plot"));
	  }
	  
	  public static boolean isInIsland() {
		  return hasLine("Your Island");
	  }
		  
	  public static boolean isInHub() {
		  return (hasLine("Village") && !hasLine("Dwarven"));
	  }
	  
	  public static boolean isInLobby() {
		  return (hasLine("HYPIXEL") || hasLine("PROTOTYPE"));
	  }
	  
	  public static boolean inLimbo() {
		  if (mc.objectMouseOver == null || mc.objectMouseOver.getBlockPos() == null)
			  return false; 
		  ItemStack heldItem = mc.thePlayer.getHeldItem();
		  return (mc.theWorld.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock() == Blocks.wall_sign && heldItem == null);
	  }
	  
	  public void macrostop() {
		  (new Thread(() -> {
				  try {					
					Thread.sleep((500 + random.nextInt(100)));
					mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.AQUA + "You must stop macro for a while Farm yourself!"));
					mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.AQUA + "Or stop farming!"));
					Thread.sleep((1500 + random.nextInt(100)));
					KeyBinding.unPressAllKeys();
					float p = 0.2F;
					for(int i=0;i<86;i++) {
						mc.thePlayer.rotationYaw -=1.0F;
						mc.thePlayer.rotationPitch +=p;
						Thread.sleep((random.nextInt(5)));
						p *= -1;
					}
					
					Thread.sleep((500 + random.nextInt(94)));
					for(int i=0;i<157;i++) {
						mc.thePlayer.rotationYaw +=1.0F;
						mc.thePlayer.rotationPitch +=p;
						Thread.sleep((random.nextInt(5)));
						p *= -1;
					}
					Thread.sleep((1000 + random.nextInt(94)));
					mc.thePlayer.sendChatMessage(FAILSAFE_MESSAGES[random.nextInt(50)]);
					Thread.sleep((1000 + random.nextInt(94)));
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), true);
					Thread.sleep((100 + random.nextInt(20)));
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), true);
					Thread.sleep((500 + random.nextInt(20)));
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), false);
					Thread.sleep((400 + random.nextInt(20)));
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), false);
					Thread.sleep((50 + random.nextInt(20)));
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
					Thread.sleep((300 + random.nextInt(20)));
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), true);
					Thread.sleep((100 + random.nextInt(20)));
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
					Thread.sleep((500 + random.nextInt(20)));
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), false);
					Priority.farmingActive = false;
					cont = true;
					} catch (InterruptedException e) {
					e.printStackTrace();
				}
		  })).start();
		  
		  (new Thread(() -> {
			  if(Config.After_Check_Resume.getBoolean()) {
				  try {
					Thread.sleep(600000);
					mc.thePlayer.sendChatMessage("/hub");
					Thread.sleep(1000);
					macro = false;
					Priority.farmingActive = true;
				  } catch (InterruptedException e) {
					e.printStackTrace();
				  }
			  }
		  })).start();
	  }
	  
	  @SubscribeEvent
	  public void marcocheck(TickEvent.ClientTickEvent event) {
		  if(mc==null||mc.theWorld==null||mc.thePlayer==null||mc.objectMouseOver==null||mc.objectMouseOver.getBlockPos() == null|| mc.thePlayer.getHeldItem() == null) 
			  return;
		  int leftX = (int) Math.floor(mc.thePlayer.posX);
		  int leftY = (int) Math.floor(mc.thePlayer.posY);
		  int leftZ = (int) Math.floor(mc.thePlayer.posZ);
		  BlockPos a = new BlockPos(leftX, leftY-1, leftZ);
		  ItemStack item = mc.thePlayer.getHeldItem();
		  if(mc.theWorld.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock() == Blocks.bedrock && mc.theWorld.getBlockState(a).getBlock()==Blocks.bedrock) {
			  if(!macro) {
				  macro = true;
				  (new Thread(() -> {
					  try {
						mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.BOLD + "You're in the Bedrock Cage!"));
						Thread.sleep((1000 + random.nextInt(94)));
						KeyBinding.unPressAllKeys();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				  })).start();
			  }
		  }
		  BlockPos b = new BlockPos(leftX, leftY+1,leftZ-1);
		  BlockPos c = new BlockPos(leftX, leftY+1,leftZ+1);
		  if(mc.theWorld.getBlockState(b).getBlock()==Blocks.dirt || mc.theWorld.getBlockState(c).getBlock()==Blocks.dirt) {
			  if(!macro) {
				  mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.DARK_RED + "You're got Macro Check Dirt!"));
				  macrostop();
				  macro = true;
			  }
		  }
		  
		  if(yaw != 0.0F && !macro && isOnGarden) {
			  if(Math.abs(mc.thePlayer.rotationYaw - yaw) > 2.0F) {
				  mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.DARK_RED + "You're got Macro Check Fake Rotation!"));
				  macrostop();
				  macro = true;
			  }
		  }
		  
		  if(item.getItem() == Items.filled_map || item.getItem() == Items.map) {
			  if(!macro) {
				  (new Thread(() -> {
					  try {
						mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.DARK_RED + "You're got Macro Check Map!"));
						Thread.sleep((1000 + random.nextInt(94)));
						KeyBinding.unPressAllKeys();
					} catch (InterruptedException e) {
						e.printStackTrace();
						}
				  })).start();
				  macro = true;
			  }
		  }
		  if(flying) {
			  flying = false;
			  KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
			  during = true;
		  }
		  if(during) {
			  if(!mc.thePlayer.capabilities.isFlying) {
				  KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
				  during = false;
				  isOnGarden = true;
			  }
		  }
	  }
	  
	  @SubscribeEvent
	  public void Jacob(TickEvent.ClientTickEvent event) {
		  if(Config.Jacob.getBoolean()) {
			  if(!cooldown) {
				  cooldown = true;
				  (new Thread(() -> {
					  try {
						if(Config.Failsafe.getBoolean()) {
							if(hasLine("Jacobs")) {
								save = true;
								mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.AQUA + "Contest Start FailSafe Stop!"));
								Config.Failsafe.setValue(false);
								Priority.configfile.config.save();
							}
						}
						Thread.sleep(10000);
						cooldown = false;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				  })).start();
			  }
		  }
	  }
	  
	  @SubscribeEvent
	  public void check10sec(TickEvent.ClientTickEvent event) {
		  if(mc==null||mc.theWorld==null||mc.thePlayer==null||mc.objectMouseOver==null||mc.objectMouseOver.getBlockPos() == null|| mc.thePlayer.getHeldItem() == null) 
			  return;
		  if(!check10) {
			  check10 = true;
			  (new Thread(() -> {
				  try {
					checkplace();
					Thread.sleep(10000);
					check10 = false;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			  })).start();
		  }
	  }
	  
	  @SubscribeEvent
	  public void onChat(ClientChatReceivedEvent event) {
		  String message = StringUtils.stripControlCodes(event.message.getUnformattedText());
		  if(Config.Jacob.getBoolean()) {
			  if (message.contains(" Farming Contest is over!")) {
				  if(save) {
					  (new Thread(() -> {
						  try {
							Thread.sleep(1000);
							mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.AQUA + "Contest End FailSafe Resume!"));
							Config.Failsafe.setValue(true);
							Priority.configfile.config.save();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					  })).start();
				  }
			  }
		  }
		  
		  if(message.contains(" are playing on profile")||message.contains(" joined the lobby")) {
			  checkplace();
		  }
		  
		  if(message.contains("Important")) {
			  (new Thread(() -> {
				  try {
					mc.thePlayer.sendChatMessage("/sethome");
					Thread.sleep((500 + random.nextInt(94)));
					mc.thePlayer.sendChatMessage("/hub");
					
				  } catch (InterruptedException e) {
					e.printStackTrace();
				  }
			  })).start();
		  }
		  
		  if (message.contains(" spawned in Limbo")) {
			  checkplace();
		  }
	  }
	  
	  public static void checkplace() {
		  if(isInGarden()){
			  if((Minecraft.getMinecraft()).thePlayer.capabilities.isFlying) {
				 flying = true;
			  }else
				  isOnGarden = true;
		  }else {
			  KeyBinding.unPressAllKeys();
			  Monitor.cont = true;
			  isOnGarden = false;
		  }
		  if(isInHub()) {
			  (new Thread(() -> {
				  try {
					  Thread.sleep((1000 + random.nextInt(94)));
					  mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.BOLD + "You're in the Hub! Warping Back"));
					  Thread.sleep((500 + random.nextInt(94)));
					  mc.thePlayer.sendChatMessage("/warp garden");
				  } catch (Exception e) {
					  e.printStackTrace();
				  } 
			  })).start();
			  return;
		  }
		  if(isInLobby()) {
			  (new Thread(() -> {
				  try {
					  Thread.sleep((1000 + random.nextInt(524)));
					  mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.BOLD + "You're in the Lobby! Warping Back"));
					  Thread.sleep((500 + random.nextInt(94)));
					  mc.thePlayer.sendChatMessage("/play sb");
				  } catch (Exception e) {
					  e.printStackTrace();
				  } 
			  })).start();
			  return;
		  }
		  if(inLimbo()) {
			  (new Thread(() -> {
				  try {
					  Thread.sleep((1000 + random.nextInt(322)));
					  mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.BOLD + "You're in the Limbo! Warping Back"));
					  Thread.sleep((500 + random.nextInt(94)));
					  mc.thePlayer.sendChatMessage("/lobby");
				  } catch (Exception e) {
					  e.printStackTrace();
				  } 
			  })).start();
			  return;	
		  }
		  if(isInIsland()) {
			  (new Thread(() -> {
				  try {
					  Thread.sleep((1000 + random.nextInt(100)));
					  mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.BOLD + "You're on the Island! Warping Back"));
					  Thread.sleep((500 + random.nextInt(94)));
					  mc.thePlayer.sendChatMessage("/warp garden");
				  } catch (Exception e) {
					  e.printStackTrace();
				  } 
			  })).start();
			  return;
		  }
	  }
	  
	  @SubscribeEvent
	  public void onWorldChange(WorldEvent.Load event) {
	    if (mc == null || mc.thePlayer == null || !Priority.farmingActive)
	      return; 
	    KeyBinding.unPressAllKeys();
	    isOnGarden = false;
	  }
	  
	  @SubscribeEvent(receiveCanceled = true)
	  public void onOpenGui(GuiOpenEvent event) {
	    if (event.gui instanceof GuiDisconnected && Priority.farmingActive) {
	      reconnect();
	      event.setCanceled(true);
	    } 
	  }
	  
	  private void reconnect() {
	    FMLClientHandler.instance().connectToServer((GuiScreen)new GuiMultiplayer((GuiScreen)new GuiMainMenu()), new ServerData("Hypixel", "mc.hypixel.net", false));
	  }
}
