package buildcraft.lib.block;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import buildcraft.core.BCRegistry;
import buildcraft.lib.CreativeTabManager;
import buildcraft.lib.MigrationManager;
import buildcraft.lib.TagManager;
import buildcraft.lib.TagManager.EnumTagType;
import buildcraft.lib.TagManager.EnumTagTypeMulti;

public class BlockBuildCraftBase_BC8 extends Block {
    private static List<BlockBuildCraftBase_BC8> registeredBlocks = new ArrayList<>();

    /** The tag used to identify this in the {@link TagManager} */
    public final String id;

    public BlockBuildCraftBase_BC8(Material material, String id) {
        super(material);
        this.id = id;
        setUnlocalizedName(TagManager.getTag(id, EnumTagType.UNLOCALIZED_NAME));
        setRegistryName(TagManager.getTag(id, EnumTagType.REGISTRY_NAME));
        setCreativeTab(CreativeTabManager.getTab(TagManager.getTag(id, EnumTagType.CREATIVE_TAB)));
    }

    public static <B extends BlockBuildCraftBase_BC8> B register(B block) {
        return register(block, false, ItemBlock::new);
    }

    public static <B extends BlockBuildCraftBase_BC8> B register(B block, boolean force) {
        return register(block, force, ItemBlock::new);
    }

    public static <B extends BlockBuildCraftBase_BC8> B register(B block, Function<B, Item> itemBlockConstructor) {
        return register(block, false, itemBlockConstructor);
    }

    public static <B extends BlockBuildCraftBase_BC8> B register(B block, boolean force, Function<B, Item> itemBlockConstructor) {
        if (BCRegistry.INSTANCE.registerBlock(block, itemBlockConstructor.apply(block).setRegistryName(block.getRegistryName()), force)) {
            registeredBlocks.add(block);
            MigrationManager.INSTANCE.addBlockMigration(block, TagManager.getMultiTag(block.id, EnumTagTypeMulti.OLD_REGISTRY_NAME));
            return block;
        }
        return null;
    }
}
