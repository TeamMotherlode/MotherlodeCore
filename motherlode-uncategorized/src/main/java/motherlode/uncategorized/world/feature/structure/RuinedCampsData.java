package motherlode.uncategorized.world.feature.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import motherlode.base.Motherlode;
import net.minecraft.structure.pool.LegacySinglePoolElement;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.Identifier;

public class RuinedCampsData {
    public static void init() {}

    static {
        StructurePoolBasedGenerator.REGISTRY.add(
                new StructurePool(
                        Motherlode.id("camps/ruined/start"),
                        new Identifier("empty"),
                        ImmutableList.of(
                                new Pair(new LegacySinglePoolElement("motherlode:camps/ruined/start/start_ground_0"), 1)
                        ),
                        StructurePool.Projection.TERRAIN_MATCHING
                )
        );
        StructurePoolBasedGenerator.REGISTRY.add(
                new StructurePool(
                        Motherlode.id("camps/ruined/ground"),
                        new Identifier("empty"),
                        ImmutableList.of(
                                new Pair(new LegacySinglePoolElement("motherlode:camps/ruined/ground/ground_small_0"), 1)
                        ),
                        StructurePool.Projection.TERRAIN_MATCHING
                )
        );
        StructurePoolBasedGenerator.REGISTRY.add(
                new StructurePool(
                        Motherlode.id("camps/ruined/centers"),
                        new Identifier("empty"),
                        ImmutableList.of(
                                new Pair(new LegacySinglePoolElement("motherlode:camps/ruined/centers/campfire_0"), 4),
                                new Pair(new LegacySinglePoolElement("motherlode:camps/ruined/centers/campfire_1"), 4),
                                new Pair(new LegacySinglePoolElement("motherlode:camps/ruined/centers/well_0"), 3)
                        ),
                        StructurePool.Projection.RIGID
                )
        );
        StructurePoolBasedGenerator.REGISTRY.add(
                new StructurePool(
                        Motherlode.id("camps/ruined/structures"),
                        new Identifier("empty"),
                        ImmutableList.of(
                                new Pair(new LegacySinglePoolElement("motherlode:camps/ruined/structures/build_0"), 3),
                                new Pair(new LegacySinglePoolElement("motherlode:camps/ruined/structures/build_1"), 3),
                                new Pair(new LegacySinglePoolElement("motherlode:camps/ruined/structures/build_2"), 2),
                                new Pair(new LegacySinglePoolElement("motherlode:camps/ruined/structures/build_3"), 1),
                                new Pair(new LegacySinglePoolElement("motherlode:camps/ruined/structures/hay_0"), 3),
                                new Pair(new LegacySinglePoolElement("motherlode:camps/ruined/structures/hay_1"), 3),
                                new Pair(new LegacySinglePoolElement("motherlode:camps/ruined/structures/horse_0"), 2),
                                new Pair(new LegacySinglePoolElement("motherlode:camps/ruined/structures/horse_1"), 1),
                                new Pair(new LegacySinglePoolElement("motherlode:camps/ruined/structures/logs_0"), 2),
                                new Pair(new LegacySinglePoolElement("motherlode:camps/ruined/structures/tent_0"), 3),
                                new Pair(new LegacySinglePoolElement("motherlode:camps/ruined/structures/tent_1"), 2),
                                new Pair(new LegacySinglePoolElement("motherlode:camps/ruined/structures/tent_3"), 3),
                                new Pair(new LegacySinglePoolElement("motherlode:camps/ruined/structures/tent_4"), 2)
                        ),
                        StructurePool.Projection.RIGID
                )
        );
    }
}
