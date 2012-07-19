package spock.damagecontrol

class FailedFeature extends BaseFeature {

    final failed = true
    final ignored = false
    final result = 'failed'
    final failure = new Failure()
}
