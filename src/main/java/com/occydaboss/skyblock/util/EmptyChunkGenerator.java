package com.occydaboss.skyblock.util;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Random;

public class EmptyChunkGenerator extends ChunkGenerator {
    @Override
    @NonNull
    public ChunkData generateChunkData(@NonNull World world, @NonNull Random random, int x, int z, @NonNull BiomeGrid biome) {
        return createChunkData(world);
    }
}
