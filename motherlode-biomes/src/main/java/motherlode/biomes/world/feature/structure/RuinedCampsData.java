package motherlode.biomes.world.feature.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import motherlode.base.Motherlode;
import motherlode.biomes.MotherlodeBiomesMod;
import net.minecraft.structure.pool.*;
import net.minecraft.util.Identifier;

public class RuinedCampsData {

    public static final StructurePool POOL;

    public static void init() {}

    static {
        POOL = StructurePools.register(
                new StructurePool(
                        Motherlode.id(MotherlodeBiomesMod.MODID, "camps/ruined/start"),
                        new Identifier("empty"),
                        ImmutableList.of(
                                Pair.of(StructurePoolElement.method_30425("motherlode-biomes:camps/ruined/start/start_ground_0"), 1)
                        ),
                        StructurePool.Projection.TERRAIN_MATCHING
                )
        );
        StructurePools.register(
                new StructurePool(
                        Motherlode.id(MotherlodeBiomesMod.MODID, "camps/ruined/ground"),
                        new Identifier("empty"),
                        ImmutableList.of(
                          Pair.of(StructurePoolElement.method_30425("motherlode-biomes:camps/ruined/ground/ground_small_0"), 1)
                        ),
                        StructurePool.Projection.TERRAIN_MATCHING
                )
        );
        StructurePools.register(
                new StructurePool(
                        Motherlode.id(MotherlodeBiomesMod.MODID, "camps/ruined/centers"),
                        new Identifier("empty"),
                        ImmutableList.of(
                          Pair.of(StructurePoolElement.method_30425("motherlode-biomes:camps/ruined/centers/campfire_0"), 4),
                          Pair.of(StructurePoolElement.method_30425("motherlode-biomes:camps/ruined/centers/campfire_1"), 4),
                          Pair.of(StructurePoolElement.method_30425("motherlode-biomes:camps/ruined/centers/well_0"), 3)
                        ),
                        StructurePool.Projection.RIGID
                )
        );
        StructurePools.register(
                new StructurePool(
                        Motherlode.id(MotherlodeBiomesMod.MODID, "camps/ruined/structures"),
                        new Identifier("empty"),
                        ImmutableList.of(
                                Pair.of(StructurePoolElement.method_30425("motherlode-biomes:camps/ruined/structures/build_0"), 3),
                                Pair.of(StructurePoolElement.method_30425("motherlode-biomes:camps/ruined/structures/build_1"), 3),
                                Pair.of(StructurePoolElement.method_30425("motherlode-biomes:camps/ruined/structures/build_2"), 2),
                                Pair.of(StructurePoolElement.method_30425("motherlode-biomes:camps/ruined/structures/build_3"), 1),
                                Pair.of(StructurePoolElement.method_30425("motherlode-biomes:camps/ruined/structures/hay_0"), 3),
                                Pair.of(StructurePoolElement.method_30425("motherlode-biomes:camps/ruined/structures/hay_1"), 3),
                                Pair.of(StructurePoolElement.method_30425("motherlode-biomes:camps/ruined/structures/horse_0"), 2),
                                Pair.of(StructurePoolElement.method_30425("motherlode-biomes:camps/ruined/structures/horse_1"), 1),
                                Pair.of(StructurePoolElement.method_30425("motherlode-biomes:camps/ruined/structures/logs_0"), 2),
                                Pair.of(StructurePoolElement.method_30425("motherlode-biomes:camps/ruined/structures/tent_0"), 3),
                                Pair.of(StructurePoolElement.method_30425("motherlode-biomes:camps/ruined/structures/tent_1"), 2),
                                Pair.of(StructurePoolElement.method_30425("motherlode-biomes:camps/ruined/structures/tent_3"), 3),
                                Pair.of(StructurePoolElement.method_30425("motherlode-biomes:camps/ruined/structures/tent_4"), 2)
                        ),
                        StructurePool.Projection.RIGID
                )
        );
    }
}
