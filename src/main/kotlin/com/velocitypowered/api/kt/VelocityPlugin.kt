package com.velocitypowered.api.kt

import com.google.inject.Inject
import com.velocitypowered.api.event.EventManager
import com.velocitypowered.api.event.PostOrder
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.kt.event.registerCoroutineContinuationAdapter
import com.velocitypowered.api.plugin.Plugin
import org.slf4j.Logger

@Suppress("unused")
class VelocityPlugin @Inject constructor(
  val logger: Logger,
  val eventManager: EventManager,
) {

  init {
    eventManager.registerCoroutineContinuationAdapter(logger)
  }

  @Subscribe(order = PostOrder.FIRST)
  fun onInit(event: ProxyInitializeEvent) {
    logger.info("The Kotlin Language Adapter is initialized!")
  }
}
