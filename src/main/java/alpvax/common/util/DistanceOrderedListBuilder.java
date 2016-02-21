package alpvax.common.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.common.base.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class DistanceOrderedListBuilder<T extends Entity>
{
	private final Class<T> typeClass;
	private World worldObj;
	private double oX;
	private double oY;
	private double oZ;
	private double r;
	private PredicateList p = new PredicateList();
	
	public DistanceOrderedListBuilder(Class<T> clazz)
	{
		typeClass = clazz;
	}
	
	public DistanceOrderedListBuilder<T> setWorld(World world)
	{
		worldObj = world;
		return this;
	}
	public DistanceOrderedListBuilder<T> circle(double x, double y, double z, final double radius)
	{
		square(x, y, z, radius);
		with(new Predicate<T>() {
			@Override
			public boolean apply(T input) {
				return input.getDistanceSq(oX, oY, oZ) <= radius * radius;
			}
		});
		return this;
	}
	public DistanceOrderedListBuilder<T> square(double x, double y, double z, double radius)
	{
		oX = x;
		oY = y;
		oZ = z;
		r = radius;
		return this;
	}
	public DistanceOrderedListBuilder<T> with(Predicate<T> filter)
	{
		p.add(filter);
		return this;
	}
	
	public List<T> build()
	{
		List<T> list = worldObj.getEntitiesWithinAABB(typeClass, new AxisAlignedBB(oX - r, oY - r, oZ - r, oX + r, oY + r, oZ + r), p);
		list.sort(new Comparator<T>() {
			@Override
			public int compare(T arg0, T arg1) {
				return Double.valueOf(arg0.getDistanceSq(oX, oY, oZ)).compareTo(Double.valueOf(arg1.getDistanceSq(oX, oY, oZ)));
			}
		});
		return list;
	}
	
	public class PredicateList implements Predicate<T>
	{
		private List<Predicate<T>> list = new ArrayList<Predicate<T>>();
		
		public void add(Predicate<T> p)
		{
			list.add(p);
		}
		
		@Override
		public boolean apply(T input)
		{
			for(Predicate<T> p : list)
			{
				if(!p.apply(input))
				{
					return false;
				}
			}
			return true;
		}

	}
}
