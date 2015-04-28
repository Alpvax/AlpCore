package alpvax.common.util;

import java.security.InvalidParameterException;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
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
				return (Entity)o;
			}
		}
		return null;
	}

	public static String getPlayerName(EntityPlayer player)
	{
		return player.getDisplayNameString();
	}

	/**
	 * @param overrideType 0 = override with new modifier, 1 = add values, 2 = multiply values
	 */
	public static void applyAttributeModifier(EntityLivingBase entity, IAttribute attribute, AttributeModifier modifier, int overrideType)
	{
		IAttributeInstance att = entity.getAttributeMap().getAttributeInstance(attribute);
		AttributeModifier mod = att.getModifier(modifier.getID());
		if(mod != null)
		{
			att.removeModifier(mod);
		}
		if(mod == null || overrideType == 0)
		{
			mod = modifier;
		}
		else
		{
			switch(overrideType)
			{
				case 1:
					mod = new AttributeModifier(modifier.getID(), modifier.getName(), mod.getAmount() + modifier.getAmount(), modifier.getOperation());
					break;
				case 2:
					mod = new AttributeModifier(modifier.getID(), modifier.getName(), mod.getAmount() * modifier.getAmount(), modifier.getOperation());
					break;
				default:
					throw new InvalidParameterException("overrideType must be 0, 1 or 2! Recieved " + overrideType);
			}
		}
		att.applyModifier(mod);
	}

	/**
	 * @param overrideType 0 = remove modifier completely, 1 = subtract values, 2 = divide values
	 */
	public static void removeAttributeModifier(EntityLivingBase entity, IAttribute attribute, AttributeModifier modifier, int overrideType)
	{
		IAttributeInstance att = entity.getAttributeMap().getAttributeInstance(attribute);
		AttributeModifier mod = att.getModifier(modifier.getID());
		att.removeModifier(mod);
		if(mod == null || overrideType == 0)
		{
			return;
		}
		switch(overrideType)
		{
			case 1:
				mod = new AttributeModifier(modifier.getID(), modifier.getName(), mod.getAmount() - modifier.getAmount(), modifier.getOperation());
				break;
			case 2:
				mod = new AttributeModifier(modifier.getID(), modifier.getName(), mod.getAmount() / modifier.getAmount(), modifier.getOperation());
				break;
			default:
				throw new InvalidParameterException("overrideType must be 0, 1 or 2! Recieved " + overrideType);
		}
		att.applyModifier(mod);
	}

	//TODO:getClosest(EntitySelector)public 

	public abstract static class EntitySelector
	{
		public abstract boolean isEntityValid(Entity e);
	}
}
