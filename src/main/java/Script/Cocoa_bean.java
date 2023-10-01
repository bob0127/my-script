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

public class Cocoa_bean {
	public static Minecraft mc = Minecraft.getMinecraft();
	public Rotation rotation = new Rotation(-180.0F, -45.0F);
	public boolean movingbackward = true;
	public boolean cooldown = false;
	public boolean movingright = false;
	public boolean movingleft = false;
	public int setnumber = Monitor.random.nextInt(3)+5;
	public int failsafe = 0;
	
	public int edge() {
		double Z;
		  if(movingbackward)//Z+
			 Z=0.4;
		  else
			 Z=-0.4;
		  int leftX = (int) Math.floor(mc.thePlayer.posX);
	      int leftY = (int) Math.floor(mc.thePlayer.posY);
	      int leftZ = (int) Math.floor(mc.thePlayer.posZ+Z);
		  BlockPos b = new BlockPos(leftX, leftY, leftZ);
		  if(mc.theWorld.getBlockState(b).getBlock()==Blocks.dirt) {
			  return 1;  
		  }
		  else if(mc.theWorld.getBlockState(b).getBlock()==Blocks.glowstone)
			  return 2;
		  else return 0;

	}
	
	public boolean go() {
		double X = 0;
		if(movingright)
			X=0.4;
		if(movingleft)
			X=-0.4;
		int leftX = (int) Math.floor(mc.thePlayer.posX+X);
		int leftY = (int) Math.floor(mc.thePlayer.posY);
		int leftZ = (int) Math.floor(mc.thePlayer.posZ);
		BlockPos b = new BlockPos(leftX, leftY, leftZ);
		return(mc.theWorld.getBlockState(b).getBlock()==Blocks.dirt||mc.theWorld.getBlockState(b).getBlock()==Blocks.trapdoor);
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
				movingbackward = true;
				cooldown = false;
				movingright = false;
				movingleft = false;
				failsafe = 0;
				rotation.sight();
				Monitor.yaw = rotation.Yaw;
				KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(),true);
				if(movingbackward)
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), true);
				else 
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
			}
			
			if(Monitor.cont) {
				Monitor.cont = false;
				rotation.sight();
				KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(),true);
				if(movingbackward)
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), true);
				else 
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
			}
			
			if(!cooldown) {
				switch(edge()) {
					case 0:
						return;
					case 1:
						cooldown = true;
						KeyBinding.unPressAllKeys();
						movingright = true;
						KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), true);
						break;
					case 2:
						cooldown = true;
						KeyBinding.unPressAllKeys();
						movingleft  = true;
						KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), true);
						break;
				}
			}
			if(movingright||movingleft) {
				if(go()) {
					(new Thread(() -> {
						  try {
							  if(Config.Failsafe.getBoolean())
								  failsafe++;
							  movingright = false;
							  movingleft = false;
							  Thread.sleep((100 + Monitor.random.nextInt(20)));
							  KeyBinding.unPressAllKeys();
							  mc.thePlayer.sendChatMessage("/sethome");
							  rotation.sight();
							  Thread.sleep((50 + Monitor.random.nextInt(20)));
							  KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), true);
							  Thread.sleep((50 + Monitor.random.nextInt(20)));
							  if(movingbackward) 
								  KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);  
							  else 
								  KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), true); 
							  movingbackward = !movingbackward;
							  if(failsafe == setnumber) {
								  (new Thread(() -> {
										 try {
											Thread.sleep((500 + Monitor.random.nextInt(100)));
											KeyBinding.unPressAllKeys();
											mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GOLD + "Failsafe Start!"));
											Thread.sleep((30000 + Monitor.random.nextInt(100)));
											Monitor.cont = true;
											setnumber = Monitor.random.nextInt(3)+3;
											failsafe = 0;
											mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GOLD + "Script Resume!"));
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
								  })).start();
							  }
							  cooldown = false;
						  } catch (Exception e) {
							  e.printStackTrace();
						  } 
					})).start();
				}		
			}
		}
	}
}
