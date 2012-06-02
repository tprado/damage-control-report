package spock.damagecontrol

class SourceCodeNormalizer {

    final regex
    final token
    final originalValues = [:]

    SourceCodeNormalizer(regex, token) {
        this.regex = regex
        this.token = token
    }

    def normalize(sourceCode) {
        int i = 0

        sourceCode.replaceAll(regex, {
            def newKey = "##${token}_${i++}##"
            originalValues[newKey] = it[0]
            newKey
        })
    }

    def denormalize(sourceCode, closure) {
        originalValues.each { token, originalValue ->
            sourceCode = sourceCode.replaceAll(token, closure(originalValue))
        }

        sourceCode
    }
}
