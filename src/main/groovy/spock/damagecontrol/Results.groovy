package spock.damagecontrol

enum Results {

    UNKNOWN(''),
    PASSED('passed'),
    FAILED('failed'),
    NOT_PERFORMED('not performed')

    final description

    Results(description) {
        this.description = description
    }

    String toString() {
        description
    }
}
