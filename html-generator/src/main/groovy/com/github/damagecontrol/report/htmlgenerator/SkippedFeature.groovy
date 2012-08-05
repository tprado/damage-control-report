package com.github.damagecontrol.report.htmlgenerator

import static Results.SKIPPED

class SkippedFeature extends BaseFeature {

    final failed = false
    final ignored = true
    final result = SKIPPED
}
