package spock.damagecontrol

class SourceCodeNormalizer {

    private final String regex
    private final String token
    private final Map originalValues = [:]

    SourceCodeNormalizer(regex, token) {
        this.regex = regex
        this.token = token
    }

    def normalize(sourceCode) {
        int i = 0

        return sourceCode.replaceAll(regex, {
            def newKey = "##${token}_${i++}##"
            originalValues[newKey] = it[0]
            return newKey
        })
    }

    def denormalize(sourceCode, closure) {
        originalValues.each { token, originalValue ->
            sourceCode = sourceCode.replaceAll(token, closure(originalValue))
        }

        return sourceCode
    }
}