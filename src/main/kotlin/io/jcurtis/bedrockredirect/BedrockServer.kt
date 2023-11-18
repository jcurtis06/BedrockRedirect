package io.jcurtis.bedrockredirect

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioDatagramChannel
import org.cloudburstmc.netty.channel.raknet.RakChannelFactory
import org.cloudburstmc.netty.channel.raknet.config.RakChannelOption
import org.cloudburstmc.protocol.bedrock.BedrockPong
import org.cloudburstmc.protocol.bedrock.BedrockServerSession
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec
import org.cloudburstmc.protocol.bedrock.codec.v622.Bedrock_v622
import org.cloudburstmc.protocol.bedrock.netty.initializer.BedrockServerInitializer
import java.net.InetSocketAddress

class BedrockServer {
    companion object {
        val CODEC: BedrockCodec = Bedrock_v622.CODEC
    }

    init {
        val address = InetSocketAddress("0.0.0.0", 19132)

        val pong = BedrockPong()
            .edition("MCPE")
            .motd("BedrockRedirect")
            .subMotd("Join me to play!")
            .playerCount(0)
            .maximumPlayerCount(20)
            .gameType("Survival")
            .ipv4Port(19132)
            .protocolVersion(CODEC.protocolVersion)
            .version(CODEC.minecraftVersion)

        ServerBootstrap()
            .group(NioEventLoopGroup())
            .channelFactory(RakChannelFactory.server(NioDatagramChannel::class.java))
            .option(RakChannelOption.RAK_ADVERTISEMENT, pong.toByteBuf())
            .childHandler(object : BedrockServerInitializer() {
                override fun initSession(session: BedrockServerSession) {
                    session.packetHandler = PacketHandler(session)
                }
            })
            .bind(address)
            .syncUninterruptibly()

        println("BedrockRedirect has started!")
    }
}