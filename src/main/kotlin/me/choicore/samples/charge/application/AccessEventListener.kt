package me.choicore.samples.charge.application

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT
import org.springframework.transaction.event.TransactionalEventListener

@Component
class AccessEventListener {
    @TransactionalEventListener(phase = AFTER_COMMIT)
    fun onAccessEvent(event: AccessEvent) {
        log.info("Access event: {}", event)
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(AccessEventListener::class.java)
    }
}
