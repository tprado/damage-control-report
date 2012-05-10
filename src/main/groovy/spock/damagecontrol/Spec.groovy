package spock.damagecontrol

class Spec {

    def name
    def features = [:]

    Spec(name) {
        this.name = name
    }

    def file(baseFolder) {
        return new File(baseFolder.absolutePath + '/' + name.replaceAll(/\./, '/') + '.groovy')
    }
}
