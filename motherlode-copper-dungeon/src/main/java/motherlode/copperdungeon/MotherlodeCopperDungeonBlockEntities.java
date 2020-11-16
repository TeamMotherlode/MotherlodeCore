package motherlode.copperdungeon;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import motherlode.base.Motherlode;
import motherlode.copperdungeon.block.ZapperTrapBlockEntity;

public class MotherlodeCopperDungeonBlockEntities {
    public static BlockEntityType<ZapperTrapBlockEntity> ZAPPER_TRAP = register("zapper_trap", ZapperTrapBlockEntity::new, MotherlodeCopperDungeonBlocks.ZAPPER_TRAP);

    public static void init() {
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, FabricBlockEntityTypeBuilder.Factory<T> factory, Block... supportedBlocks) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, Motherlode.id(name), FabricBlockEntityTypeBuilder.create(factory, supportedBlocks).build(null));
    }
}
