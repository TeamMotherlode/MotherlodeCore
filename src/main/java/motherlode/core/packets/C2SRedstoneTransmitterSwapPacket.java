package motherlode.core.packets;

import motherlode.core.Motherlode;
import motherlode.core.block.entity.RedstoneTransmitterBlockEntity;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class C2SRedstoneTransmitterSwapPacket extends MotherlodeC2SPacket {


	@Override
	public void accept(PacketContext ctx, PacketByteBuf buf) {
		BlockPos pos = buf.readBlockPos();

		ctx.getTaskQueue().execute(() -> {
			if(ctx.getPlayer().world.canSetBlock(pos)) {
				BlockEntity be = ctx.getPlayer().world.getBlockEntity(pos);
				if (be instanceof RedstoneTransmitterBlockEntity) {
					((RedstoneTransmitterBlockEntity) be).swapTransmitter();
				}
			}
		});
	}

	@Override
	public Identifier getID() {
		return Motherlode.id("redstone_transmitter_swap");
	}
}
