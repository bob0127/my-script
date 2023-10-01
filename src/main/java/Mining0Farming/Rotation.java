package Mining0Farming;

import net.minecraft.client.Minecraft;

public class Rotation {
	public float Yaw;
	public float Pitch;
	public static Minecraft mc=Minecraft.getMinecraft();
	public Rotation(float yaw, float pitch) {
		Yaw = yaw;
		Pitch = pitch;
	}
	public void sight() {
		if (Yaw == mc.thePlayer.rotationYaw) {
			mc.thePlayer.rotationYaw = Yaw;
			mc.thePlayer.rotationPitch = Pitch;
			return;
		} 
		while (mc.thePlayer.rotationYaw > Yaw) {
			mc.thePlayer.rotationYaw -= 0.1f;
		} 
		while (mc.thePlayer.rotationYaw < Yaw) {
			mc.thePlayer.rotationYaw += 0.1f;
		}
		while (mc.thePlayer.rotationPitch > Pitch) {
			mc.thePlayer.rotationPitch -= 0.1f;
		} 
		while (mc.thePlayer.rotationPitch < Pitch) {
			mc.thePlayer.rotationPitch += 0.1f;
		}	
		mc.thePlayer.rotationYaw = Yaw;
		mc.thePlayer.rotationPitch = Pitch;
	}
	
}