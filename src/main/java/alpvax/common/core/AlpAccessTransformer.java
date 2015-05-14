package alpvax.common.core;

import java.io.IOException;

import net.minecraftforge.fml.common.asm.transformers.AccessTransformer;


public class AlpAccessTransformer extends AccessTransformer
{
	public AlpAccessTransformer() throws IOException
	{
		super("Alp_at.cfg");
	}
}
