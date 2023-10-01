package Script;

import Mining0Farming.Config;
import Mining0Farming.Monitor;
import Mining0Farming.Priority;
import Mining0Farming.Rotation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Cactus {
	public static Minecraft mc = Minecraft.getMinecraft();
	public Rotation rotation = new Rotation(-90.0F,0.0F);
	public boolean movingleft = true;
	public boolean movingforward = false;
	public boolean movingbackward = false;
	public boolean cooldown = false;
	public boolean ladder = false;
	public int setnumber = Monitor.random.nextInt(3)+12;
	public int failsafe = 0;
	
	public boolean edge() {
		double Z = 0;
		if(movingleft)
			Z=0;
		else 
			Z=0.4;
		int leftX = (int) Math.floor(mc.thePlayer.posX);
		int leftY = (int) Math.floor(mc.thePlayer.posY);
		int leftZ = (int) Math.floor(mc.thePlayer.posZ+Z);
		BlockPos b = new BlockPos(leftX, leftY, leftZ);
		return (mc.theWorld.getBlockState(b).getBlock()==Blocks.stained_glass_pane||mc.theWorld.getBlockState(b).getBlock()==Blocks.dirt);
	}
	
	public boolean go() {
		double X = 0;
		if(movingforward)
			X=0.4;
		if(movingbackward)
			X=-0.4;
		int leftX = (int) Math.floor(mc.thePlayer.posX+X);
		int leftY = (int) Math.floor(mc.thePlayer.posY);
		int leftZ = (int) Math.floor(mc.thePlayer.posZ);
		BlockPos b = new BlockPos(leftX, leftY, leftZ);
		return mc.theWorld.getBlockState(b).getBlock()==Blocks.quartz_block;
	}
	public boolean onLadder() {
		int leftX = (int) Math.floor(mc.thePlayer.posX);
		int leftY = (int) Math.floor(mc.thePlayer.posY);
		int leftZ = (int) Math.floor(mc.thePlayer.posZ);
		BlockPos b = new BlockPos(leftX, leftY, leftZ);
		return mc.theWorld.getBlockState(b).getBlock()==Blocks.ladder;
	}
	
	public void change() {
		(new Thread(() -> {
			  try {
				  if(Config.Failsafe.getBoolean())
					  failsafe++;
				  Thread.sleep((50 + Monitor.random.nextInt(20)));
				  KeyBinding.unPressAllKeys();
				  rotation.sight();
				  Thread.sleep((50 + Monitor.random.nextInt(20)));
				  KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), true);
				  Thread.sleep((100 + Monitor.random.nextInt(20)));
				  if(movingleft)
					  KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), true);
				  else 
					  KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), true);
				  movingleft = !movingleft;
				  Thread.sleep((200 + Monitor.random.nextInt(20)));
				  if(failsafe == setnumber) {
					  (new Thread(() -> {
							 try {
								Thread.sleep((500 + Monitor.random.nextInt(100)));
								KeyBinding.unPressAllKeys();
								mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GOLD + "Failsafe Start!"));
								Thread.sleep((30000 + Monitor.random.nextInt(100)));
								Monitor.cont = true;
								failsafe = 0;
								setnumber = Monitor.random.nextInt(3)+3;
								mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GOLD + "Script Resume!"));
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
					  })).start();
				  }
				  mc.thePlayer.sendChatMessage("/sethome");
				  cooldown = false;
			  } catch (Exception e) {
				  e.printStackTrace();
			  } 
		})).start();
	}
	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		if(mc==null||mc.thePlayer==null)
			 return;
		
		if(Monitor.macro) 
			return;
		
		if(failsafe == setnumber)
			return;
		
		if(Monitor.isOnGarden&&Priority.farmingActive) {
			if(Monitor.start) {
				Monitor.start = false;
				movingleft = true;
				movingforward = false;
				movingbackward = false;
				cooldown = false;
				ladder = false;
				failsafe = 0;
				rotation.sight();
				Monitor.yaw = rotation.Yaw;
				KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(),true);
				if(movingleft)
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), true);
				else 
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), true);
			}
			
			if(Monitor.cont) {
				Monitor.cont = false;
				rotation.sight();
				KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), true);
				if(movingbackward)
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), true);
				else if(movingforward)
						KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
				else {
					if(movingleft)
						KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), true);
					else 
						KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), true);
				}
			}
			
			if(!cooldown) {
				if(edge()) {
					cooldown = true;
					if(mc.thePlayer.posY == 74) { 
						movingforward = true;
						KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), movingforward);
					}else {
						movingbackward = true;
						KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), movingbackward);
					}
				}
				else if(onLadder()) {
					ladder = true;
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
					cooldown = true;
				}
			}
			
			if(movingforward||movingbackward) {
				if(go()) {
					movingforward = false;
					movingbackward = false;
					change();
				}
			}
			if(ladder) {
				if(!onLadder()) {
					ladder = false;
					change();
				}
			}
		}
	}
}
