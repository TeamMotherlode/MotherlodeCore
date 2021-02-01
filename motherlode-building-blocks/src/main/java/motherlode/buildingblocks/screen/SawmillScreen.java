package motherlode.buildingblocks.screen;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import motherlode.buildingblocks.MotherlodeModule;

public class SawmillScreen extends CutterScreen {

    public SawmillScreen(CutterScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected Identifier getTexture() {
        return MotherlodeModule.id("textures/gui/container/sawmill.png");
    }
}
