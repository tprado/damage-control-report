package com.github.damagecontrol.htmlreport

import static Results.UNKNOWN

class Step {

    def type
    def description
    def lineNumber
    def result = UNKNOWN
    def details
}
