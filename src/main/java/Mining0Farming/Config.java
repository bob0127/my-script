package Mining0Farming;

import java.io.File;

import net.minecraftforge.client.model.ItemLayerModel.Loader;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Config {
	public static Configuration config;
	public static Property Farming_Type;
	public static Property Failsafe;
	public static Property Jacob;
	public static Property After_Check_Resume;
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		File configFile = new File(event.getModConfigurationDirectory(), "bobscript");
		config = new Configuration(configFile);
		defaultConfig();
	}
	
	public void defaultConfig() {
		config.load();
		String[] Type = {"Potato", "Carrot", "Wheat", "Nether Wart", "Cactus", "Sugar Cane", "Melon", "Pumpkin", "Cocoa Bean", "Red Mush", "Brown Mush"};
		Farming_Type = config.get("Farming", "Farming Type", "", "Choose one to start", Type);
		Failsafe = config.get("Farming", "Failsafe", false, "Risk up to you");
		Jacob = config.get("Farming", "Jacob", false, "Auto stop FailSafe while Jacob");
		After_Check_Resume = config.get("Farming", "After_Check_Resume", false, "After macro check 10 mins resume");
		config.save();
	}
}
