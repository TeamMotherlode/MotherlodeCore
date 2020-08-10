package motherlode.core.item;

import motherlode.core.Motherlode;
import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Rarity;

public class DefaultMusicDiscItem extends MusicDiscItem {

    public DefaultMusicDiscItem(SoundEvent sound) {
        super(13, sound, new Item.Settings().maxCount(1).group(Motherlode.MUSIC).rarity(Rarity.RARE));
    }
}
