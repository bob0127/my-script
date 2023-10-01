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

public class Mushroom {
	public static Minecraft mc = Minecraft.getMinecraft();
	public Rotation Red = new Rotation(-65.0F,0.0F);
	public Rotation Brown = new Rotation(115.0F,0.0F);
	public double PX;
	public double PY;
	public double PZ;
	public boolean movingforward = true;
	public boolean Ladder = false;
	public boolean cooldown = false;
	public boolean stuck = false;
	public boolean interval = false;
	public boolean sethome = false;
	public int setnumber = Monitor.random.nextInt(3)+5;
	public int failsafe = 0;
	
	public boolean edge() {
		double Z = 0;
		if(Config.Farming_Type.getString().equals("Red Mush")) {
			if(movingforward) 
				Z=0.4;
			else 
				Z=-0.4;
		}else {
			if(movingforward) 
				Z=-0.4;
			else 
				Z=0.4;
		}
		int leftX = (int) Math.floor(mc.thePlayer.posX);
		int leftY = (int) Math.floor(mc.thePlayer.posY);
		int leftZ = (int) Math.floor(mc.thePlayer.posZ+Z);
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
	
	public boolean isair() {
		int Z = 0;
		if(Config.Farming_Type.getString().equals("Red Mush"))
			Z = 1;
		else
			Z = -1;
		int leftX = (int) Math.floor(mc.thePlayer.posX);
		int leftY = (int) Math.floor(mc.thePlayer.posY);
		int leftZ = (int) Math.floor(mc.thePlayer.posZ);
		BlockPos b = new BlockPos(leftX, leftY, leftZ+Z);
		return mc.theWorld.getBlockState(b).getBlock()==Blocks.air;
	}
	
	public boolean setspawn() {
		int leftX = (int) Math.floor(mc.thePlayer.posX);
		int leftY = (int) Math.floor(mc.thePlayer.posY);
		int leftZ = (int) Math.floor(mc.thePlayer.posZ);
		BlockPos b = new BlockPos(leftX, leftY, leftZ);
		return mc.theWorld.getBlockState(b).getBlock()==Blocks.stone_slab;
	}
	
	public void change() {
		(new Thread(() -> {
			  try {
				  if(Config.Failsafe.getBoolean())
					  failsafe++;
				  Thread.sleep((50 + Monitor.random.nextInt(20)));
				  KeyBinding.unPressAllKeys();
				  Thread.sleep((50 + Monitor.random.nextInt(20)));
				  if(Config.Farming_Type.getString().equals("Red Mush"))
					  Red.sight();
				  else 
					  Brown.sight();
				  if(!sethome) {
					  KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), true);
					  Thread.sleep((50 + Monitor.random.nextInt(20)));
					  mc.thePlayer.sendChatMessage("/sethome");
					  if(movingforward)
						  KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), true);
					  else 
						  KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
				  }
				  movingforward = !movingforward;
				  Thread.sleep((1000 + Monitor.random.nextInt(20)));
				  if(failsafe == setnumber) {
					  (new Thread(() -> {
							 try {
								Thread.sleep((500 + Monitor.random.nextInt(100)));
								KeyBinding.unPressAllKeys();
								mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GOLD + "Failsafe Start!"));
								Thread.sleep((10000 + Monitor.random.nextInt(100)));
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
				  Thread.sleep((500 + Monitor.random.nextInt(20)));
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
				movingforward = true;
				Ladder = false;
				cooldown = false;
				failsafe = 0;
				if(Config.Farming_Type.getString().equals("Red Mush")) {
					Red.sight();
					Monitor.yaw = Red.Yaw;
					movingforward = true;
				}else { 
					Brown.sight();
					Monitor.yaw = Brown.Yaw;
					movingforward = false;
				}
				KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(),true);
				if(movingforward)
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
				else 
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), true);
			}
			
			if(Monitor.cont) {
				Monitor.cont = false;
				if(Config.Farming_Type.getString().equals("Red Mush"))
					  Red.sight();
				else 
					  Brown.sight();
				KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), true);
				if(movingforward)
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
				else 
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), true);
			}
			
			if(stuck) {
				stuck = false;
				KeyBinding.unPressAllKeys();
				(new Thread(() -> {
					try {
						KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), true);
						Thread.sleep((200 + Monitor.random.nextInt(20)));
						KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), false);
						Thread.sleep((50 + Monitor.random.nextInt(20)));
						KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), true);
						Thread.sleep((200 + Monitor.random.nextInt(20)));
						KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), false);
						Monitor.cont = true;	
						cooldown = false;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				})).start();
			}
			
			if(!interval) {
				interval = true;
				(new Thread(() -> {
					try {
						PX = mc.thePlayer.posX;
						PY = mc.thePlayer.posY;
						PZ = mc.thePlayer.posZ;
						Thread.sleep(3000);
						if(mc.thePlayer.getDistance(PX, PY, PZ) < 2.0D) {
							stuck = true;
							cooldown = true;
						}
						interval = false;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				})).start();
			}
			
			if(!cooldown) {
				if(edge()) {
					cooldown = true;
					change();
					sethome = true;
				}else if(onLadder()) {
					cooldown = true;
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
					Ladder = true;
				}
			}
			
			if(Ladder) {
				if(!onLadder()) {
					Ladder = false;
					(new Thread(() -> {
						try {
							Thread.sleep((50 + Monitor.random.nextInt(20)));
							movingforward = !isair();
							change();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					})).start();
				}
			}
			
			if(sethome) {
				if(setspawn()) {
					sethome = false;
					mc.thePlayer.sendChatMessage("/sethome");
					Monitor.cont = true;
				}
			}
		}
	}
}
