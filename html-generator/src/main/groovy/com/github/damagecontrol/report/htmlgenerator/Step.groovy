package com.github.damagecontrol.report.htmlgenerator

import static Results.UNKNOWN

class Step {

    def type
    def description
    def lineNumber
    def result = UNKNOWN
    def details
}
