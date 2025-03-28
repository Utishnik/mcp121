package net.minecraft.network.protocol.game;

import java.util.function.BiFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ClientboundBlockEntityDataPacket implements Packet<ClientGamePacketListener> {
    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundBlockEntityDataPacket> STREAM_CODEC = StreamCodec.composite(
        BlockPos.STREAM_CODEC,
        ClientboundBlockEntityDataPacket::getPos,
        ByteBufCodecs.registry(Registries.BLOCK_ENTITY_TYPE),
        ClientboundBlockEntityDataPacket::getType,
        ByteBufCodecs.TRUSTED_COMPOUND_TAG,
        ClientboundBlockEntityDataPacket::getTag,
        ClientboundBlockEntityDataPacket::new
    );
    private final BlockPos pos;
    private final BlockEntityType<?> type;
    private final CompoundTag tag;

    public static ClientboundBlockEntityDataPacket create(BlockEntity p_195643_, BiFunction<BlockEntity, RegistryAccess, CompoundTag> p_335361_) {
        RegistryAccess registryaccess = p_195643_.getLevel().registryAccess();
        return new ClientboundBlockEntityDataPacket(p_195643_.getBlockPos(), p_195643_.getType(), p_335361_.apply(p_195643_, registryaccess));
    }

    public static ClientboundBlockEntityDataPacket create(BlockEntity p_195641_) {
        return create(p_195641_, BlockEntity::getUpdateTag);
    }

    private ClientboundBlockEntityDataPacket(BlockPos p_195637_, BlockEntityType<?> p_195638_, CompoundTag p_195639_) {
        this.pos = p_195637_;
        this.type = p_195638_;
        this.tag = p_195639_;
    }

    @Override
    public PacketType<ClientboundBlockEntityDataPacket> type() {
        return GamePacketTypes.CLIENTBOUND_BLOCK_ENTITY_DATA;
    }

    public void handle(ClientGamePacketListener p_131703_) {
        p_131703_.handleBlockEntityData(this);
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public BlockEntityType<?> getType() {
        return this.type;
    }

    public CompoundTag getTag() {
        return this.tag;
    }
}