package Script_GUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import Mining0Farming.Config;
import Mining0Farming.Priority;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiClickableScrolledSelectionListProxy;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fml.client.GuiScrollingList;


public class FarmingGui extends GuiScreen{
    public static boolean showList = false;
    public List<GuiButton> hey = new ArrayList<>();
    public static GuiScrollingList dis;
    
	public void initGui() {
		super.initGui();
		ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
		int width = scaledResolution.getScaledWidth();
		int height = scaledResolution.getScaledHeight();
		this.buttonList.add(new BetterButton(0,((int) Math.floor(width*0.07D)+10) , ((int) Math.floor(height*0.125F)+10)  ,  (int) Math.floor(this.mc.fontRendererObj.getStringWidth("Farming Type") * 1.5D), 30, "Farming Type"));	
		this.buttonList.add(new BetterButton(1,(int) Math.floor(width*0.75F) , ((int) Math.floor(height*0.171875D)-2)  ,  (int) Math.floor(this.mc.fontRendererObj.getStringWidth("Nether Wart") * 1.5D), 30, Config.Farming_Type.getString()));
		this.buttonList.add(new BetterButton(2,(int) Math.floor(width*0.765F) , ((int) Math.floor(height*0.30D))  ,  (int) Math.floor(this.mc.fontRendererObj.getStringWidth("Nether Wart")), 20, ""));
		this.buttonList.add(new BetterButton(3,(int) Math.floor(width*0.765F) , ((int) Math.floor(height*0.425D))  ,  (int) Math.floor(this.mc.fontRendererObj.getStringWidth("Nether Wart")), 20, ""));
		this.buttonList.add(new BetterButton(4,(int) Math.floor(width*0.765F) , ((int) Math.floor(height*0.550D))  ,  (int) Math.floor(this.mc.fontRendererObj.getStringWidth("Nether Wart")), 20, ""));
		dis = new FarmingType(mc, (int) Math.floor(this.mc.fontRendererObj.getStringWidth("Nether Wart") * 1.5D) , 130, ((int) Math.floor(height*0.171875D)+28), ((int) Math.floor(height*0.171875D)+158), (int) Math.floor(width*0.75F), 26, width, height);
	}
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
	    ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
	    int width = scaledResolution.getScaledWidth();
	    int height = scaledResolution.getScaledHeight();
	    super.drawScreen(mouseX, mouseY, partialTicks);
	    FontUtils.cusdrawBackground(0, 0, width, height, 24, 24, 24, 255);
	    FontUtils.cusdrawBackground((int) Math.floor(width*0.07D), (int) Math.floor(height*0.125F), (int) Math.floor(width*0.93D), (int) Math.floor(height*0.875F), 35, 35, 35, 255);
	    FontUtils.cusdrawBackground((int) Math.floor(width*0.07D)+3, (int) Math.floor(height*0.125F)+3, (int) Math.floor(width*0.93D)-3, (int) Math.floor(height*0.875F)-3, 24, 24, 24, 255);
	    FontUtils.cusdrawBackground((int) Math.floor(width*0.3125D), ((int) Math.floor(height*0.125F)+10), (int) Math.floor(width*0.875D), (int) Math.floor(height*0.25F), 35, 35, 35, 255);
	    FontUtils.cusdrawBackground((int) Math.floor(width*0.3125D)+2, ((int) Math.floor(height*0.125F)+12), (int) Math.floor(width*0.875D)-2, (int) Math.floor(height*0.25F)-2, 24, 24, 24, 255);
	    FontUtils.cusdrawBackground((int) Math.floor(width*0.3125D), ((int) Math.floor(height*0.25F)+5), (int) Math.floor(width*0.875D), (int) Math.floor(height*0.375F), 35, 35, 35, 255);
	    FontUtils.cusdrawBackground((int) Math.floor(width*0.3125D)+2, ((int) Math.floor(height*0.25F)+7), (int) Math.floor(width*0.875D)-2, (int) Math.floor(height*0.375F)-2, 24, 24, 24, 255);
	    FontUtils.cusdrawBackground((int) Math.floor(width*0.3125D), ((int) Math.floor(height*0.375F)+5), (int) Math.floor(width*0.875D), (int) Math.floor(height*0.5F), 35, 35, 35, 255);
	    FontUtils.cusdrawBackground((int) Math.floor(width*0.3125D)+2, ((int) Math.floor(height*0.375F)+7), (int) Math.floor(width*0.875D)-2, (int) Math.floor(height*0.5F)-2, 24, 24, 24, 255);
	    FontUtils.cusdrawBackground((int) Math.floor(width*0.3125D), ((int) Math.floor(height*0.5F)+5), (int) Math.floor(width*0.875D), (int) Math.floor(height*0.625F), 35, 35, 35, 255);
	    FontUtils.cusdrawBackground((int) Math.floor(width*0.3125D)+2, ((int) Math.floor(height*0.5F)+7), (int) Math.floor(width*0.875D)-2, (int) Math.floor(height*0.625F)-2, 24, 24, 24, 255);
	    GlStateManager.scale(1.5D, 1.5D, 1.0D);
	    drawString(this.mc.fontRendererObj, Config.Farming_Type.getName(), (int) Math.floor((width*0.3125D+2)/1.5D)+5, (int) Math.floor((height*0.125F+10)/1.5D)+5, 14737632);
	    drawString(this.mc.fontRendererObj, Config.Farming_Type.comment, (int) Math.floor((width*0.3125D+2)/1.5D)+5, (int) Math.floor((height*0.125F+10)/1.5D)+22, 14737632);
	    drawString(this.mc.fontRendererObj, Config.Failsafe.getName(), (int) Math.floor((width*0.3125D+2)/1.5D)+5, (int) Math.floor((height*0.25F+10)/1.5D)+5, 14737632);
	    drawString(this.mc.fontRendererObj, Config.Failsafe.comment, (int) Math.floor((width*0.3125D+2)/1.5D)+5, (int) Math.floor((height*0.25F+10)/1.5D)+22, 14737632);
	    drawString(this.mc.fontRendererObj, Config.Jacob.getName(), (int) Math.floor((width*0.3125D+2)/1.5D)+5, (int) Math.floor((height*0.375F+10)/1.5D)+5, 14737632);
	    drawString(this.mc.fontRendererObj, Config.Jacob.comment, (int) Math.floor((width*0.3125D+2)/1.5D)+5, (int) Math.floor((height*0.375F+10)/1.5D)+22, 14737632);
	    drawString(this.mc.fontRendererObj, Config.After_Check_Resume.getName(), (int) Math.floor((width*0.3125D+2)/1.5D)+5, (int) Math.floor((height*0.5F+10)/1.5D)+5, 14737632);
	    drawString(this.mc.fontRendererObj, Config.After_Check_Resume.comment, (int) Math.floor((width*0.3125D+2)/1.5D)+5, (int) Math.floor((height*0.5F+10)/1.5D)+22, 14737632);
	    GlStateManager.scale(1.0D / 1.5D,1.0D / 1.5D, 1.0D);
	    ((BetterButton) this.buttonList.get(0)).cusdrawButton(this.mc, mouseX, mouseY);
	    ((BetterButton) this.buttonList.get(1)).calltypedrawButton(this.mc, mouseX, mouseY);
	    ((BetterButton) this.buttonList.get(2)).truefalsebutton(this.mc, mouseX, mouseY, Config.Failsafe);
	    ((BetterButton) this.buttonList.get(3)).truefalsebutton(this.mc, mouseX, mouseY, Config.Jacob);
	    ((BetterButton) this.buttonList.get(4)).truefalsebutton(this.mc, mouseX, mouseY, Config.After_Check_Resume);
	    if(showList) {
	    	dis.drawScreen(mouseX, mouseY, partialTicks);
	    }
	}
	  
	  protected void actionPerformed(GuiButton button) {
	    switch(button.id) {
	    	case 0:
	    		break;
	    	case 1:
	    		showList = !showList;
	    		break;
	    	case 2:
	    		if(!showList) {
	    			Config.Failsafe.setValue(!Config.Failsafe.getBoolean());
	    			Priority.configfile.config.save();
	    		}
	    		break;
	    	case 3:
	    		if(!showList) {
	    			Config.Jacob.setValue(!Config.Jacob.getBoolean());
	    			Priority.configfile.config.save();
	    		}
	    		break;
	    	case 4:
	    		Config.After_Check_Resume.setValue(!Config.After_Check_Resume.getBoolean());
				Priority.configfile.config.save();
	    		break;
	    		
	    }
	  }
	  
	  public boolean doesGuiPauseGame() {
	    return false;
	  }
	  
	  
}
