package motherlode.redstone.networking;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.fabricmc.fabric.api.network.PacketConsumer;
import net.fabricmc.fabric.api.network.PacketContext;
import motherlode.redstone.MotherlodeModule;
import motherlode.redstone.block.RedstoneTransmitterBlockEntity;

public class RedstoneTransmitterSwapC2SPacket implements PacketConsumer {

    @Override
    public void accept(PacketContext ctx, PacketByteBuf buf) {
        BlockPos pos = buf.readBlockPos();

        ctx.getTaskQueue().execute(() -> {
            if (ctx.getPlayer().world.canSetBlock(pos)) {
                BlockEntity be = ctx.getPlayer().world.getBlockEntity(pos);
                if (be instanceof RedstoneTransmitterBlockEntity) {
                    ((RedstoneTransmitterBlockEntity) be).swapTransmitter();
                }
            }
        });
    }

    public Identifier getId() {
        return MotherlodeModule.id("redstone_transmitter_swap");
    }
}
