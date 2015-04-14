package alpvax.mod.common.util;

import net.minecraft.entity.player.EntityPlayer;

public class PlayerHelper
{
	public static String getPlayerName(EntityPlayer player)
	{
		return player.getDisplayNameString();
	}
}
