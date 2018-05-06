package com.nukkitx.server.network.minecraft.packet;

import com.flowpowered.math.vector.Vector3f;
import com.nukkitx.server.network.minecraft.MinecraftPacket;
import com.nukkitx.server.network.minecraft.NetworkPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import static com.nukkitx.server.network.minecraft.MinecraftUtil.*;

@Data
public class InteractPacket implements MinecraftPacket {
    private Action action;
    private long runtimeEntityId;
    private Vector3f mousePosition;

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte(action.ordinal());
        writeRuntimeEntityId(buffer, runtimeEntityId);

        if (action == Action.MOUSEOVER) {
            writeVector3f(buffer, mousePosition);
        }
    }

    @Override
    public void decode(ByteBuf buffer) {
        action = Action.values()[buffer.readByte()];
        runtimeEntityId = readRuntimeEntityId(buffer);

        if (action == Action.MOUSEOVER) {
            mousePosition = readVector3f(buffer);
        }
    }

    @Override
    public void handle(NetworkPacketHandler handler) {
        handler.handle(this);
    }

    public enum Action {
        NONE,
        UNKNOWN_1,
        DAMAGE,
        LEAVE_VEHICLE,
        MOUSEOVER,
        UNKNOWN_5,
        OPEN_INVENTORY
    }
}