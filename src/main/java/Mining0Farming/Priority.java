package Mining0Farming;

import Script.Cactus;
import Script.Cocoa_bean;
import Script.Melon_Pumpkin;
import Script.Mushroom;
import Script.Sugar_Cane;
import Script.Wheat_Potato_Carrot_Wart;
import Script_GUI.GuiCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.ICommand;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.GuiScrollingList;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod(modid = Priority.MODID, version = Priority.VERSION)
public class Priority
{
    public static final String MODID = "bobscript";
    public static final String VERSION = "1.0";
    public static Config configfile;
    public static GuiScreen displayScreen;
    KeyBinding farming = new KeyBinding("Farming",0,"bobscript");
	KeyBinding mining = new KeyBinding("Mining",0,"bobscript");
	public static boolean farmingActive=false;
	public static boolean miningActive=false;
	public static String save = "";
	public Monitor monitor = new Monitor();
	public Cocoa_bean cocoa = new Cocoa_bean();
	public Cactus cactus = new Cactus();
	public Wheat_Potato_Carrot_Wart wheat_potato_carrot_wart = new Wheat_Potato_Carrot_Wart();
	public Melon_Pumpkin melon_pumpkin = new Melon_Pumpkin();
	public Mushroom mushroom = new Mushroom();
	public Sugar_Cane sugar_cane = new Sugar_Cane();
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		configfile = new Config();
        configfile.preInit(event);
	}
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	ClientRegistry.registerKeyBinding(farming);
    	ClientRegistry.registerKeyBinding(mining); 
    	MinecraftForge.EVENT_BUS.register(this);
		ClientCommandHandler.instance.registerCommand((ICommand)new GuiCommand());
    }
    
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
    	if (displayScreen != null) {
    		Minecraft.getMinecraft().displayGuiScreen(displayScreen);
    		displayScreen = null;
    	}
    }
    
    @SubscribeEvent
    public void key(InputEvent.KeyInputEvent event) {
    	if(farming.isKeyDown()) {
    		if(farmingActive) {
    			farmingActive = false;
    			Monitor.cont = true;
    			MinecraftForge.EVENT_BUS.unregister(monitor);
    			(Minecraft.getMinecraft()).thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.YELLOW + "Farming Stop!"));
    			KeyBinding.unPressAllKeys();
    		}else {
    			farmingActive = true;
    			Monitor.macro = false;
    			Monitor.checkplace();
    			(Minecraft.getMinecraft()).thePlayer.sendChatMessage("/sethome");
    			(Minecraft.getMinecraft()).thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.YELLOW +"Farming Start!"));
    			if(!save.equals(Config.Farming_Type.getString())) {
    				Monitor.cont = false;
    				Monitor.start = true;
    				switch(save) {
    				case "Potato":
    			    	MinecraftForge.EVENT_BUS.unregister(wheat_potato_carrot_wart);
        				break;
        			case "Carrot":
        		    	MinecraftForge.EVENT_BUS.unregister(wheat_potato_carrot_wart);
        				break;
        			case "Wheat":
        		    	MinecraftForge.EVENT_BUS.unregister(wheat_potato_carrot_wart);
        				break;
        			case "Nether Wart":
        		    	MinecraftForge.EVENT_BUS.unregister(wheat_potato_carrot_wart);
        				break;
        			case "Cactus":
        		    	MinecraftForge.EVENT_BUS.unregister(cactus);
        				break;
        			case "Sugar Cane":
        		    	MinecraftForge.EVENT_BUS.unregister(sugar_cane);
        				break;
        			case "Melon":
        		    	MinecraftForge.EVENT_BUS.unregister(melon_pumpkin);
        				break;
        			case "Pumpkin":
        		    	MinecraftForge.EVENT_BUS.unregister(melon_pumpkin);
        				break;
        			case "Cocoa Bean":
        		    	MinecraftForge.EVENT_BUS.unregister(cocoa);
        				break;
        			case "Red Mush":
        		    	MinecraftForge.EVENT_BUS.unregister(mushroom);
        				break;
        			case "Brown Mush":
        		    	MinecraftForge.EVENT_BUS.unregister(mushroom);
        				break;
        			default:
        				break;
    				}
    				save = Config.Farming_Type.getString();
    				switch(Config.Farming_Type.getString()) {
        			case "Potato":
    			    	MinecraftForge.EVENT_BUS.register(wheat_potato_carrot_wart);
        				break;
        			case "Carrot":
        		    	MinecraftForge.EVENT_BUS.register(wheat_potato_carrot_wart);
        				break;
        			case "Wheat":
        		    	MinecraftForge.EVENT_BUS.register(wheat_potato_carrot_wart);
        				break;
        			case "Nether Wart":
        		    	MinecraftForge.EVENT_BUS.register(wheat_potato_carrot_wart);
        				break;
        			case "Cactus":
        		    	MinecraftForge.EVENT_BUS.register(cactus);
        				break;
        			case "Sugar Cane":
        		    	MinecraftForge.EVENT_BUS.register(sugar_cane);
        				break;
        			case "Melon":
        		    	MinecraftForge.EVENT_BUS.register(melon_pumpkin);
        				break;
        			case "Pumpkin":
        		    	MinecraftForge.EVENT_BUS.register(melon_pumpkin);
        				break;
        			case "Cocoa Bean":
        		    	MinecraftForge.EVENT_BUS.register(cocoa);
        				break;
        			case "Red Mush":
        		    	MinecraftForge.EVENT_BUS.register(mushroom);
        				break;
        			case "Brown Mush":
        		    	MinecraftForge.EVENT_BUS.register(mushroom);
        				break;
        			default:
            			(Minecraft.getMinecraft()).thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.RED +"Farming Didn't Start! Choose one crop"));
        				break;
        			}
    			}
    			MinecraftForge.EVENT_BUS.register(monitor);
    		}
    	}
    	if(mining.isKeyDown()) {
    		if(miningActive) {
    			miningActive=false;
    			(Minecraft.getMinecraft()).thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GRAY+"Mining Stop!"));
    			
    		}else {
    			miningActive=true;
    			(Minecraft.getMinecraft()).thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GRAY+"Mining Start!"));

    		}
    		
    	}
    }
    
}
