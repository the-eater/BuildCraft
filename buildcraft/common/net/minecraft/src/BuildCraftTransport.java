package net.minecraft.src;

import net.minecraft.src.buildcraft.core.CoreProxy;
import net.minecraft.src.buildcraft.core.Utils;
import net.minecraft.src.buildcraft.transport.BlockCobblestonePipe;
import net.minecraft.src.buildcraft.transport.BlockDiamondPipe;
import net.minecraft.src.buildcraft.transport.BlockGoldenPipe;
import net.minecraft.src.buildcraft.transport.BlockIronPipe;
import net.minecraft.src.buildcraft.transport.BlockStonePipe;
import net.minecraft.src.buildcraft.transport.BlockObsidianPipe;
import net.minecraft.src.buildcraft.transport.BlockWoodenPipe;
import net.minecraft.src.buildcraft.transport.TileCobblestonePipe;
import net.minecraft.src.buildcraft.transport.TileDiamondPipe;
import net.minecraft.src.buildcraft.transport.TileGoldenPipe;
import net.minecraft.src.buildcraft.transport.TileIronPipe;
import net.minecraft.src.buildcraft.transport.TileObsidianPipe;
import net.minecraft.src.buildcraft.transport.TileStonePipe;
import net.minecraft.src.buildcraft.transport.TileWoodenPipe;

public class BuildCraftTransport {
	
	public static final int diamondGUI = 122;
	
	private static boolean initialized = false;
	
	public static BlockWoodenPipe woodenPipeBlock;
	public static BlockStonePipe stonePipeBlock;
	public static BlockIronPipe ironPipeBlock;
	public static BlockGoldenPipe goldenPipeBlock;
	public static BlockDiamondPipe diamondPipeBlock;
	public static BlockObsidianPipe obsidianPipeBlock;
	public static BlockCobblestonePipe cobblestonePipeBlock;
		
	public static int plainIronTexture;
	public static int [] diamondTextures = new int [6];

	public static int tilePipeItemPacket = 1;
	public static int tileDiamondPipeContents = 2;
	
	public static void initialize () {
		if (initialized) {
			return;
		}
		
		initialized = true;
		
		mod_BuildCraftCore.initialize();						
			
		CraftingManager craftingmanager = CraftingManager.getInstance();		
		woodenPipeBlock = new BlockWoodenPipe(Utils.getSafeBlockId(
				"woodenPipe.blockId", 145));
		CoreProxy.addName(woodenPipeBlock.setBlockName("woodenPipe"), "Wooden Pipe");
		ModLoader.RegisterBlock(woodenPipeBlock);		
		craftingmanager.addRecipe(new ItemStack(woodenPipeBlock, 8), new Object[] {
				"   ", "PGP", "   ", Character.valueOf('P'), Block.planks,
				Character.valueOf('G'), Block.glass});
		
		stonePipeBlock = new BlockStonePipe(Utils.getSafeBlockId(
				"stonePipe.blockId", 146));
		CoreProxy.addName(stonePipeBlock.setBlockName("stonePipe"), "Stone Pipe");
		ModLoader.RegisterBlock(stonePipeBlock);		
		craftingmanager.addRecipe(new ItemStack(stonePipeBlock, 8), new Object[] {
				"   ", "PGP", "   ", Character.valueOf('P'), Block.stone,
				Character.valueOf('G'), Block.glass});
		
		ironPipeBlock = new BlockIronPipe(Utils.getSafeBlockId(
				"ironPipe.blockId", 147));
		CoreProxy.addName(ironPipeBlock.setBlockName("ironPipe"), "Iron Pipe");
		ModLoader.RegisterBlock(ironPipeBlock);		
		craftingmanager.addRecipe(new ItemStack(ironPipeBlock, 8), new Object[] {
				"   ", "PGP", "   ", Character.valueOf('P'), Item.ingotIron,
				Character.valueOf('G'), Block.glass});
		
		goldenPipeBlock = new BlockGoldenPipe(Utils.getSafeBlockId(
				"goldenPipe.blockId", 148));
		CoreProxy.addName(goldenPipeBlock.setBlockName("goldenPipe"), "Golden Pipe");
		ModLoader.RegisterBlock(goldenPipeBlock);		
		craftingmanager.addRecipe(new ItemStack(goldenPipeBlock, 8), new Object[] {
				"   ", "PGP", "   ", Character.valueOf('P'), Item.ingotGold,
				Character.valueOf('G'), Block.glass});
		
		diamondPipeBlock = new BlockDiamondPipe(Utils.getSafeBlockId(
				"diamondPipe.blockId", 149));
		CoreProxy.addName(diamondPipeBlock.setBlockName("diamondPipe"), "Diamond Pipe");
		ModLoader.RegisterBlock(diamondPipeBlock);		
		craftingmanager.addRecipe(new ItemStack(diamondPipeBlock, 8), new Object[] {
				"   ", "PGP", "   ", Character.valueOf('P'), Item.diamond,
				Character.valueOf('G'), Block.glass});
		
		obsidianPipeBlock = new BlockObsidianPipe(Utils.getSafeBlockId(
				"obsidianPipeBlock.blockId", 156));
		CoreProxy.addName(obsidianPipeBlock.setBlockName("obsidianPipe"), "Obsidian Pipe");
		ModLoader.RegisterBlock(obsidianPipeBlock);		
		craftingmanager.addRecipe(new ItemStack(obsidianPipeBlock, 8), new Object[] {
				"   ", "PGP", "   ", Character.valueOf('P'), Block.obsidian,
				Character.valueOf('G'), Block.glass});
		
		cobblestonePipeBlock = new BlockCobblestonePipe(Utils.getSafeBlockId(
				"cobblestonePipeBlock.blockId", 156));
		CoreProxy.addName(cobblestonePipeBlock.setBlockName("cobblestonePipe"),
				"Cobblestone Pipe");
		ModLoader.RegisterBlock(cobblestonePipeBlock);		
		craftingmanager.addRecipe(new ItemStack(cobblestonePipeBlock, 8), new Object[] {
				"   ", "PGP", "   ", Character.valueOf('P'), Block.cobblestone,
				Character.valueOf('G'), Block.glass});
		
		ModLoader.RegisterTileEntity(TileWoodenPipe.class, "WoodenPipe");
		ModLoader.RegisterTileEntity(TileStonePipe.class, "StonePipe");
		ModLoader.RegisterTileEntity(TileIronPipe.class, "IronPipe");
		ModLoader.RegisterTileEntity(TileGoldenPipe.class, "GoldenPipe");
		ModLoader.RegisterTileEntity(TileDiamondPipe.class, "DiamondPipe");
		ModLoader.RegisterTileEntity(TileObsidianPipe.class, "ObsidianPipe");
		ModLoader.RegisterTileEntity(TileCobblestonePipe.class, "CobblestonePipe");
		
		plainIronTexture = 1 * 16 + 3;
		
		for (int j = 0; j < 6; ++j) {
			diamondTextures [j] = 1 * 16 + 6 + j;
		}				
		
		Utils.saveProperties();

	}	

	public static void ModsLoaded () {
		mod_BuildCraftCore.initialize();
		initialize ();
	}
}
