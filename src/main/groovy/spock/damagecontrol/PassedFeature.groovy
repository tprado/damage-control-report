package spock.damagecontrol

import static spock.damagecontrol.Results.PASSED

class PassedFeature extends BaseFeature {

    final failed = false
    final ignored = false
    final result = PASSED
}
