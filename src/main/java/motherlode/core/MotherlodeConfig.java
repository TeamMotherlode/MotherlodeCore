package motherlode.core;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;
import net.minecraft.world.Difficulty;

@Config(name = Motherlode.MODID)
public class MotherlodeConfig implements ConfigData {

    @ConfigEntry.Category("Ender Invasion")
    public Difficulty spreadDifficulty = Difficulty.HARD;
    @ConfigEntry.Category("Ender Invasion")
    public int invasionEndTime = 216000;
    @ConfigEntry.Category("Ender Invasion")
    public double endermanSpawnRateDay = 0.1;
    @ConfigEntry.Category("Ender Invasion")
    public double endermanSpawnRateNight = 0.25;

    @Override
    public void validatePostLoad() throws ValidationException {

        if(invasionEndTime < 0) invasionEndTime = 0;
        endermanSpawnRateDay = Math.min(Math.max(endermanSpawnRateDay, 0), 1);
        endermanSpawnRateNight = Math.min(Math.max(endermanSpawnRateNight, 0), 1);
    }
}
