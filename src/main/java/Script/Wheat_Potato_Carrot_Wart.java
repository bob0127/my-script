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

public class Wheat_Potato_Carrot_Wart {
	public static Minecraft mc = Minecraft.getMinecraft();
	public Rotation wp = new Rotation(-90.0F,6.0F);
	public Rotation cw = new Rotation(90.0F,6.0F);
	public boolean movingright = true;
	public boolean movingbackward = false;
	public boolean movingforward = false;
	public boolean cooldown = false;
	public boolean changedir = false;
	public int setnumber = Monitor.random.nextInt(3)+5;
	public int failsafe = 0;
	
	public boolean edge() {
		double Z = 0;
		if(Config.Farming_Type.getString().equals("Wheat")||Config.Farming_Type.getString().equals("Potato")) {
			if(movingright) 
				Z=0.4;
			else 
				Z=-0.4;
		}else {
			if(movingright) 
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
	
	public boolean trapdoor() {
		int leftX = (int) Math.floor(mc.thePlayer.posX);
		int leftY = (int) Math.floor(mc.thePlayer.posY);
		int leftZ = (int) Math.floor(mc.thePlayer.posZ);
		BlockPos b = new BlockPos(leftX, leftY, leftZ);
		return mc.theWorld.getBlockState(b).getBlock()==Blocks.trapdoor;
	}
	
	public boolean iswater() {
		int leftX = (int) Math.floor(mc.thePlayer.posX);
		int leftY = (int) Math.floor(mc.thePlayer.posY);
		int leftZ = (int) Math.floor(mc.thePlayer.posZ);
		BlockPos b = new BlockPos(leftX+1, leftY, leftZ);
		return mc.theWorld.getBlockState(b).getBlock()==Blocks.water;
	}
	
	public void change() {
		(new Thread(() -> {
			  try {
				  if(Config.Failsafe.getBoolean())
					  failsafe++;
				  Thread.sleep((50 + Monitor.random.nextInt(20)));
				  if(Config.Farming_Type.getString().equals("Wheat")||Config.Farming_Type.getString().equals("Potato")) 
						wp.sight();
				  else 
						cw.sight();
				  KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), true);
				  Thread.sleep((50 + Monitor.random.nextInt(20)));
				  if(movingright)
					  KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), true);
				  else 
					  KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), true);
				  movingright = !movingright;
				  Thread.sleep((50 + Monitor.random.nextInt(20)));
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
				  Thread.sleep((350 + Monitor.random.nextInt(20)));
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
				movingright = true;
				movingbackward = false;
				movingforward = false;
				cooldown = false;
				changedir = false;
				failsafe = 0;
				KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(),true);
				if(Config.Farming_Type.getString().equals("Wheat")||Config.Farming_Type.getString().equals("Potato")) {
					movingright = true;
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), true);
					wp.sight();
					Monitor.yaw = wp.Yaw;
				}else {
					movingright = false;
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), true);
					cw.sight();
					Monitor.yaw = cw.Yaw;
				}
			}
			
			if(Monitor.cont) {
				Monitor.cont = false;
				if(Config.Farming_Type.getString().equals("Wheat")||Config.Farming_Type.getString().equals("Potato")) 
					wp.sight();
				else 
					cw.sight();
				KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), true);
				if(movingbackward)
						KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), true);
				else if(movingforward)
						KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
				else if(movingright)
						KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), true);
				else 
						KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), true);
			}
			
			if(!cooldown) {
				if(edge()) {
					cooldown = true;
					(new Thread(() -> {
						  try {
							  Thread.sleep((50 + Monitor.random.nextInt(20)));
							  KeyBinding.unPressAllKeys();
							  Thread.sleep((50 + Monitor.random.nextInt(20)));
							  if(mc.thePlayer.posY == 67) { 
									if(iswater()) 
										if(Config.Farming_Type.getString().equals("Wheat")||Config.Farming_Type.getString().equals("Potato")) { 
											KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
											movingforward = true;
										}
										else {
											KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), true);
											movingbackward = true;
										}
									else 
										if(Config.Farming_Type.getString().equals("Wheat")||Config.Farming_Type.getString().equals("Potato")) {
											KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), true);
											movingbackward = true;
										}
										else { 
											KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
											movingforward = true;
										}
							  }else {
								  	change();
							  }
						  } catch (Exception e) {
							  e.printStackTrace();
						  } 
					})).start();
				}
			}	
			
			if(movingforward||movingbackward) {
				if(trapdoor()) {
					movingforward = false;
					movingbackward = false;
					(new Thread(() -> {
						  try {
							  Thread.sleep((50 + Monitor.random.nextInt(20)));
							  KeyBinding.unPressAllKeys();
							  Thread.sleep((50 + Monitor.random.nextInt(20)));
							  KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), true);
							  changedir = true;
						  } catch (Exception e) {
							  e.printStackTrace();
						  } 
					})).start();
				}
			}
			
			if(changedir) {
				if(mc.thePlayer.isOffsetPositionInLiquid(0.0, 1.0, 0.0)) {
					changedir = false;
					change();
					(new Thread(() -> {
						try {
							Thread.sleep((350 + Monitor.random.nextInt(20)));
							KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), false);
							Thread.sleep((50 + Monitor.random.nextInt(20)));
							KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
							Thread.sleep((200 + Monitor.random.nextInt(20)));
							KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					})).start();
				}
			}
		}
	}
	
}

