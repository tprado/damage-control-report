package spock.damagecontrol

import static spock.damagecontrol.Results.SKIPPED

class SkippedFeature extends BaseFeature {

    final failed = false
    final ignored = true
    final result = SKIPPED
}
