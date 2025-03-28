package net.minecraft.network.protocol.common.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record PoiAddedDebugPayload(BlockPos pos, String poiType, int freeTicketCount) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, PoiAddedDebugPayload> STREAM_CODEC = CustomPacketPayload.codec(
        PoiAddedDebugPayload::write, PoiAddedDebugPayload::new
    );
    public static final CustomPacketPayload.Type<PoiAddedDebugPayload> TYPE = CustomPacketPayload.createType("debug/poi_added");

    private PoiAddedDebugPayload(FriendlyByteBuf p_300736_) {
        this(p_300736_.readBlockPos(), p_300736_.readUtf(), p_300736_.readInt());
    }

    private void write(FriendlyByteBuf p_298137_) {
        p_298137_.writeBlockPos(this.pos);
        p_298137_.writeUtf(this.poiType);
        p_298137_.writeInt(this.freeTicketCount);
    }

    @Override
    public CustomPacketPayload.Type<PoiAddedDebugPayload> type() {
        return TYPE;
    }
}