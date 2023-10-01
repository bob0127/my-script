package Script_GUI;



import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class FontUtils {
	public static void drawBackground(int width, int height, int alpha) {
	    Gui.drawRect(0, 0, width, height, (new Color(0, 0, 0, alpha)).getRGB());
	}
	
	public static void drawCenteredString(String text, float x, float y, int color) {
		(Minecraft.getMinecraft()).fontRendererObj.drawString(text, (int)(x - (Minecraft.getMinecraft()).fontRendererObj.getStringWidth(text) / 2.0F), (int)y, color);
	}
	
	public static void cusdrawBackground(int lx,int ly,int rx, int ry, int R, int G, int B, int alpha) {
	    Gui.drawRect(lx, ly, rx, ry, (new Color(R, G, B, alpha)).getRGB());
	}
}
