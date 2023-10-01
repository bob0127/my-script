package Script_GUI;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;

import Mining0Farming.Config;
import Mining0Farming.Monitor;
import Mining0Farming.Priority;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.client.GuiScrollingList;

public class FarmingType extends GuiScrollingList{
	public int width;
	public int height;
	public int bottom;
	public int left;
	public int top;
	public int slide = 0;
	public int min;
	public boolean first;
	public Config config = new Config();
	public List<GuiButton> list = new ArrayList<>(); 
	public static Minecraft mc = Minecraft.getMinecraft();
	public FarmingType(Minecraft client, int width, int height, int top, int bottom, int left, int entryHeight,
			int screenWidth, int screenHeight) {
		super(client, width, height, top, bottom, left, entryHeight, screenWidth, screenHeight);
		this.top = top;
		this.width = width;
		this.height = height;
		this.bottom = bottom;
		this.left = left;
		int i = 0;
		for(String a : Config.Farming_Type.getValidValues()) {
			list.add(new BetterButton(i, left, (top+(i * 26)) , width, 26, a));
			i++; 
		}
		
	}

	@Override
	protected int getSize() {
		return 11;
	}

	@Override
	protected void elementClicked(int index, boolean doubleClick) {
		if(Priority.configfile.config != null) {
			Priority.configfile.Farming_Type.setValue(list.get(index).displayString);
			Priority.configfile.config.save();
			Priority.farmingActive = false;
		}
		FarmingGui.showList=false;
	}

	@Override
	protected boolean isSelected(int index) {
		return false;
	}

	@Override
	protected void drawBackground() {
		Gui.drawRect(left-2, top, left+width+2, bottom, (new Color(35, 35, 35, 255)).getRGB());
		first = true;
	}

	@Override
	protected void drawSlot(int slotIdx, int entryRight, int slotTop, int slotBuffer, Tessellator tess) {
		if(first) {
			min=slotIdx;
			first = false;
		}
		((BetterButton) list.get(slotIdx)).typedrawButton(mc, mouseX, mouseY , min);
	}
}