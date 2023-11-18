package io.jcurtis.bedrockredirect

import io.jcurtis.bedrockredirect.utils.BedrockProtocol
import io.jcurtis.bedrockredirect.utils.World
import org.cloudburstmc.protocol.bedrock.BedrockServerSession
import org.cloudburstmc.protocol.bedrock.data.PacketCompressionAlgorithm
import org.cloudburstmc.protocol.bedrock.packet.*
import org.cloudburstmc.protocol.common.PacketSignal

class PacketHandler(
    private val session: BedrockServerSession
) : BedrockPacketHandler {
    init {
        println("PacketHandler has been initialized!")
    }

    override fun handle(packet: LoginPacket): PacketSignal {
        try {
            val status = PlayStatusPacket()
            status.status = PlayStatusPacket.Status.LOGIN_SUCCESS
            session.sendPacket(status)

            val resourcePacksInfo = ResourcePacksInfoPacket()
            resourcePacksInfo.isForcedToAccept = false
            resourcePacksInfo.isScriptingEnabled = false
            session.sendPacket(resourcePacksInfo)

            World.joinGame(session)
        } catch (e: Exception) {
            session.disconnect("disconnectionScreen.internalError.cantConnect")
            throw RuntimeException("Unable to complete login", e)
        }
        return PacketSignal.HANDLED
    }

    override fun handle(packet: RequestNetworkSettingsPacket): PacketSignal {
        val protocolVersion = packet.protocolVersion
        val packetCodec = BedrockProtocol.getBedrockCodec(packet.protocolVersion)

        if (packetCodec == null) {
            val status = PlayStatusPacket()
            if (protocolVersion > BedrockProtocol.DEFAULT_BEDROCK_CODEC.protocolVersion) {
                status.status = PlayStatusPacket.Status.LOGIN_FAILED_SERVER_OLD
            } else {
                status.status = PlayStatusPacket.Status.LOGIN_FAILED_CLIENT_OLD
            }

            session.sendPacketImmediately(status)
            return PacketSignal.HANDLED
        }

        session.codec = packetCodec
        val algorithm = PacketCompressionAlgorithm.ZLIB

        val response = NetworkSettingsPacket()
        response.compressionAlgorithm = algorithm
        response.compressionThreshold = 0
        session.sendPacketImmediately(response)

        session.setCompression(algorithm)
        return PacketSignal.HANDLED
    }

    override fun handle(packet: ResourcePackClientResponsePacket): PacketSignal {
        when (packet.status) {
            ResourcePackClientResponsePacket.Status.COMPLETED -> {}
            ResourcePackClientResponsePacket.Status.HAVE_ALL_PACKS -> {
                val rs = ResourcePackStackPacket()
                rs.isForcedToAccept = false
                rs.gameVersion = "*"
                session.sendPacket(rs)
            }

            else -> session.disconnect("disconnectionScreen.resourcePack")
        }
        return PacketSignal.HANDLED
    }

    override fun handle(packet: RequestChunkRadiusPacket): PacketSignal {
        val chunkRadiusUpdatePacket = ChunkRadiusUpdatedPacket()
        chunkRadiusUpdatePacket.radius = packet.radius
        session.sendPacketImmediately(chunkRadiusUpdatePacket)
        val playStatus = PlayStatusPacket()
        playStatus.status = PlayStatusPacket.Status.PLAYER_SPAWN
        session.sendPacket(playStatus)

        transfer(BedrockProtocol.getIP("smp.hometownmc.com"), 19132)
        return PacketSignal.HANDLED
    }

    private fun transfer(ip: String, port: Int) {
        try {
            val tp = TransferPacket()
            tp.address = ip
            tp.port = port
            session.sendPacketImmediately(tp)
        } catch (e: java.lang.Exception) {
            println("Could not transfer to $ip:$port!")
        }
    }

    // ------------------------ UNUSED PACKETS ---------------------------
    // These packets are unused, but will throw errors if not implemented.
    // ------------------------ UNUSED PACKETS ---------------------------

    override fun handle(packet: ClientCacheStatusPacket): PacketSignal {
        return PacketSignal.HANDLED
    }

    override fun handle(packet: ClientToServerHandshakePacket): PacketSignal {
        return PacketSignal.HANDLED
    }

    override fun handle(packet: TickSyncPacket): PacketSignal {
        return PacketSignal.HANDLED
    }
}