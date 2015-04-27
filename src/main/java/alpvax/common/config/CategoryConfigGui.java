package alpvax.common.config;

import java.util.List;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;


/** This GuiConfig object specifies the configID of the object and as such will force-save when it is closed. The parent GuiConfig object's entryList will also be refreshed to reflect the changes. */
public class CategoryConfigGui extends GuiConfig
{
	public CategoryConfigGui(GuiConfig parentScreen, ConfigCategory category, IConfigElement configElement, String title)
	{
		this(parentScreen, category, configElement, title, "");
	}

	public CategoryConfigGui(GuiConfig parentScreen, ConfigCategory category, List<IConfigElement> configElements, IConfigElement configElement, String title)
	{
		this(parentScreen, category, configElements, configElement, title, "");
	}

	public CategoryConfigGui(GuiConfig parentScreen, ConfigCategory category, IConfigElement configElement, String title, String titleLine2)
	{
		super(parentScreen, new ConfigElement(category).getChildElements(), parentScreen.modID, category.getName(), configElement.requiresWorldRestart() || parentScreen.allRequireWorldRestart, configElement.requiresMcRestart() || parentScreen.allRequireMcRestart, title, titleLine2);
	}

	public CategoryConfigGui(GuiConfig parentScreen, ConfigCategory category, List<IConfigElement> configElements, IConfigElement configElement, String title, String titleLine2)
	{
		super(parentScreen, configElements, parentScreen.modID, category.getName(), configElement.requiresWorldRestart() || parentScreen.allRequireWorldRestart, configElement.requiresMcRestart() || parentScreen.allRequireMcRestart, title, titleLine2);
	}
}
