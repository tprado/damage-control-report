package spock.damagecontrol

class HtmlSpecDefinitionFormatter {

    static final LINE = /(?m)^.*$/

    final spec

    HtmlSpecDefinitionFormatter(spec) {
        this.spec = spec
    }

    def format() {
        String specDefinitionHtml = spec.sourceCode

        SourceCodeNormalizer strLiteral = new SourceCodeNormalizer(/('.*')|(".*")/, 'string_literal')
        SourceCodeNormalizer comments = new SourceCodeNormalizer(/\/\*(.*)\*\//, 'comments')

        specDefinitionHtml = strLiteral.normalize(specDefinitionHtml)
        specDefinitionHtml = comments.normalize(specDefinitionHtml)

        specDefinitionHtml = reservedWords(specDefinitionHtml)
        specDefinitionHtml = lineNumbers(specDefinitionHtml)
        specDefinitionHtml = errorLines(specDefinitionHtml)

        specDefinitionHtml = strLiteral.denormalize(specDefinitionHtml, { "<span class='string-literal'>${it}</span>" })
        specDefinitionHtml = comments.denormalize(specDefinitionHtml, { "<span class='comments'>${it}</span>" })

        specDefinitionHtml
    }

    def lineNumbers(result) {
        int lineCount = 1
        result.replaceAll(LINE, { "<span class='line-number'>${lineCount++}</span>${it}" })
    }

    def errorLines(result) {
        List errorLines = spec.errorLines()
        int lineCount = 1
        result.replaceAll(LINE, { errorLines.contains(lineCount++) ? "<span class='error'>${it}</span>" : it })
    }

    def reservedWords(result) {
        result.replaceAll(/((package)|(class)|(extends)|(def))[\s]+/, { "<span class='reserved'>${it[1]}</span> " })
    }
}
