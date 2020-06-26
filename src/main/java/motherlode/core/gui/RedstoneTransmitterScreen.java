package motherlode.core.gui;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class RedstoneTransmitterScreen extends CottonInventoryScreen<RedstoneTransmitterGuiDescription> {
	public RedstoneTransmitterScreen(RedstoneTransmitterGuiDescription gui, PlayerEntity player, Text title) {
		super(gui, player, title);
	}
}