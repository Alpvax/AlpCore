package alpvax.common.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;


public class EntityHelper
{
	public static Entity getEntityByID(int entityID, World world)
	{
		for(Object o : world.getLoadedEntityList())
		{
			if(((Entity)o).getEntityId() == entityID)
			{
				return ((Entity)o);
			}
		}
		return null;
	}

	public static String getPlayerName(EntityPlayer player)
	{
		return player.getDisplayNameString();
	}
}
