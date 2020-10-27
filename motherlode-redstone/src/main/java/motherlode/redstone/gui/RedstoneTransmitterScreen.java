package motherlode.redstone.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;

public class RedstoneTransmitterScreen extends CottonInventoryScreen<RedstoneTransmitterGuiDescription> {
    public RedstoneTransmitterScreen(RedstoneTransmitterGuiDescription gui, PlayerEntity player, Text title) {
        super(gui, player, title);
    }

    public RedstoneTransmitterScreen(RedstoneTransmitterGuiDescription gui, PlayerInventory inventory, Text title) {
        super(gui, inventory.player, title);
    }
}
