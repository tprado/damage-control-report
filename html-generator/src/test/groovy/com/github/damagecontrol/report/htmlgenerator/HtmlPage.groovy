package com.github.damagecontrol.report.htmlgenerator

class HtmlPage {

    final html

    HtmlPage(contents) {
        html = new XmlSlurper().parseText(contents)
    }

    @SuppressWarnings('ThrowRuntimeException')
    def findElementById(id) {
        def nodes = html.'**'.findAll { it.@id == id }

        if (nodes.size() == 0) {
            throw new RuntimeException("No element found where id == '${id}'.")
        }

        nodes[0]
    }

    def hasElementWithId(id) {
        def nodes = html.'**'.findAll { it.@id == id }

        !nodes.isEmpty()
    }
}
