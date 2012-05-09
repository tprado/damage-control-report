package spock.damagecontrol.testresults

class Spec {

    final String name

    def features = [:]

    Spec(String name) {
        this.name = name
    }

    File file(File baseFolder) {
        return new File(baseFolder.absolutePath + '/' + name.replaceAll(/\./, '/') + '.groovy')
    }
}
