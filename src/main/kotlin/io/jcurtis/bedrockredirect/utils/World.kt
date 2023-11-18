package io.jcurtis.bedrockredirect.utils

import org.cloudburstmc.math.vector.Vector2f
import org.cloudburstmc.math.vector.Vector3f
import org.cloudburstmc.math.vector.Vector3i
import org.cloudburstmc.nbt.NbtMap
import org.cloudburstmc.protocol.bedrock.BedrockServerSession
import org.cloudburstmc.protocol.bedrock.data.*
import org.cloudburstmc.protocol.bedrock.packet.CreativeContentPacket
import org.cloudburstmc.protocol.bedrock.packet.StartGamePacket
import org.cloudburstmc.protocol.common.util.OptionalBoolean
import java.util.*

object World {
    fun joinGame(session: BedrockServerSession) {
        val startGamePacket = StartGamePacket()
        startGamePacket.uniqueEntityId = 1
        startGamePacket.runtimeEntityId = 1
        startGamePacket.playerGameType = GameType.SURVIVAL
        startGamePacket.playerPosition = Vector3f.from(0f, 0f, 0f)
        startGamePacket.rotation = Vector2f.from(1f, 1f)
        startGamePacket.seed = -1
        startGamePacket.dimensionId = 0
        startGamePacket.generatorId = 1
        startGamePacket.levelGameType = GameType.SURVIVAL
        startGamePacket.difficulty = 1
        startGamePacket.defaultSpawn = Vector3i.ZERO
        startGamePacket.isAchievementsDisabled = false
        startGamePacket.currentTick = -1
        startGamePacket.eduEditionOffers = 0
        startGamePacket.isEduFeaturesEnabled = false
        startGamePacket.rainLevel = 0f
        startGamePacket.lightningLevel = 0f
        startGamePacket.isMultiplayerGame = true
        startGamePacket.isBroadcastingToLan = true
        startGamePacket.gamerules.add(GameRuleData("showcoordinates", false))
        startGamePacket.platformBroadcastMode = GamePublishSetting.PUBLIC
        startGamePacket.xblBroadcastMode = GamePublishSetting.PUBLIC
        startGamePacket.isCommandsEnabled = true
        startGamePacket.isTexturePacksRequired = false
        startGamePacket.isBonusChestEnabled = false
        startGamePacket.isStartingWithMap = false
        startGamePacket.isTrustingPlayers = true
        startGamePacket.defaultPlayerPermission = PlayerPermission.MEMBER
        startGamePacket.serverChunkTickRange = 4
        startGamePacket.isBehaviorPackLocked = false
        startGamePacket.isResourcePackLocked = false
        startGamePacket.isFromLockedWorldTemplate = false
        startGamePacket.isUsingMsaGamertagsOnly = false
        startGamePacket.isFromWorldTemplate = false
        startGamePacket.isWorldTemplateOptionLocked = false
        startGamePacket.spawnBiomeType = SpawnBiomeType.DEFAULT
        startGamePacket.customBiomeName = ""
        startGamePacket.educationProductionId = ""
        startGamePacket.forceExperimentalGameplay = OptionalBoolean.empty()
        startGamePacket.authoritativeMovementMode = AuthoritativeMovementMode.CLIENT
        startGamePacket.rewindHistorySize = 0
        startGamePacket.isServerAuthoritativeBlockBreaking = false
        startGamePacket.vanillaVersion = "*"
        startGamePacket.isInventoriesServerAuthoritative = true
        startGamePacket.serverEngine = ""
        startGamePacket.levelId = "world"
        startGamePacket.levelName = "world"
        startGamePacket.premiumWorldTemplateId = "00000000-0000-0000-0000-000000000000"
        startGamePacket.currentTick = 0
        startGamePacket.enchantmentSeed = 0
        startGamePacket.multiplayerCorrelationId = ""
        startGamePacket.serverEngine = ""
        startGamePacket.playerPropertyData = NbtMap.EMPTY
        startGamePacket.worldTemplateId = UUID.randomUUID()
        startGamePacket.chatRestrictionLevel = ChatRestrictionLevel.NONE
        session.sendPacket(startGamePacket)

        val creativeContentPacket = CreativeContentPacket()
        creativeContentPacket.contents = arrayOfNulls(0)
        session.sendPacket(creativeContentPacket)
    }
}