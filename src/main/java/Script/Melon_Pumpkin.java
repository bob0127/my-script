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

public class Melon_Pumpkin {
	public static Minecraft mc = Minecraft.getMinecraft();
	public Rotation rotation = new Rotation(90.0F,30.0F);
	public boolean movingleft = true;
	public boolean movingbackward = false;
	public boolean movingforward = false;
	public boolean Ladder = false;
	public boolean cooldown = false;
	public int setnumber = Monitor.random.nextInt(3)+5;
	public int failsafe = 0;
	
	public int edge() {
		double Z = 0;
		if(movingleft)
			Z=0.4;
		else 
			Z=-0.4;
		int leftX = (int) Math.floor(mc.thePlayer.posX);
		int leftY = (int) Math.floor(mc.thePlayer.posY);
		int leftZ = (int) Math.floor(mc.thePlayer.posZ+Z);
		BlockPos b = new BlockPos(leftX, leftY, leftZ);
		if(mc.theWorld.getBlockState(b).getBlock()==Blocks.quartz_block) 
			if(leftY == 69) 
				return 1;
			else 
				return 3;
		else if(mc.theWorld.getBlockState(b).getBlock()==Blocks.trapdoor) 
			return 2;
		else 
			return 0;
	}
	
	public boolean onLadder() {
		int leftX = (int) Math.floor(mc.thePlayer.posX);
		int leftY = (int) Math.floor(mc.thePlayer.posY);
		int leftZ = (int) Math.floor(mc.thePlayer.posZ);
		BlockPos b = new BlockPos(leftX, leftY, leftZ);
		return mc.theWorld.getBlockState(b).getBlock()==Blocks.ladder;
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
				movingbackward = false;
				movingforward = false;
				Ladder = false;
				cooldown = false;
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
				if(movingbackward)
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), true);
				else if(movingforward)
						KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
				else {
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), true);
					if(movingleft)
						KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), true);
					else 
						KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), true);
				}
			}
			
			if(!cooldown) {
				switch(edge()) {
				case 0:
					return;
				case 1:
					cooldown = true;
					KeyBinding.unPressAllKeys();
					movingbackward = true;
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), true);
				  	break;
				case 2:
					cooldown = true;
					(new Thread(() -> {
						try {
							Thread.sleep((200 + Monitor.random.nextInt(20)));
							KeyBinding.unPressAllKeys();
							Thread.sleep((50 + Monitor.random.nextInt(20)));
							KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
							movingforward = true;
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					})).start();
					break;
				case 3:
					cooldown = true;
					Ladder = true;
					break;
				}
			}
			
			if(movingbackward||movingforward) {
				if(onLadder()) {				
					movingbackward = false;
					movingforward = false;
					Ladder = true;
				}
			}
			
			if(Ladder) {
				if(!onLadder()) {
					Ladder = false;
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
							mc.thePlayer.sendChatMessage("/sethome");
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
