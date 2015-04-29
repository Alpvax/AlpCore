package alpvax.common.util;

import java.security.InvalidParameterException;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.google.common.base.Predicate;


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

	public static MovingObjectPosition getLookingAt(EntityLivingBase lookingEntity, double maxDistance, Predicate<Entity> filter)
	{
		if(filter == null)
		{
			filter = EntitySelector.ALL;
		}
		Vec3 start = new Vec3(lookingEntity.posX, lookingEntity.posY + lookingEntity.getEyeHeight(), lookingEntity.posZ);
		Vec3 look = lookingEntity.getLookVec();
		Vec3 end = start.addVector(look.xCoord * maxDistance, look.yCoord * maxDistance, look.zCoord * maxDistance);
		MovingObjectPosition mop = lookingEntity.worldObj.rayTraceBlocks(start, end);
		if(mop != null)
		{
			end = new Vec3(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
		}
		if(!lookingEntity.worldObj.isRemote)
		{
			Entity entity = null;
			@SuppressWarnings("unchecked")
			List<Entity> list = lookingEntity.worldObj.getEntitiesWithinAABBExcludingEntity(lookingEntity, new AxisAlignedBB(start.xCoord, start.yCoord, start.zCoord, end.xCoord, end.yCoord, end.zCoord));
			double d0 = Double.MAX_VALUE;
			for(int j = 0; j < list.size(); ++j)
			{
				Entity entity1 = list.get(j);

				if(filter.apply(entity1))
				{
					float f = 0.3F;
					AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand(f, f, f);
					MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(start, end);

					if(movingobjectposition1 != null)
					{
						double d1 = start.distanceTo(movingobjectposition1.hitVec);

						if(d1 < d0)
						{
							entity = entity1;
							d0 = d1;
						}
					}
				}
			}
			if(entity != null)
			{
				mop = new MovingObjectPosition(entity);
			}
		}
		return mop;
	}

	public static Entity getClosestEntity(World world, MovingObjectPosition mop, double searchRadius, Predicate<Entity> filter)
	{
		switch(mop.typeOfHit)
		{
			case BLOCK:
				if(searchRadius == 0D)
				{
					return null;
				}
				double x = mop.hitVec.xCoord;
				double y = mop.hitVec.yCoord;
				double z = mop.hitVec.zCoord;
				@SuppressWarnings("unchecked")
				Iterator<Entity> i = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(x - searchRadius, y - searchRadius, z - searchRadius, x + searchRadius, y + searchRadius, z + searchRadius), filter).iterator();
				Entity entity = null;
				double d = Double.MAX_VALUE;
				while(i.hasNext())
				{
					Entity e = i.next();
					double d1 = e.getDistanceSq(x, y, z);
					if(d1 < d)
					{
						d = d1;
						entity = e;
					}
				}
				return entity;
			case ENTITY:
				return mop.entityHit;
			default:
				return null;
		}
	}

	public static abstract class EntitySelector implements Predicate<Entity>
	{
		public static final EntitySelector ALL = new EntitySelector(){
			@Override
			protected boolean isEntityValid(Entity e)
			{
				return true;
			}
		};
		public static final EntitySelector PLAYER = new EntitySelector(){
			@Override
			protected boolean isEntityValid(Entity e)
			{
				return e instanceof EntityPlayer;
			}
		};
		public static final EntitySelector NOT_PLAYER = new EntitySelector(){
			@Override
			protected boolean isEntityValid(Entity e)
			{
				return !(e instanceof EntityPlayer);
			}
		};

		protected abstract boolean isEntityValid(Entity e);

		@Override
		public boolean apply(Entity input)
		{
			return isEntityValid(input);
		}
	}
}
