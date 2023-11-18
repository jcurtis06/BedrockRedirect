package io.jcurtis.bedrockredirect.utils

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec
import org.cloudburstmc.protocol.bedrock.codec.v544.Bedrock_v544
import org.cloudburstmc.protocol.bedrock.codec.v545.Bedrock_v545
import org.cloudburstmc.protocol.bedrock.codec.v554.Bedrock_v554
import org.cloudburstmc.protocol.bedrock.codec.v557.Bedrock_v557
import org.cloudburstmc.protocol.bedrock.codec.v560.Bedrock_v560
import org.cloudburstmc.protocol.bedrock.codec.v567.Bedrock_v567
import org.cloudburstmc.protocol.bedrock.codec.v568.Bedrock_v568
import org.cloudburstmc.protocol.bedrock.codec.v575.Bedrock_v575
import org.cloudburstmc.protocol.bedrock.codec.v582.Bedrock_v582
import org.cloudburstmc.protocol.bedrock.codec.v589.Bedrock_v589
import org.cloudburstmc.protocol.bedrock.codec.v594.Bedrock_v594
import org.cloudburstmc.protocol.bedrock.codec.v618.Bedrock_v618
import org.cloudburstmc.protocol.bedrock.codec.v622.Bedrock_v622
import java.net.InetAddress
import java.net.UnknownHostException


object BedrockProtocol {
    /**
     * Latest available version
     */
    val DEFAULT_BEDROCK_CODEC: BedrockCodec = Bedrock_v622.CODEC

    /**
     * A list of all supported Bedrock versions that can join BedrockConnect
     */
    private val SUPPORTED_BEDROCK_CODECS: MutableList<BedrockCodec> = ArrayList()

    init {
        SUPPORTED_BEDROCK_CODECS.add(Bedrock_v544.CODEC)
        SUPPORTED_BEDROCK_CODECS.add(
            Bedrock_v545.CODEC.toBuilder()
                .minecraftVersion("1.19.21/1.19.22")
                .build()
        )
        SUPPORTED_BEDROCK_CODECS.add(
            Bedrock_v554.CODEC.toBuilder()
                .minecraftVersion("1.19.30/1.19.31")
                .build()
        )
        SUPPORTED_BEDROCK_CODECS.add(
            Bedrock_v557.CODEC.toBuilder()
                .minecraftVersion("1.19.40/1.19.41")
                .build()
        )
        SUPPORTED_BEDROCK_CODECS.add(
            Bedrock_v560.CODEC.toBuilder()
                .minecraftVersion("1.19.50/1.19.51")
                .build()
        )
        SUPPORTED_BEDROCK_CODECS.add(
            Bedrock_v567.CODEC.toBuilder()
                .minecraftVersion("1.19.52/1.19.62")
                .build()
        )
        SUPPORTED_BEDROCK_CODECS.add(Bedrock_v568.CODEC)
        SUPPORTED_BEDROCK_CODECS.add(
            Bedrock_v575.CODEC.toBuilder()
                .minecraftVersion("1.19.70/1.19.71/1.19.73")
                .build()
        )
        SUPPORTED_BEDROCK_CODECS.add(
            Bedrock_v582.CODEC.toBuilder()
                .build()
        )
        SUPPORTED_BEDROCK_CODECS.add(
            Bedrock_v589.CODEC.toBuilder()
                .build()
        )
        SUPPORTED_BEDROCK_CODECS.add(
            Bedrock_v594.CODEC.toBuilder()
                .build()
        )
        SUPPORTED_BEDROCK_CODECS.add(
            Bedrock_v618.CODEC.toBuilder()
                .build()
        )
        SUPPORTED_BEDROCK_CODECS.add(DEFAULT_BEDROCK_CODEC)
    }

    /**
     * Gets the [BedrockCodec] of the given protocol version.
     * @param protocolVersion The protocol version to attempt to find
     * @return The packet codec, or null if the client's protocol is unsupported
     */
    fun getBedrockCodec(protocolVersion: Int): BedrockCodec? {
        for (packetCodec in SUPPORTED_BEDROCK_CODECS) {
            if (packetCodec.protocolVersion == protocolVersion) {
                return packetCodec
            }
        }
        return null
    }

    fun getIP(hostname: String): String {
        try {
            val host = InetAddress.getByName(hostname)
            println("IP Address: " + host.hostAddress)
            return host.hostAddress
        } catch (ex: UnknownHostException) {
            ex.printStackTrace()
        }
        return hostname
    }
}