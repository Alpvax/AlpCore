package alpvax.common.config;

import java.util.List;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.GuiConfigEntries.IConfigEntry;
import net.minecraftforge.fml.client.config.IConfigElement;

public class SimpleCategoryElement extends DummyCategoryElement
{
	public SimpleCategoryElement(ConfigCategory category, List<IConfigElement> childElements)
	{
		super(category.getName(), category.getLanguagekey(), childElements);
	}

	public SimpleCategoryElement(ConfigCategory category, Class<? extends IConfigEntry> customListEntryClass)
	{
		super(category.getName(), category.getLanguagekey(), customListEntryClass);
	}

	public SimpleCategoryElement(ConfigCategory category, List<IConfigElement> childElements, Class<? extends IConfigEntry> customListEntryClass)
	{
		super(category.getName(), category.getLanguagekey(), childElements, customListEntryClass);
	}
}
