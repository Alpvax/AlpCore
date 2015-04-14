package alpvax.common.core;

import net.minecraft.launchwrapper.IClassTransformer;

public class AlpClassTransformer implements IClassTransformer
{
	@Override
	public byte[] transform(String arg0, String arg1, byte[] arg2)
	{
		/*
		 * TODO:Iterator<String> i = AlpMod.classTransformMap.keySet().iterator(); while(i.hasNext()) { String path = i.next(); if(arg0.equals(path) || arg0.equals(AlpMod.classTransformMap.get(path))) { System.out.println("********* Inside AlpCore transformer about to patch: " + arg0); arg2 = patchClassInJar(arg0, arg2, arg0, AlpMod.location); } }
		 */
		return arg2;
	}

	/*
	 * public byte[] patchClassInJar(String name, byte[] bytes, String ObfName, File location) { try { //open the jar as zip ZipFile zip = new ZipFile(location); ZipEntry entry = zip.getEntry(name.replace('.', '/') + ".class");
	 *
	 *
	 * if (entry == null) { System.out.println(name + " not found in " + location.getName()); } else { //serialize the class file into the bytes array InputStream zin = zip.getInputStream(entry); bytes = new byte[(int) entry.getSize()]; zin.read(bytes); zin.close(); System.out.println("[" + ModData.coreModID + "]: " + "Class " + name + " patched!"); } zip.close(); } catch (Exception e) { throw new RuntimeException("Error overriding " + name + " from " + location.getName(), e); } //return the new bytes return bytes; }
	 */
}
