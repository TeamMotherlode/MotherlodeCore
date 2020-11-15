package motherlode.core.block;

import motherlode.core.registry.MotherlodeBlocks;

public class MotherlodeDoorBlock extends net.minecraft.block.DoorBlock {
    public MotherlodeDoorBlock(Settings settings) {
        super(settings);
        MotherlodeBlocks.defaultLootTableList.add(this);
        // In case a resourcepack wants to make the door have holes.
        MotherlodeBlocks.cutouts.add(this);
    }
}
