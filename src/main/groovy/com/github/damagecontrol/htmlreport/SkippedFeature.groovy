package com.github.damagecontrol.htmlreport

import static Results.SKIPPED

class SkippedFeature extends BaseFeature {

    final failed = false
    final ignored = true
    final result = SKIPPED
}
