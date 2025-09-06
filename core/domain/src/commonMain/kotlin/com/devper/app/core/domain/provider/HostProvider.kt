package com.devper.app.core.domain.provider

import com.devper.app.core.domain.model.login.System

interface HostProvider {
    fun updateHost(host: String)
}

fun HostProvider.setSystem(system: System) {
    updateHost(system.host)
}
