package alpvax.common.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class WeightedRandomList<T>
{
	private int totalWeight = 0;
	public List<WeightEntry> list = new ArrayList<WeightEntry>();

	public void addWeightedItem(int weight, T object)
	{
		list.add(new WeightEntry(totalWeight, totalWeight += weight, object));
	}

	public T getRandomObject(Random rand)
	{
		if(!list.isEmpty())
		{
			int r = rand.nextInt(totalWeight);
			Iterator<WeightEntry> i = list.iterator();
			while(i.hasNext())
			{
				WeightEntry e = i.next();
				if(e.inRange(r))
				{
					return e.getObject();
				}
			}
		}
		return null;
	}

	private class WeightEntry
	{
		private int min;
		private int max;
		private T obj;

		public WeightEntry(int min, int max, T object)
		{
			this.min = min;
			this.max = max;
			this.obj = object;
		}

		public boolean inRange(int i)
		{
			return i >= min && i < max;
		}

		public T getObject()
		{
			return obj;
		}
	}
}
