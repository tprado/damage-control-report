package com.github.damagecontrol.htmlreport

enum Results {

    UNKNOWN(''),
    PASSED('passed'),
    FAILED('failed'),
    SKIPPED('skipped'),
    NOT_PERFORMED('not performed')

    final description

    Results(description) {
        this.description = description
    }
}
