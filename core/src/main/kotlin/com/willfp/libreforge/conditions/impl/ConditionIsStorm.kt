package com.willfp.libreforge.conditions.impl

import com.willfp.eco.core.config.interfaces.Config
import com.willfp.libreforge.*
import com.willfp.libreforge.conditions.Condition
import com.willfp.libreforge.plugin
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.weather.WeatherChangeEvent

object ConditionIsStorm : Condition<NoCompileData>("is_storm") {
    override fun isMet(
        dispatcher: Dispatcher<*>,
        config: Config,
        holder: ProvidedHolder,
        compileData: NoCompileData
    ): Boolean {
        val location = dispatcher.location ?: return false
        return location.world.hasStorm()
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun handle(event: WeatherChangeEvent) {
        val world = event.world
        for (chunk in world.loadedChunks) {
            Bukkit.getRegionScheduler().run(plugin, world, chunk.x, chunk.z) {
                for (entity in chunk.entities) {
                    entity.scheduler.run(plugin, {
                        entity.toDispatcher().updateEffects()
                    }, null)
                }
            }
        }
    }
}
