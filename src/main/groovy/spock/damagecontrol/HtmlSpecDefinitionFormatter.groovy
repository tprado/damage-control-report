package spock.damagecontrol

class HtmlSpecDefinitionFormatter {

    private final Spec spec

    HtmlSpecDefinitionFormatter(spec) {
        this.spec = spec
    }

    def format() {
        String specDefinitionHtml = spec.sourceCode

        SourceCodeNormalizer stringLiteral = new SourceCodeNormalizer(/('.*')|(".*")/, 'string_literal')
        SourceCodeNormalizer comments = new SourceCodeNormalizer(/\/\*(.*)\*\//, 'comments')

        specDefinitionHtml = stringLiteral.normalize(specDefinitionHtml)
        specDefinitionHtml = comments.normalize(specDefinitionHtml)

        specDefinitionHtml = reservedWords(specDefinitionHtml)
        specDefinitionHtml = lineNumbers(specDefinitionHtml)
        specDefinitionHtml = errorLines(specDefinitionHtml)

        specDefinitionHtml = stringLiteral.denormalize(specDefinitionHtml, { "<span class='string-literal'>${it}</span>" })
        specDefinitionHtml = comments.denormalize(specDefinitionHtml, { "<span class='comments'>${it}</span>" })

        return specDefinitionHtml
    }

    private String lineNumbers(String result) {
        int lineCount = 1
        return result.replaceAll(/(?m)^.*$/, { "<span class='line-number'>${lineCount++}</span>${it}" })
    }

    private String errorLines(String result) {
        List errorLines = spec.errorLines()
        int lineCount = 1
        return result.replaceAll(/(?m)^.*$/, { errorLines.contains(lineCount++) ? "<span class='error'>${it}</span>" : it })
    }

    private String reservedWords(String result) {
        return result.replaceAll(/((package)|(class)|(extends)|(def))[\s]+/, {
            "<span class='reserved'>${it[1]}</span> "
        })
    }
}
