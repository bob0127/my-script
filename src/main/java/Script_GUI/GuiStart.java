package Script_GUI;

import Mining0Farming.Priority;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class GuiStart extends GuiScreen {
	public void initGui() {
		super.initGui();
		ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
		int width = scaledResolution.getScaledWidth();
		int height = scaledResolution.getScaledHeight();
		this.buttonList.add(new BetterButton(0, width / 2 - 95, height / 4 + 110, 190, 20, "Farming"));
		this.buttonList.add(new BetterButton(1, width / 2 - 95, height / 4 + 135, 190, 20, "Mining"));
	}
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
	    String yung = "bob script";
	    String ver = "1.0";
	    ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
	    int width = scaledResolution.getScaledWidth();
	    int height = scaledResolution.getScaledHeight();
	    super.drawScreen(mouseX, mouseY, partialTicks);
	    FontUtils.drawBackground(this.width, this.height,90);
	    float scale = 5.0F;
	    int stringWidth = this.mc.fontRendererObj.getStringWidth(yung);
	    if (stringWidth * scale > width * 0.9F)
	      scale = width * 0.9F / stringWidth; 
	    GlStateManager.scale(scale, scale, 0.0F);
	    FontUtils.drawCenteredString(yung, width / 2.0F / scale, (height / 4.0F - 75.0F) / scale, 14033984);
	    FontUtils.drawCenteredString(ver, width / 2.0F / scale, (height / 2.7F - 60.0F) / scale, 14033984);
	    GlStateManager.scale(1.0F / scale, 1.0F / scale, 0.0F);
	    for (GuiButton button : this.buttonList)
	      button.drawButton(this.mc, mouseX, mouseY); 
	  }
	  
	  protected void actionPerformed(GuiButton button) {
	    switch(button.id) {
	    	case 0:
	    		GuiScreen newGui = new FarmingGui();
	    		Minecraft.getMinecraft().displayGuiScreen(newGui);
	    		newGui = null;
	    		break;
	    	case 1:
	    		
	    		break;
	    }
	  }
	  
	  public boolean doesGuiPauseGame() {
	    return false;
	  }
}
