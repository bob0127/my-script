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

public class Sugar_Cane {
	public static Minecraft mc = Minecraft.getMinecraft();
	public Rotation rotation = new Rotation(135.0F,0.0F);
	public double PX;
	public double PY;
	public double PZ;
	public boolean movingbackward = true;
	public boolean moving = false;
	public boolean stuck = false;
	public boolean cooldown = false;
	public boolean interval = false;
	public int setnumber = Monitor.random.nextInt(3)+9;
	public int failsafe = 0;
	
	public boolean edge() {
		double Z = 0;
		if(movingbackward)
			Z = 0.4;
		else 
			Z = -0.4;
		int leftX = (int) Math.floor(mc.thePlayer.posX);
		int leftY = (int) Math.floor(mc.thePlayer.posY);
		int leftZ = (int) Math.floor(mc.thePlayer.posZ+Z);
		BlockPos b = new BlockPos(leftX, leftY, leftZ);
		return mc.theWorld.getBlockState(b).getBlock()==Blocks.quartz_block;
	}
	
	public boolean go() {
		double X = 0;
		if(mc.thePlayer.posY == 68) 
			X = -0.4;
		else 
			X = 0.4;
		int leftX = (int) Math.floor(mc.thePlayer.posX+X);
		int leftY = (int) Math.floor(mc.thePlayer.posY);
		int leftZ = (int) Math.floor(mc.thePlayer.posZ);
		BlockPos b = new BlockPos(leftX, leftY, leftZ);
		return mc.theWorld.getBlockState(b).getBlock()==Blocks.sea_lantern;
	}
	
	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		if(mc==null||mc.thePlayer==null)
			 return;
		
		if(Monitor.macro) 
			return;
		
		if(failsafe == setnumber)
			return;
		
		System.out.println((int) Math.floor(mc.thePlayer.posX+0.4));
		if(Monitor.isOnGarden&&Priority.farmingActive) {
			if(Monitor.start) {
				Monitor.start = false;
				movingbackward = true;
				moving = false;
				stuck = false;
				cooldown = false;
				interval = false;
				failsafe = 0;
				rotation.sight();
				Monitor.yaw = rotation.Yaw;
				KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(),true);
				if(movingbackward)
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), true);
				else 
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), true);
			}
		
			if(Monitor.cont) {
				Monitor.cont = false;
				rotation.sight();
				KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(),true);
				if(movingbackward)
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), true);
				else 
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), true);
			}
			
			if(stuck) {
				stuck = false;
				KeyBinding.unPressAllKeys();
				(new Thread(() -> {
					try {
						KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), true);
						Thread.sleep((300 + Monitor.random.nextInt(20)));
						KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), true);
						Thread.sleep((200 + Monitor.random.nextInt(20)));
						KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), false);
						Thread.sleep((200 + Monitor.random.nextInt(20)));
						KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), false);
						Thread.sleep((50 + Monitor.random.nextInt(20)));
						KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
						Thread.sleep((200 + Monitor.random.nextInt(20)));
						KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), true);
						Thread.sleep((200 + Monitor.random.nextInt(20)));
						KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
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
					if(mc.thePlayer.posY == 68 ) { 
						(new Thread(() -> {
							try {
								Thread.sleep((50 + Monitor.random.nextInt(20)));
								if(movingbackward) {
									KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), false);
									Thread.sleep((50 + Monitor.random.nextInt(20)));
									KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), true);
								}else {
									KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), false);
									Thread.sleep((50 + Monitor.random.nextInt(20)));
									KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
								}
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						})).start();
					}
					moving = true;
				}
			}
			
			if(moving) {
				if(go()){
					moving = false;
					(new Thread(() -> {
						try {		
							if(Config.Failsafe.getBoolean())
								  failsafe++;
							Thread.sleep((50 + Monitor.random.nextInt(20)));
							KeyBinding.unPressAllKeys();
							Thread.sleep((50 + Monitor.random.nextInt(20)));
							rotation.sight();
							KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), true);
							if(movingbackward) 
								KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), true);
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
							mc.thePlayer.sendChatMessage("/sethome");
							cooldown = false;
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					})).start();
				}
			}
		}
	}
}
