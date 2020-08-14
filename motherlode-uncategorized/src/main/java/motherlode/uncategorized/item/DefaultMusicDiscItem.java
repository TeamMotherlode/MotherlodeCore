package motherlode.uncategorized.item;

import motherlode.uncategorized.MotherlodeUncategorized;
import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Rarity;

public class DefaultMusicDiscItem extends MusicDiscItem {

    public DefaultMusicDiscItem(SoundEvent sound) {
        super(13, sound, new Item.Settings().maxCount(1).group(MotherlodeUncategorized.MUSIC).rarity(Rarity.RARE));
    }
}
