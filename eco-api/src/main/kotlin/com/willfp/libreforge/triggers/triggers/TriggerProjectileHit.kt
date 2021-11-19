package com.willfp.libreforge.triggers.triggers

import com.willfp.eco.core.integrations.mcmmo.McmmoManager
import com.willfp.libreforge.triggers.Trigger
import com.willfp.libreforge.triggers.TriggerData
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.ProjectileHitEvent

class TriggerProjectileHit : Trigger("projectile_hit") {
    @EventHandler(ignoreCancelled = true)
    fun onProjectileHit(event: ProjectileHitEvent) {
        if (McmmoManager.isFake(event)) {
            return
        }

        val projectile = event.entity
        val shooter = projectile.shooter

        if (shooter !is Player) {
            return
        }

        this.processTrigger(
            shooter,
            TriggerData(
                player = shooter,
                projectile = projectile,
                location = projectile.location
            )
        )
    }
}
