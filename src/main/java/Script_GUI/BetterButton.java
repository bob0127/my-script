package Script_GUI;

import java.awt.Color;

import Mining0Farming.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.common.config.Property;

public class BetterButton extends GuiButton {
  public BetterButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
    super(buttonId, x, y, widthIn, heightIn, buttonText);
  }
  
  public void drawButton(Minecraft mc, int mouseX, int mouseY) {
    if (this.visible) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height	);
      drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, this.hovered ? (new Color(85, 85, 85, 90)).getRGB() : (new Color(5, 5, 5, 85)).getRGB());
      mouseDragged(mc, mouseX, mouseY);
      int j = 14737632;
      if (this.packedFGColour != 0) {
        j = this.packedFGColour;
      } else if (!this.enabled) {
        j = 10526880;
      } else if (this.hovered) {
        j = 16777120;
      } 
      drawCenteredString(mc.fontRendererObj, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, j);
    } 
  }
  public void cusdrawButton(Minecraft mc, int mouseX, int mouseY) {
	  if (this.visible) {
	      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	      this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < (this.xPosition + this.width) && mouseY < this.yPosition + this.height	);
	      drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, (new Color(5, 5, 5, 0)).getRGB());
	      mouseDragged(mc, mouseX, mouseY);
	      int j = 14737632;
	      if (this.packedFGColour != 0) {
	        j = this.packedFGColour;
	      } else if (!this.enabled) {
	        j = 10526880;
	      } else if (this.hovered) {
	        j = 16777120;
	      } 	      
	      GlStateManager.scale(1.5D, 1.5D, 1.0D);
	      drawString(mc.fontRendererObj, this.displayString,(int) Math.floor(this.xPosition / 1.5D)+1, (int) Math.floor((this.yPosition + (this.height - 8) / 2) / 1.5D) , j);
	      GlStateManager.scale(1.0D / 1.5D,1.0D / 1.5D, 1.0D);
	  } 
  }
  public void typedrawButton(Minecraft mc, int mouseX, int mouseY ,int min) {
	  if (this.visible) {
	      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	      this.hovered = (mouseX >= this.xPosition && mouseY >= (this.yPosition - (26 * min))&& mouseX < this.xPosition + this.width && mouseY < (this.yPosition + this.height - (26 * min)));
	      drawRect(this.xPosition, this.yPosition - (26 * min), this.xPosition + this.width, this.yPosition + this.height - (26 * min), this.hovered ? (new Color(85, 85, 85, 90)).getRGB() : (new Color(5, 5, 5, 85)).getRGB());
	      mouseDragged(mc, mouseX, mouseY);
	      int j = 14737632;
	      if (this.packedFGColour != 0) {
	        j = this.packedFGColour;
	      } else if (!this.enabled) {
	        j = 10526880;
	      } else if (this.hovered) {
	        j = 16777120;
	      } 
	      drawCenteredString(mc.fontRendererObj, this.displayString, this.xPosition + this.width / 2, this.yPosition - (26 * min) + (this.height - 8) / 2, j);
	  } 
  }
  public void calltypedrawButton(Minecraft mc, int mouseX, int mouseY) {
	  if (this.visible) {
	      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	      drawRect(this.xPosition-1, this.yPosition-1, this.xPosition + this.width + 1, this.yPosition + this.height + 1, (new Color(35, 35, 35, 255)).getRGB());
	      drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, (new Color(24, 24, 24, 255)).getRGB());
	      mouseDragged(mc, mouseX, mouseY);	      
	      drawCenteredString(mc.fontRendererObj, Config.Farming_Type.getString(), this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, 14737632);
	  } 
  }
  
  public void truefalsebutton(Minecraft mc, int mouseX, int mouseY , Property x) {
	  if (this.visible) {
	      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    	  drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, x.getBoolean() ? (new Color(41, 151, 255, 255)).getRGB() : (new Color(187, 187, 187, 255)).getRGB());
	      if(x.getBoolean()) {
	    	  drawRect(this.xPosition + 1, this.yPosition + 1, this.xPosition + (int) Math.floor(this.width / 2), this.yPosition + this.height - 1,	(new Color(41, 151, 255, 255)).getRGB());
		      drawRect(this.xPosition + (int) Math.floor(this.width / 2), this.yPosition + 1, this.xPosition + this.width - 1, this.yPosition + this.height - 1, (new Color(35, 35, 35, 255)).getRGB());
	      }else {
	    	  drawRect(this.xPosition + 1, this.yPosition + 1, this.xPosition + (int) Math.floor(this.width / 2), this.yPosition + this.height - 1, (new Color(35, 35, 35, 255)).getRGB());
		      drawRect(this.xPosition + (int) Math.floor(this.width / 2), this.yPosition + 1, this.xPosition + this.width - 1, this.yPosition + this.height - 1, (new Color(187, 187, 187, 255)).getRGB());
	      }
	      mouseDragged(mc, mouseX, mouseY);	      
	  } 
  }
}