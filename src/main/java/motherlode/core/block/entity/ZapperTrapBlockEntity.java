package motherlode.core.block.entity;

import com.google.common.collect.Lists;
import motherlode.core.block.DefaultTrapBlock;
import motherlode.core.network.packet.s2c.ZapS2CPacket;
import motherlode.core.registry.MotherlodeBlockEntities;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

public class ZapperTrapBlockEntity extends BlockEntity implements Tickable {
    private static final int ZAP_DELAY_MAXIMUM = 20;
    private static final int ZAP_RADIUS = 8;

    private int zap_delay = ZAP_DELAY_MAXIMUM;

    public ZapperTrapBlockEntity() {
        super(MotherlodeBlockEntities.ZAPPER_TRAP);
    }

    @Override
    public void tick() {
        if(this.world.isClient) return;
        if(!this.world.getBlockState(this.pos).get(DefaultTrapBlock.POWERED)) return;
        zap_delay--;
        if(zap_delay <= 0){
            zap_delay = ZAP_DELAY_MAXIMUM;
            LivingEntity target = this.world.getClosestEntity(LivingEntity.class, TargetPredicate.DEFAULT, null,
                    this.pos.getX(), this.pos.getY(), this.pos.getZ(),
                    new Box(
                            this.pos.getX() - ZAP_RADIUS, this.pos.getY() - ZAP_RADIUS, this.pos.getZ() - ZAP_RADIUS,
                            this.pos.getX() + ZAP_RADIUS, this.pos.getY() + ZAP_RADIUS, this.pos.getZ() + ZAP_RADIUS
                    ));
            if(target == null) return;

            Stream<PlayerEntity> watchingPlayers = PlayerStream.watching(world, this.pos);
            watchingPlayers.forEach(watching -> {
                ServerSidePacketRegistry.INSTANCE.sendToPlayer(watching, new ZapS2CPacket(
                        this.pos, new Vec3d(target.getX(), target.getY() + (target.getHeight() / 2.f), target.getZ())));
            });
            target.damage(DamageSource.GENERIC, 4);
        }
    }
}
