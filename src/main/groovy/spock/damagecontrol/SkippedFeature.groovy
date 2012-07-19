package spock.damagecontrol

class SkippedFeature extends BaseFeature {

    final failed = false
    final ignored = true
    final result = 'skipped'
}
