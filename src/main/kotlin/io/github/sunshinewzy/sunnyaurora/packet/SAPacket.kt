package io.github.sunshinewzy.sunnyaurora.packet

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.ListenerOptions
import com.comphenix.protocol.events.ListenerPriority
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketEvent
import com.comphenix.protocol.injector.GamePhase
import io.github.sunshinewzy.sunnyaurora.SunnyAurora.Companion.getPlugin
import io.github.sunshinewzy.sunnyaurora.listeners.SAListener
import io.github.sunshinewzy.sunnyaurora.objects.SAJson
import io.github.sunshinewzy.sunstcore.interfaces.Initable

object SAPacket : Initable {
    val pm = ProtocolLibrary.getProtocolManager()
    
    
    override fun init() {
        regListeners()
        
    }
    
    private fun regListeners() {
        pm.addPacketListener(PlayOutChat)
        
    }
    
    
    object PlayOutChat : PacketAdapter(
        params()
            .plugin(getPlugin())
            .serverSide()
            .listenerPriority(ListenerPriority.HIGHEST)
            .gamePhase(GamePhase.PLAYING)
            .optionAsync()
            .options(ListenerOptions.SKIP_PLUGIN_VERIFIER)
            .types(PacketType.Play.Server.CHAT)
    ) {
        override fun onPacketSending(event: PacketEvent) {
            val packet = event.packet
            val type = event.packetType
            val player = event.player
            
            when(type) {
                PacketType.Play.Server.CHAT -> {
                    val texts = packet.chatComponents.values
                    
                    var msg = ""
                    texts.forEach { 
                        msg += it.json
                    }
                    
                    val text = SAJson.chat(msg)
                    if(text != "")
                        SAListener.sendToServerOnContacts(text)
                    
                }
            }
        }

        override fun onPacketReceiving(event: PacketEvent) {
            
        }
    }
    
}